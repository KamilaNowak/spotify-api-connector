package com.nowak.spotifyapiconnector.security;

        import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.security.config.annotation.web.builders.HttpSecurity;
        import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
        import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableOAuth2Sso
public class SecurityConfigurationClass extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/users/**").authenticated()
                                .antMatchers("/data/**").authenticated();
    }
}
