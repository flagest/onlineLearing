package com.xuecheng.auth;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author wu on 2020/3/11 0011
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRedis {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void testRedis() {
        //对应的key
        String key = "user_token:3c3e6fc1-301b-4ca5-ad10-b971a16ae283";
        //定义Value
        Map<String, String> map = new HashMap<>();
        map.put("jwt","eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb21wYW55SWQiOm51bGwsInVzZXJwaWMiOm51bGwsInVzZXJfbmFtZSI6Iml0Y2FzdCIsInNjb3BlIjpbImFwcCJdLCJuYW1lIjpudWxsLCJ1dHlwZSI6bnVsbCwiaWQiOm51bGwsImV4cCI6MTU4Mzk2MDY2NCwianRpIjoiYjYyNzBhYzktMjI2MC00YmZlLWEzNTctOTk4YjAxZWQyMGM4IiwiY2xpZW50X2lkIjoiWGNXZWJBcHAifQ.G6d6mls3w4yRat-fAuVtDw1G8oE7DM92Exg3HZ9EdEei4jJG0VyzzEyHJ6ftpFKzEhshok1oUXP8VK6NdXuDQXb1sKRhtIljr30Fp8iw96BJwJzMSkUfERtRN7VVqKKR6q52Uojw6gQavGvk1XlxVRKo7eyRnABIESDFn4JtDEXFWBSCBFzuJI5qj6kLJR9OAGAH9OAvV-BpGB-Rp9wFfRe6MuFU__");
        map.put("refresh_token","eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb21wYW55SWQiOm51bGwsInVzZXJwaWMiOm51bGwsInVzZXJfbmFtZSI6IlhjV2ViQXBwIiwic2NvcGUiOlsiYXBwIl0sImF0aSI6IjNjM2U2ZmMxLTMwMWItNGNhNS1hZDEwLWI5NzFhMTZhZTI4MyIsIm5hbWUiOm51bGwsInV0eXBlIjpudWxsLCJpZCI6bnVsbCwiZXhwIjoxNTgzOTUzOTgzLCJqdGkiOiI1M2M2OWY0ZC1mZTE1LTRlNjYtYjA1MC02OGIzMGI0ODU2NjQiLCJjbGllbnRfaWQiOiJYY1dlYkFwcCJ9.YklVZT0TkO2fAmBBd4cRvmjslyeIlQrGErXULYQuaRn07QaFVC4oMHKhbpeKV_bOaqm-VVo3QEo2YXdssetSYaoHESJfG8MG6VrCT77SOPdqlH8JgnUQEFIr6VAkT1JS8EnpaoKR9BhtvfUb9FrFJLF2aw3BX_yAw3DO_RfzBP8MA_V1S-J89IFoThgcQUG1ycU8-_O2kfSfbNjX0a7DKXI3OWr8wounJUdS_PYE7jYp9IrCmMGFGVTQ_Uwz4WMpEk2cupEnCuky4lT0f-sr-939lWhwoHaveTW5pZKocOY44yFs8NdhoKQSLCXjXba2DcyPotz0DX8Q5Cs2vqNeiw");
        String jsonString = JSON.toJSONString(map);
        //校验key是否存在
        Long expire = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
        System.out.println(expire+"____");
        stringRedisTemplate.boundValueOps(key).set(jsonString,30,TimeUnit.SECONDS);
        //获取key
        String string = stringRedisTemplate.opsForValue().get(key);
        System.out.println(string+"====");


    }

}
