package mk.ukim.finki.emt.lab.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * This class is used to configure user login on path '/login' and logout on path '/logout'.
 * The only public page in the application should be '/'.
 * All other pages should be visible only for a user with admin role.
 * Furthermore, in the "list.html" template, the 'Edit', 'Delete', 'Add' buttons should only be
 * visible for a user with admin role.
 * The 'Extend' button should only be visible for a user with role of user.
 * <p>
 * For login inMemory users should be used. Their credentials are given below:
 * [{
 * username: "user",
 * password: "user",
 * role: "USER"
 * },
 * <p>
 * {
 * username: "admin",
 * password: "admin",
 * role: "ADMIN"
 * }]
 */

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * Remove the implementation of the following method and replace it with your own code to implement the security requests.
     * If you do not wish to implement the security requests, leave this code as it is.
     */

    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers((headers) -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
                .authorizeHttpRequests((requests) -> requests
//                                .requestMatchers("/**").permitAll()
                          .requestMatchers("/api/books", "/api/countries", "/api/authors").permitAll()
                          .requestMatchers("/h2/**").hasAnyRole("ADMIN", "LIBRARIAN")
                          .requestMatchers("/api/books/**", "/api/countries/**", "/api/authors/**").hasAnyRole("ADMIN", "LIBRARIAN")//.hasRole("LIBRARIAN")
                          .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").hasAnyRole("ADMIN", "LIBRARIAN")//.hasRole("LIBRARIAN")
//                        .requestMatchers("/reservations/extend/**").hasRole("USER")
//                        .requestMatchers("/reservations/add/**", "/reservations/edit/**", "/reservations/delete/**").hasRole("ADMIN")
//                      .requestMatchers("/h2/**").permitAll()
//                        .anyRequest()
//                        .authenticated()
                )
                .formLogin((form) -> form
                        .permitAll()
                        .failureUrl("/login?error=BadCredentials")
                        .defaultSuccessUrl("/api/books", true)
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/")
                );


        return http.build();
    }

    // In Memory Authentication
        @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1 = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user"))
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN")
                .build();
            UserDetails librarian = User.builder()
                    .username("librarian")
                    .password(passwordEncoder.encode("librarian"))
                    .roles("LIBRARIAN")
                    .build();

            
        return new InMemoryUserDetailsManager(user1, admin, librarian);
    }}


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/**")
//                        .permitAll()
//                );
//        return http.build();
//    }
//}
