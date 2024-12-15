package com.farmstory.security;

import com.farmstory.oauth2.MyOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final MyOauth2UserService myOauth2UserService;


    //어플리케이션 실행시 등록됨
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 로그인 설정
        http.formLogin(login -> login
                .loginPage("/category/user/login")
                .defaultSuccessUrl("/")
                .failureUrl("/category/user/login?success=100")
                .usernameParameter("uid")
                .passwordParameter("pass"));



        http.sessionManagement(session -> session
                .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::migrateSession)  // 세션 고정 공격 방지
                .maximumSessions(1)  // 동시 로그인 세션 하나로 제한 (옵션)
                .maxSessionsPreventsLogin(true)  // 새로운 로그인이 기존 세션을 만료시키지 않도록 설정
        );



        // 로그아웃 설정
        http.logout(logout -> logout
                .invalidateHttpSession(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/category/user/login?success=101"));

        //Oauth2 설정
        http.oauth2Login(login -> login.loginPage("/user/login")
                .userInfoEndpoint(endpoint-> endpoint.userService(myOauth2UserService)));
        // 인가 설정
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/").permitAll()
                .requestMatchers("/article/**").permitAll()
                .requestMatchers("/category/**").permitAll()
                .requestMatchers("/article/{cateGroup}/{cateName}/{articleNo}").permitAll()
                .requestMatchers("/article/write").authenticated()
                .requestMatchers("/article/delete/**").authenticated()
                .requestMatchers("/user/**").permitAll()
                .anyRequest().permitAll());
        // 기타 보안 설정
        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();



//           .authorizeHttpRequests((requests) -> requests
//                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()  // 정적 리소스 허용
//                .requestMatchers("/", "/index", "/**").permitAll()  // 인증 없이 접근 허용할 경로
//                .anyRequest().permitAll() // 그 외의 요청은 인증 필요
//        )

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        //평문을 암호화시킬때 암호문 만들때 도와주는 encoder
        return new BCryptPasswordEncoder();
    }
}
