package com.udemy.blogproject.springbootblogrestapi.config;

import com.udemy.blogproject.springbootblogrestapi.security.CustomUserDetailsService;
import com.udemy.blogproject.springbootblogrestapi.security.JwtAuthenticationEntryPoint;
import com.udemy.blogproject.springbootblogrestapi.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // to provide method level security
public class SecurityConfig {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

  @Autowired
  private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests((authorize)->authorize.antMatchers(
                        HttpMethod.GET,"/api/v1/**").permitAll()
                        .antMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                        .antMatchers(HttpMethod.GET, "api/v2/**").permitAll()
                        .antMatchers("/api/v1/auth/**").permitAll()
                        .antMatchers("/v2/api-docs/**").permitAll()
                        .antMatchers("/swagger-ui/**").permitAll()
                        .antMatchers("/swagger-resources/**").permitAll()
                        .antMatchers("/swagger-ui.html").permitAll()
                        .antMatchers("/webjars/**").permitAll()
                        .anyRequest()
                        .authenticated()
                );
        http.addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }




    // to create multiple user with roles


//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails himanshu = User.builder().username("himanshu").password(passwordEncoder().encode("password")).roles("USER").build();
//
//        UserDetails ramu = User.builder().username("ramu").password(passwordEncoder().encode("admin")).roles("ADMIN").build();
//
//        return new InMemoryUserDetailsManager(himanshu, ramu);
//    }
}
