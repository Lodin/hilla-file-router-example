package org.vaadin.example.security;

import java.util.Base64;
import java.util.Optional;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.RequestPath;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithms;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import com.vaadin.hilla.route.ClientRouteRegistry;
import com.vaadin.hilla.route.records.ClientViewConfig;

import jakarta.servlet.http.HttpServletRequest;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends VaadinWebSecurity {

    private final ClientRouteRegistry clientRouteRegistry;

    public SecurityConfiguration(ClientRouteRegistry clientRouteRegistry) {
        this.clientRouteRegistry = clientRouteRegistry;
    }

    // The secret is stored in /config/secrets/application.properties by default.
    // Never commit the secret into version control; each environment should have
    // its own secret.
    @Value("${org.vaadin.example.auth.secret}")
    private String authSecret;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(
                authorize -> authorize.requestMatchers(new AntPathRequestMatcher("/images/*.png")).permitAll());
        // Icons from the line-awesome addon
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(new AntPathRequestMatcher("/line-awesome/**/*.svg")).permitAll());

        http.authorizeHttpRequests(authorize -> authorize.requestMatchers(this::isRouteAllowed).permitAll());
        // http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/").permitAll());

        super.configure(http);

        http.sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        setLoginView(http, "/login");
        setStatelessAuthentication(http, new SecretKeySpec(Base64.getDecoder().decode(authSecret), JwsAlgorithms.HS256),
                "org.vaadin.example");
    }

    public ClientViewConfig getRouteByPath(String path) {
        var registeredRoutes = clientRouteRegistry.getAllRoutes();
        var routes = registeredRoutes.keySet();
        var pathMatcher = new AntPathMatcher();
        for (var route : routes) {
            if (pathMatcher.match(route, path)) {
                return registeredRoutes.get(route);
            }
        }
        return null;
    }

    public boolean isRouteAllowed(HttpServletRequest request) {
        var viewConfig = getRouteData(request);
        if (viewConfig.isEmpty()) {
            return false;
        }
        // current roles logic means that an empty array denies access to all
        var rolesAllowed = viewConfig.get().rolesAllowed();
        if (rolesAllowed != null) {
            for (var role : rolesAllowed) {
                if (request.isUserInRole(role)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    private Optional<ClientViewConfig> getRouteData(
            HttpServletRequest request) {
        var requestPath = RequestPath.parse(request.getRequestURI(),
                request.getContextPath());
        return Optional.ofNullable(getRouteByPath(requestPath.pathWithinApplication().value()));
    }
}
