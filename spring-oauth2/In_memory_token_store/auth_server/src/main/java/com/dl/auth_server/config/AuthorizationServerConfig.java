package com.dl.auth_server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @Author: Damon
 * @Date: 2020/6/7
 */
@Configuration
//开启oauth2,auth server模式
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     *
     * 用来配置令牌端点的安全约束，针对令牌访问的安全约束
     * 类似
     *   .antMatchers("/r/r1").hasAnyAuthority("p1,p2,p3")
     *                 //这是权限控制，访问r/r1需要p1权限
     *                 .antMatchers("/r/r2").hasAnyAuthority("p2")
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                // 对应路径： oauth/token_key
                .tokenKeyAccess("permitAll")
                // 对应路径：oauth/check_token 公开
                .checkTokenAccess("permitAll")
                //允许表单认证
                .allowFormAuthenticationForClients();
    }


    /**
     *
     * 用来配置客户端详情服务（ClientDetailsService），
     * 客户端详情信息在这里进行初始化，你能够把客户端详情信息写死在这里或者通过数据库来存储调取详细信息
     *
     * client_id
     * client_secret
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        //暂时使用内存方式存储客户端详情信息
        clients.inMemory()
                //client的id和密码
                .withClient("client1")
                .secret(passwordEncoder.encode("123"))

                //client1的资源权限
                .resourceIds("resource1")


                //authorization_code授权码模式,这个是标准模式
                //implicit简单模式,这个主要是给无后台的纯前端项目用的
                //password密码模式,直接拿用户的账号密码授权,不安全
                //client_credentials客户端模式,用clientid和secret授权,和用户无关的授权方式
                //refresh_token使用有效的refresh_token去重新生成一个token,之前的会失效
                .authorizedGrantTypes("authorization_code", "password", "client_credentials", "implicit", "refresh_token")

                //允许授权的范围
                .scopes("scope1")

                //false 跳转到授权页面，true不用跳转直接发令牌
                .autoApprove(false)
//                .authorities()

                //验证回调地址，获取code
                .redirectUris("http://www.baidu.com")

                .and()
                //client2
                .withClient("client2")
                .secret(passwordEncoder.encode("111111"))
                .resourceIds("resource2")
                .authorizedGrantTypes("authorization_code", "password", "client_credentials", "implicit", "refresh_token")
                .scopes("scope1", "scope2")
                .autoApprove(false)
//                .authorities()
                .redirectUris("http://www.baidu.com");

    }

    /**
     * 配置授权服务器终端的非安全特征
     * authenticationManager 校验用户信息是否合法
     * tokenStore：token存储
     * 用来配置 1.令牌（token）的访问端点（理解为申请令牌的url要配）和 2.令牌服务（token services）
     * 令牌访问端点
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //认证管理器
        endpoints
                //密码模式需要
                .authenticationManager(authenticationManager)
                //授权码管理,授权码模式需要
                .authorizationCodeServices(new InMemoryAuthorizationCodeServices())
                //token管理
                .tokenServices(tokenServices())
//                .userDetailsService()
                //允许post提交来访问令牌
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }

    //令牌token管理服务
    @Bean
    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        //客户端信息服务
        defaultTokenServices.setClientDetailsService(clientDetailsService);
        //是否产生刷新令牌
        defaultTokenServices.setSupportRefreshToken(true);

        //令牌存储策略
        defaultTokenServices.setTokenStore(tokenStore);
        //令牌默认有效期2小时
        defaultTokenServices.setAccessTokenValiditySeconds(7200);
        //刷新令牌默认有效期3天
        defaultTokenServices.setRefreshTokenValiditySeconds(259200);
        return defaultTokenServices;
    }
}
