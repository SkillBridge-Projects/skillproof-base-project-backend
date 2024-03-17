package com.skillproof.skillproofapi.security;

import com.skillproof.skillproofapi.services.UserDetailsServiceImp;
import com.skillproof.skillproofapi.services.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true) //pre-authorized
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImp userDetailsService;
    private BCryptPasswordEncoder  passwordEncoder;

    @Autowired
    private UserService userService;

//    public void configure(WebSecurity web) {
//        web.ignoring()
//                .antMatchers(
//                        "/v2/api-docs",
//                        "/configuration/ui",
//                        "/swagger-resources/**",
//                        "/configuration/security",
//                        "/swagger-ui.html",
//                        "/webjars/**"
//                );
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/in/{id}/**","/in/{id}/*","/in/{id}",
                        "/users/{id}")
                .access("@guardUser.checkUserId(authentication,#id)")
                .antMatchers("/login/**",
                        "/signup/**",
                        "/admin/users",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "swagger-resources/**",
                        "/configuration/**",
                        "/v3/api-docs/swagger-config",
                        "/v3/api-docs",
                        "/skills-experiences/**",
                        "/accessToken/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .permitAll()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), userService))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.applyPermitDefaultValues();
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        config.addExposedHeader("Authorization");
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
