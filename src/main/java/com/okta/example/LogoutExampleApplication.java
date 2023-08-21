package com.okta.example;

//import com.okta.spring.boot.oauth.Okta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@SpringBootApplication
public class LogoutExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogoutExampleApplication.class, args);
    }

    @Configuration
    static class SecurityConfig {

//        @Autowired
//        ClientRegistrationRepository clientRegistrationRepository;

        /**
         * refer: https://github.com/okta/okta-spring-boot
         */
        @Bean
        SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/", "/test1").permitAll()
                    .anyRequest().authenticated()
                    .and().oauth2Client()
                    .and().oauth2Login();
            return http.build();
        }

    }

    @Controller
    static class SimpleController {

        @GetMapping("/")
        public String home() {
            return "home";
        }

        @GetMapping("/test1")
        @ResponseBody
        public ResponseEntity<String> home2() {
            return ResponseEntity.ok("asdas");
        }

        @GetMapping("/profile")
        public ModelAndView userDetails(OAuth2AuthenticationToken authentication) {
            return new ModelAndView("profile" , Collections.singletonMap("details", authentication.getPrincipal().getAttributes()));
        }
    }
}