package appLibrary.configs.security;

import appLibrary.db.models.User;
import appLibrary.db.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.persistence.EntityNotFoundException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenVerifierFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        Optional<String> optionalToken = getTokenFromRequest(request);

        optionalToken.ifPresent(token -> {

            String email = jwtUtils.verifyToken(token);

            System.out.println("email = " + email);
            User user = userRepository.findByEmail(email)
                    .orElseThrow(EntityNotFoundException::new);

            userRepository.findAll().forEach(System.out::println);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    user.getEmail(),
                    null,
                    Collections.singletonList(user.getRole())
            );

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        });

        filterChain.doFilter(request, response);
    }

    private Optional<String> getTokenFromRequest(HttpServletRequest request) {

        String header = request.getHeader("Authorization");

        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return Optional.of(header.substring("Bearer ".length()));
        }

        return Optional.empty();
    }
}