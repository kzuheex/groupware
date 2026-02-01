package com.zuhee.gw.api.common.security.oauth2;

import com.zuhee.gw.api.common.security.jwt.JwtTokenProvider;
import com.zuhee.gw.api.domain.user.User;
import com.zuhee.gw.api.domain.user.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            // New User -> Redirect to signup/dept selection
            String targetUrl = UriComponentsBuilder.fromUriString("/signup")
                    .queryParam("email", email)
                    .queryParam("name", (String) oAuth2User.getAttribute("name"))
                    .build().toUriString();
            getRedirectStrategy().sendRedirect(request, response, targetUrl);
            return;
        }

        // Existing User -> Issue JWT
        String accessToken = tokenProvider.createAccessToken(authentication);
        String refreshToken = tokenProvider.createRefreshToken(authentication);

        addRefreshTokenCookie(response, refreshToken);

        String targetUrl = UriComponentsBuilder.fromUriString("/")
                .queryParam("token", accessToken)
                .build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    private void addRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie("refresh_token", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // Should be true in production
        cookie.setPath("/");
        cookie.setMaxAge(14 * 24 * 60 * 60); // 14 days
        response.addCookie(cookie);
    }
}
