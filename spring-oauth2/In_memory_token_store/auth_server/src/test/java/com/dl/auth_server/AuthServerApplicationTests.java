package com.dl.auth_server;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
class AuthServerApplicationTests {

    public static String authorization_server = "http://127.0.0.1:9000/oauth/token";

    @Autowired
    private RestTemplate restTemplate;
//    @Autowired
//    private OAuth2RestTemplate oAuth2RestTemplate;

    /**
     * 授权码模式
     */
    @Test
    public void getTokenByCode(){
        //浏览器访问
        //http://127.0.0.1:9000/oauth/authorize?client_id=client1&response_type=code&scope=scope1&redirect_uri=http://www.baidu.com
        //http://127.0.0.1:9000/oauth/authorize?client_id=client1&response_type=token&scope=scope1&redirect_uri=http://www.baidu.com

        //重定向结果
        //https://www.baidu.com/?code=r9zqyd

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", "client1");
        params.add("client_secret",  "123");//passwordEncoder.encode("123")
        params.add("grant_type", "authorization_code");
        params.add("code", "UT2vca");
        params.add("redirect_uri", "http://www.baidu.com");

//        ResponseEntity<String> responseEntity =
//                restTemplate.postForEntity(authorization_server,
//                        params, String.class);
//        String body = responseEntity.getBody();
//        System.out.println(body);

        //=========================================================
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
        // 以json的方式提交
//        headers.setContentType(MediaType.APPLICATION_JSON);
        //以表单的方式提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //将请求头部和参数合成一个请求
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        //执行HTTP请求
        ResponseEntity<String> response = restTemplate.exchange(authorization_server, method, requestEntity, String.class);


        System.out.println(response.getBody());

    }

}
