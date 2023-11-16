package Test.Kalvad.Configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Autowired
    private final PasswordConfig passwordConfig;

    @Autowired
    private final BasicAuthEntryPoint basicAuthEntryPoint;


    public SecurityConfig(PasswordConfig passwordConfig, BasicAuthEntryPoint basicAuthEntryPoint) {
        this.passwordConfig = passwordConfig;
        this.basicAuthEntryPoint = basicAuthEntryPoint;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .authorizeHttpRequests()
                .antMatchers("/customer/{id}/address").hasAnyRole("USER", "ADMIN")
                .antMatchers("customer/{id}/address/{address_id}").hasAnyRole("USER", "ADMIN")
                .antMatchers("/customer/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(this.basicAuthEntryPoint))
                .csrf().disable();

        return http.build();
    }

    @Bean
    public UserDetailsService users() {
        // The builder will ensure the passwords are encoded before saving in memory
        User.UserBuilder users = User.builder();
        UserDetails admin = users
                .username("admin")
                .password(passwordConfig.passwordEncoder().encode("ADMIN"))
                .roles("ADMIN")
                .build();
        UserDetails customer = users
                .username("user")
                .password(passwordConfig.passwordEncoder().encode("PASSWORD"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(customer, admin);
    }


}