package appLibrary.configs.security;

import appLibrary.db.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true
)
public class WebAppSecurity {

    @Bean
    AuthenticationProvider authenticationProvider(UserRepository userRepo) {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService((email) -> userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "user with email = " + email + " not found!"
                )));

        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    @SneakyThrows
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, TokenVerifierFilter filter) {

        httpSecurity.cors().and().csrf().disable()
                .authorizeHttpRequests(authz -> {
                    authz.antMatchers("/api-docs", "/v3/api-docs")
                            .permitAll()
                            .anyRequest()
                            .permitAll();
                });

        httpSecurity.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
