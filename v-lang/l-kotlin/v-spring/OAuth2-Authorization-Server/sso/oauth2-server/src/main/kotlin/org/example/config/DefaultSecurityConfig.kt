package org.example.config

import org.example.federation.FederatedIdentityAuthenticationSuccessHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.session.SessionRegistry
import org.springframework.security.core.session.SessionRegistryImpl
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.session.HttpSessionEventPublisher
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter


@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
class DefaultSecurityConfig {

    @Bean
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { //① 配置鉴权的
                it
                    .requestMatchers("/assets/**", "/webjars/**", "/login","/oauth2/**","/oauth/**").permitAll() //② 忽略鉴权的url
                    .anyRequest().authenticated()//③ 排除忽略的其他url就需要鉴权了
            }

            .csrf { it.disable() }
            .formLogin{
                it.loginPage("/login") //④ 授权服务认证页面（可以配置相对和绝对地址，前后端分离的情况下填前端的url）
            }
            .oauth2Login {
                it
                    .loginPage("/login") //⑤ oauth2的认证页面（也可配置绝对地址）
                    .successHandler(authenticationSuccessHandler()) //⑥ 登录成功后的处理 可以自定义，如果要开启oauth2认证，一定不要禁用
            }

        return http.build();
    }

    private fun authenticationSuccessHandler(): AuthenticationSuccessHandler {
        return FederatedIdentityAuthenticationSuccessHandler()
    }

    // 初始化了一个用户在内存里面（这样就不会每次启动就再去生成密码了）
    @Bean
    fun users(): UserDetailsService {
        val user = User.withDefaultPasswordEncoder()
            .username("user1")
            .password("password")
            .roles("USER")
            .build()
        return InMemoryUserDetailsManager(user)
    }


    @Bean
    fun sessionRegistry(): SessionRegistry {
        return SessionRegistryImpl()
    }

    @Bean
    fun httpSessionEventPublisher(): HttpSessionEventPublisher {
        return HttpSessionEventPublisher()
    }

    /**
     * 跨域过滤器配置
     * @return
     */
    @Bean
    fun corsFilter(): CorsFilter {
        val configuration = CorsConfiguration()
        configuration.addAllowedOrigin("*")
        configuration.allowCredentials = true
        configuration.addAllowedMethod("*")
        configuration.addAllowedHeader("*")
        val configurationSource = UrlBasedCorsConfigurationSource()
        configurationSource.registerCorsConfiguration("/**", configuration)
        return CorsFilter(configurationSource)
    }
}