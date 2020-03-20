package com.xuecheng.auth.service;

import com.alibaba.fastjson.JSON;
import com.xuecheng.framework.client.XcServiceList;
import com.xuecheng.framework.domain.ucenter.ext.AuthToken;
import com.xuecheng.framework.domain.ucenter.ext.UserTokenStore;
import com.xuecheng.framework.domain.ucenter.response.AuthCode;
import com.xuecheng.framework.exception.ExceptionCast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.spring.web.json.Json;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author wu on 2020/3/11 0011
 */
@Service
public class AuthService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);
    @Value("${auth.tokenValiditySeconds}")
    int tokenValiditySeconds;
    @Autowired
    LoadBalancerClient loadBalancerClient;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    StringRedisTemplate stringredisTemplate;

    //用户申请令牌，并存如reids中
    public AuthToken login(String username, String password, String clientId, String clientSecret) {
        //请求SpringSecurity申请令牌
        AuthToken authToken = this.applyToken(username, password, clientId, clientSecret);
        if (authToken == null) {
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_APPLYTOKEN_FAIL);
        }
        String access_token = authToken.getAccess_token();
        String content = JSON.toJSONString(authToken);
        //存储在Redis中
        boolean result = this.saveRedis(access_token, content, tokenValiditySeconds);
        if (!result) {
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_TOKEN_SAVEFAIL);
        }


        return authToken;
    }

    //申请令牌
    private AuthToken applyToken(String username, String password, String clientId, String clientSecret) {
        //从Eurka中获取认证服务地址(因为在Spring sercutiry在认证服务中)
        ServiceInstance serviceInstance = loadBalancerClient.choose(XcServiceList.XC_SERVICE_UCENTER_AUTH);
        //此地址就是 http://ip:port
        URI uri = serviceInstance.getUri();
        //令牌申请地址
        String authUri = uri + "/auth/oauth/token";
        //定义header
        LinkedMultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        String httpBasic = getHttpBasic(clientId, clientSecret);
        header.add("Authorization", httpBasic);
        //定义body
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("username", username);
        body.add("password", password);


        HttpEntity<MultiValueMap<String, String>> multiValueMapHttpEntity = new HttpEntity<>(body, header);
        //设置restTemplate远程调用时候，对400和401不让报错，正确返回数据
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getRawStatusCode() != 400 && response.getRawStatusCode() != 401) {
                    super.handleError(response);
                }
            }
        });
        //申请令牌的结果
        ResponseEntity<Map> exchange = restTemplate.exchange(authUri, HttpMethod.POST, multiValueMapHttpEntity, Map.class);
        //申请令牌的信息
        Map bodyMap = exchange.getBody();
        if (bodyMap == null || bodyMap.get("access_token") == null || bodyMap.get("refresh_token") == null || bodyMap.get("jti") == null) {
            if (bodyMap != null && bodyMap.get("error_description") != null) {
                String error_description = (String) bodyMap.get("error_description");
                if (error_description.indexOf("UserDetailsService returned null, which is an interface contract violation") >= 0) {
                    ExceptionCast.cast(AuthCode.AUTH_ACCOUNT_NOTEXISTS);
                } else if (error_description.indexOf("坏的凭证") >= 0) {
                    ExceptionCast.cast(AuthCode.AUTH_CREDENTIAL_ERROR);
                }
            }
            return null;
        }
        AuthToken authToken = new AuthToken();
        authToken.setAccess_token((String) bodyMap.get("jti"));//用户身份令牌
        authToken.setJwt_token((String) bodyMap.get("access_token"));//jwt令牌
        authToken.setRefresh_token((String) bodyMap.get("refresh_token"));//用户刷新令牌
        System.out.println(bodyMap + "----??");
        return authToken;
    }


    /**
     * @param * @param null
     * @Date:22:38 2020/3/11 0011
     * @Description://存储令牌到Redis中
     * @retrun
     */
    private boolean saveRedis(String access_token, String content, long ttl) {
        String key = "user_token:" + access_token;
        stringredisTemplate.boundValueOps(key).set(content, ttl, TimeUnit.SECONDS);
        Long expire = stringredisTemplate.getExpire(key, TimeUnit.SECONDS);
        return expire > 0;
    }

    /**
     * @param * @param null
     * @Date:17:53 2020/3/12 0012
     * @Description:清理Redis中的jwt令牌
     * @retrun
     */
    public boolean deleteRdis(String access_token) {
        String key = "user_token:" + access_token;
        stringredisTemplate.delete(key);
        return true;
    }

    //获取httpbasic串
    private String getHttpBasic(String clientId, String clientSecret) {
        String string = clientId + ":" + clientSecret;
        //将串进行Base64为编码
        byte[] encode = Base64Utils.encode(string.getBytes());
        return "Basic " + new String(encode);

    }

    //从Redis中查询令牌
    public AuthToken getUserToken(String uid) {
        String key = "user_token:" + uid;
        //从Redis中获取jwt
        String stringToken = stringredisTemplate.opsForValue().get(key);
        if (stringToken != null) {
            AuthToken authToken=null;
            try {
                //转为对象
                authToken = JSON.parseObject(stringToken, AuthToken.class);
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("getUserToken from redis and execute JSON.parseObject error {}",e.getMessage());
            }
            return authToken;
        }
        return null;
    }
}
