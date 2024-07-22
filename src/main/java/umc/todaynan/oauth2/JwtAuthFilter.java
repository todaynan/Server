package umc.todaynan.oauth2;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import umc.todaynan.converter.UserConverter;
import umc.todaynan.repository.UserRepository;
import umc.todaynan.web.dto.UserDTO.UserDTO;

import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
public class JwtAuthFilter extends GenericFilterBean {
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = tokenService.getJwtFromHeader((HttpServletRequest) request);

        if (token != null && tokenService.verifyToken(token)) {
            String email = tokenService.getUid(token);

            UserDTO userDTO = userConverter.toUserDTO(userRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("사용자가 존재하지 않습니다.")));

            Authentication auth = getAuthentication(userDTO);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        chain.doFilter(request, response);
    }

    public Authentication getAuthentication(UserDTO user) {
        return new UsernamePasswordAuthenticationToken(user, "",
                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
