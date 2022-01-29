package ai.ecma.appticket.security;

import ai.ecma.appticket.exception.RestException;
import ai.ecma.appticket.service.AuthService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Base64;
import java.util.UUID;

@Component

public class JWTFilter extends OncePerRequestFilter {

    private final JWTProvider jwtProvider;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    public JWTFilter(JWTProvider jwtProvider, AuthService authService, @Lazy PasswordEncoder passwordEncoder) {
        this.jwtProvider = jwtProvider;
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer")) {
            String token = authorization.substring(7);
            String userId = jwtProvider.getIdFromToken(token);
            UserDetails userDetails = authService.loadById(UUID.fromString(userId));
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } else if (authorization != null && authorization.startsWith("Basic")){
            String token = authorization.substring("Basic ".length());
            String basic = new String(Base64.getDecoder().decode(token));
            String[] split = basic.split(":");
            UserDetails userDetails = authService.loadUserByUsername(split[0]);
            boolean matches = passwordEncoder.matches(split[1], userDetails.getPassword());
            if (!matches)
                throw new RestException(HttpStatus.UNAUTHORIZED, "Login yoki parol xato");
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
