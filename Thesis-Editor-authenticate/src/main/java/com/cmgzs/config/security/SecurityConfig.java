package com.cmgzs.config.security;

import com.cmgzs.domain.base.ApiResult;
import com.cmgzs.filter.JwtAuthenticationTokenFilter;
import com.cmgzs.handler.LogoutSuccessHandlerImpl;
import com.cmgzs.service.impl.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.Resource;
import java.io.PrintWriter;


@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)/*开启权限认证*/
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 自定义用户认证逻辑
     */
    @Resource
    private UserDetailsServiceImpl myCustomUserService;
    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    /**
     * 退出处理类
     */
    @Resource
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    /**
     * 跨域过滤器
     */
    @Resource
    private CorsFilter corsFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 放行所有OPTIONS请求
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .and().httpBasic()
                // 未登录时，进行json格式的提示
                .authenticationEntryPoint((request, response, authException) -> {
                    Object message = request.getAttribute("ExMessage");
                    //检测是否存在异常信息
                    if (message == null || message.toString().isEmpty())
                        message = "未登录";
                    response.setContentType("application/json;charset=utf-8");
//                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    PrintWriter out = response.getWriter();
                    ApiResult apiResult = new ApiResult(401, String.valueOf(message));
                    out.write(objectMapper.writeValueAsString(apiResult));
                    out.flush();
                    out.close();
                })
                .and()
                .authorizeRequests()
                // 处理跨域请求中的Preflight请求
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                //开放登录、注册接口
                .antMatchers("/auth/login", "/auth/register").permitAll()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated() // 必须授权才能范围
                .and()
                .exceptionHandling()
                // 没有权限，返回json
                .accessDeniedHandler((request, response, authException) -> {
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    ApiResult apiResult = new ApiResult(403, "权限不足");
                    out.write(objectMapper.writeValueAsString(apiResult));
                    out.flush();
                    out.close();
                })
                .and().cors()
                .and().csrf().disable()
                //禁用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.logout().logoutUrl("/api/auth/logout").logoutSuccessHandler(logoutSuccessHandler);
        // 添加CORS filter
        http.addFilterBefore(corsFilter, JwtAuthenticationTokenFilter.class);
        http.addFilterBefore(corsFilter, LogoutFilter.class);
    }

    /**
     * 描述: 静态资源放行，这里的放行，是不走 Spring Security 过滤器链
     **/
    @Override
    public void configure(WebSecurity web) {
//        web.ignoring().antMatchers("/file/**");
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        // 对默认的UserDetailsService进行覆盖
        authenticationProvider.setUserDetailsService(myCustomUserService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authenticationProvider;
    }

    /**
     * 强散列哈希加密实现
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    /**
////     * 身份认证接口
////     */
////    @Override
////    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        auth.userDetailsService(myCustomUserService).passwordEncoder(bCryptPasswordEncoder());
////    }
}