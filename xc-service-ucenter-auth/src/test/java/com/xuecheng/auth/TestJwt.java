package com.xuecheng.auth;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import org.springframework.test.context.junit4.SpringRunner;
import springfox.documentation.spring.web.json.Json;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wu on 2020/3/11 0011
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestJwt {


    //创建JWT令牌
    @Test
    public void testCreateJwt() {
        //密钥文件
        String keyStore = "xc.keystore";
        //密钥库密码
        String keystore_password = "xuechengkeystore";
        //密钥别名
        String alias = "xckey";
        //密钥的访问密码
        String key_password = "xuecheng";
        //密钥库文件路径
        ClassPathResource classPathResource = new ClassPathResource(keyStore);
        //密钥工厂
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(classPathResource, keystore_password.toCharArray());
        //密钥对（公钥和私钥）
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(alias, key_password.toCharArray());
        //获取私钥
        RSAPrivateKey aPrivate = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, String> map = new HashMap<>();
        map.put("name", "itcast");
        String mapString = JSON.toJSONString(map);

        //生成Jwt令牌
        Jwt jwt = JwtHelper.encode(mapString, new RsaSigner(aPrivate));
        //生成JWT令牌编码
        String encoded = jwt.getEncoded();
        System.out.println(encoded);

    }

    //校验Jwt令牌
    @Test
    public void testVerify() {
        //公钥
        String publickey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnASXh9oSvLRLxk901HANYM6KcYMzX8vFPnH/To2R+SrUVw1O9rEX6m1+rIaMzrEKPm12qPjVq3HMXDbRdUaJEXsB7NgGrAhepYAdJnYMizdltLdGsbfyjITUCOvzZ/QgM1M4INPMD+Ce859xse06jnOkCUzinZmasxrmgNV3Db1GtpyHIiGVUY0lSO1Frr9m5dpemylaT0BV3UwTQWVW9ljm6yR3dBncOdDENumT5tGbaDVyClV0FEB1XdSKd7VjiDCDbUAUbDTG1fm3K9sx7kO1uMGElbXLgMfboJ963HEJcU01km7BmFntqI5liyKheX+HBUCD4zbYNPw236U+7QIDAQAB-----END PUBLIC KEY-----";
        //jwt令牌
        String jwtString = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb21wYW55SWQiOiIxIiwidXNlcnBpYyI6bnVsbCwidXNlcl9uYW1lIjoiaXRjYXN0Iiwic2NvcGUiOlsiYXBwIl0sIm5hbWUiOiJ0ZXN0MDIiLCJ1dHlwZSI6IjEwMTAwMiIsImlkIjoiNDkiLCJleHAiOjE1ODQxMzY5MjcsImF1dGhvcml0aWVzIjpbInhjX3RlYWNobWFuYWdlcl9jb3Vyc2VfYmFzZSIsInhjX3RlYWNobWFuYWdlcl9jb3Vyc2VfZGVsIiwieGNfdGVhY2htYW5hZ2VyX2NvdXJzZV9wbGFuIiwieGNfdGVhY2htYW5hZ2VyX2NvdXJzZSIsImNvdXJzZV9maW5kX2xpc3QiLCJ4Y190ZWFjaG1hbmFnZXIiLCJ4Y190ZWFjaG1hbmFnZXJfY291cnNlX21hcmtldCIsInhjX3RlYWNobWFuYWdlcl9jb3Vyc2VfcHVibGlzaCIsImNvdXJzZV9waWNfbGlzdCIsInhjX3RlYWNobWFuYWdlcl9jb3Vyc2VfYWRkIl0sImp0aSI6IjJiNjY4ODcxLTI2ZDctNDQwNy04ODliLTNlYjJkZGFhODYwZCIsImNsaWVudF9pZCI6IlhjV2ViQXBwIn0.E02bSg8p_DQ-Q5d9qhAfCIjxDzxIizXZIfuYkG6K-UDTu7396NvrVVUGk_q4ImSSoxWA3yDOKqipK86N-I_4u_IUFdfpFluYn6T7m44F8-8tLNHpBe5sUW4ngXLqmh3nsZsndYpFo--uHaZxNGlNYP324Fq533OPKw_ccm85Pkux9HOXnhMKXHduoko-p4g_GYSaEsqbn68T-1dgxN73guB-d-FGm1-PmqJoCcylgbJbXHtLj9qj_a0Kxy-TdAS2sOlu9lg9-PuEzVhYnydDmzdcE1SIh4ikeWJHTULgmgnbFi1q8BWVBOwb1DTvWTocSqp62nSA-t3rf0LfxPJgZg";
        //校验Jwt令牌
        Jwt jwtBody = JwtHelper.decodeAndVerify(jwtString, new RsaVerifier(publickey));
        //得到jwt中自定义类容
        String claims = jwtBody.getClaims();
        System.out.println(claims);


    }

}
