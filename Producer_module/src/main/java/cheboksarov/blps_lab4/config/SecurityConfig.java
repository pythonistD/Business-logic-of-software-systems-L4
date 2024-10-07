package cheboksarov.blps_lab4.config;

import cheboksarov.blps_lab4.model.Permissions;
import cheboksarov.blps_lab4.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain  securityFilterChain(HttpSecurity http) throws Exception{
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests((authorize) ->
               authorize
                       .requestMatchers("api/v1/authenticate/**", "/swagger-ui.html", "/swagger-ui/**", "/**").permitAll()
                       //.requestMatchers("api/v1/match/save_match/**").hasAuthority(Role.ADMIN.name())
                       .requestMatchers("api/v1/match/save_match/**")
                           .hasAuthority(Permissions.CREATE_MATCH.name())
                       .requestMatchers("api/v1/match/**")
                           .hasAuthority(Permissions.GET_MATCHES.name())
                       .requestMatchers("api/v1/match/update")
                           .hasAuthority(Permissions.UPDATE_MATCH.name())
                       .requestMatchers("api/v1/statistics/save_statistics")
                           .hasAuthority(Permissions.CREATE_STATISTIC.name())
                       .requestMatchers("api/v1/statistics/**")
                           .hasAuthority(Permissions.GET_STATISTIC.name())
                       .requestMatchers("api/v1/coeff/save_coeff")
                           .hasAuthority(Permissions.CREATE_COEFFICIENT.name())
                       .requestMatchers("api/v1/coeff/**")
                           .hasAuthority(Permissions.GET_COEFFICIENT.name())
                       .requestMatchers("api/v1/user/getAll")
                           .hasAuthority(Permissions.GET_USERS.name())
                       .requestMatchers("api/v1/user/**").hasRole(Role.USER.name())
                       .requestMatchers("api/v1/bet/doBet")
                           .hasRole(Role.USER.name())
                       .requestMatchers("api/v1/bet/getMyAllBets")
                           .hasRole(Role.USER.name())
                       .anyRequest().authenticated()
            ).sessionManagement(httSessionManagement ->
                httSessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
