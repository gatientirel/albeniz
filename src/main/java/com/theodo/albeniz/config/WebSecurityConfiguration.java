package com.theodo.albeniz.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.theodo.albeniz.repositories.UserEntityRepository;
import com.theodo.albeniz.services.UserDetailsServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class WebSecurityConfiguration {

        @Autowired
        private UserEntityRepository userEntityRepository;

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        private static class NoRedirectStrategy implements RedirectStrategy {
                @Override
                public void sendRedirect(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                String s) throws IOException {
                }
        }

        /**
         * We override the success handler to prevent Spring MVC from returning a 302
         * REDIRECT to the user application
         * We defer the login redirection handling to the front end.
         *
         * @return A success handler that will return a 200 on successful authentication
         */
        private AuthenticationSuccessHandler successHandler() {
                SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
                successHandler.setRedirectStrategy(new NoRedirectStrategy());
                return successHandler;
        }

        /**
         * @return A simple failureHandler that returns a 401
         */
        private AuthenticationFailureHandler failureHandler() {
                return new SimpleUrlAuthenticationFailureHandler();
        }

        /**
         * We override the success handler to prevent Spring MVC from returning a 302
         * REDIRECT to the user application
         * We defer the logout redirection handling to the front end.
         *
         * @return A success handler that will return a 200 on successful logout
         */
        private LogoutSuccessHandler logoutSuccessHandler() {
                return new HttpStatusReturningLogoutSuccessHandler();
        }

        @Bean
        protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
                http
                                .cors(Customizer.withDefaults())
                                .csrf(AbstractHttpConfigurer::disable)
                                .httpBasic(Customizer.withDefaults())
                                .userDetailsService(new UserDetailsServiceImpl(userEntityRepository))
                                .formLogin(conf -> conf
                                                .usernameParameter("username")
                                                .passwordParameter("password")
                                                .permitAll()
                                                .successHandler(successHandler())
                                                .failureHandler(failureHandler()))
                                .logout(conf -> conf.permitAll(false)
                                                .logoutSuccessHandler(logoutSuccessHandler())

                                )
                                .authorizeHttpRequests(httpRequests -> httpRequests
                                                .requestMatchers(
                                                                "/tooling/health")
                                                .permitAll()
                                                .requestMatchers(
                                                                "/v3/api-docs",
                                                                "/v3/api-docs/*",
                                                                "/swagger-ui.html",
                                                                "/swagger-ui/index.html",
                                                                "/swagger-ui/*")
                                                .permitAll()
                                                .requestMatchers(
                                                                "/message",
                                                                "/message/**")
                                                .permitAll()
                                                .requestMatchers(
                                                                "/library",
                                                                "/library/**")
                                                .permitAll()
                                                .requestMatchers(
                                                                "/users",
                                                                "/users/**")
                                                .permitAll()
                                                .anyRequest()
                                                .authenticated());
                return http.build();
        }
}