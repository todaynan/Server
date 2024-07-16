package umc.todaynan.oauth2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import umc.todaynan.converter.UserConverter;
import umc.todaynan.oauth2.JwtAuthFilter;
import umc.todaynan.oauth2.OAuth2SuccessHandler;
import umc.todaynan.oauth2.TokenService;
import umc.todaynan.oauth2.authority.CustomAuthorityMapper;
import umc.todaynan.repository.UserRepository;
import umc.todaynan.service.UserService.CustomOAuth2UserService;
import umc.todaynan.service.UserService.CustomOidcUserService;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOidcUserService customOidcUserService;
    @Bean
    SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**").permitAll()
                        .requestMatchers("/token/**", "/user/signup/**", "/user/login/**", "/health").permitAll()
                        .anyRequest().authenticated())

                .addFilterBefore(new JwtAuthFilter(tokenService, userRepository,userConverter), UsernamePasswordAuthenticationFilter.class)

                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/token/expired")
                        .permitAll()
                        .successHandler(oAuth2SuccessHandler)
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(customOAuth2UserService)
                                .oidcUserService(customOidcUserService)))

                .logout(logout -> logout
                        .logoutSuccessUrl("/logout"))
        ;

        return http.build();
    }
    @Bean
    public GrantedAuthoritiesMapper customAuthorityMapper() {
        return new CustomAuthorityMapper();
    }

}
