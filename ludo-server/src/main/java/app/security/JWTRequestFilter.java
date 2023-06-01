package app.security;

import app.APIConfig;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.Arrays;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    @Autowired
    APIConfig configuration;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String servletPath = request.getServletPath();

        if (HttpMethod.OPTIONS.matches(request.getMethod()) ||
                Arrays.stream(this.configuration.SECURED_PATHS).noneMatch(servletPath::startsWith)) {

            filterChain.doFilter(request, response);

            return;
        }

        String encryptedToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (encryptedToken == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No token provided.");
            return;
        }
        try {
            JWToken jwToken = new JWToken();
            JWTokenInfo tokenInfo = new JWTokenInfo();
            tokenInfo = jwToken.decodeToken(encryptedToken.replace("Bearer ", ""), false);
        } catch (RuntimeException e) {
            System.out.println(encryptedToken);
            System.out.println("Authentication error: " + e.getMessage());
            // avoid giving clues about the nature of the error to the caller
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication error");
            return;
        }
        filterChain.doFilter(request, response);
    }

}

