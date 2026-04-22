package com.webapp.arvand.coreback.Handler;

import com.webapp.arvand.coreback.Dtos.GithubUserDto;
import com.webapp.arvand.coreback.Entity.GithubUser;
import com.webapp.arvand.coreback.Guava.GuavaCache;
import com.webapp.arvand.coreback.Guava.GuavaService;
import com.webapp.arvand.coreback.Services.GitlabInterface;
import com.webapp.arvand.coreback.Jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class GithubSuccessHandler implements AuthenticationSuccessHandler {

    @Value("${frontend.url}")
    private String frontendUrl;

    @Autowired
    private GitlabInterface gitlabInterface;
    @Autowired
    private GuavaService  guavaService;
    @Autowired
    private JwtService jwtService;
    private final OAuth2AuthorizedClientService authorizedClientService;

    public GithubSuccessHandler(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2AuthenticationToken auth = (OAuth2AuthenticationToken) authentication;

        OAuth2AuthorizedClient client =
                authorizedClientService.loadAuthorizedClient(
                        auth.getAuthorizedClientRegistrationId(),
                        auth.getName()
                );

        String accessToken = client.getAccessToken().getTokenValue();
        try {
            GithubUser resUser = gitlabInterface.login(accessToken);
            if (resUser == null) {
                response.sendRedirect("http://localhost:3000/callback?error=user_login_failed");
                return;
            }
            String jti = UUID.randomUUID().toString();
            String jwt = jwtService.generateToken(resUser.getUserId(),jti);
            GuavaCache guavaCache = new GuavaCache(resUser,jti);
            guavaService.putToCache(guavaCache.getUserId(),guavaCache);
            String cookie = String.format("access_token=%s; HttpOnly; Path=/; SameSite=Lax; Max-Age=86400; Secure=%s",
                    jwt,
                    frontendUrl.startsWith("https") ? "true" : "false"
            );
            response.setHeader("Set-Cookie", cookie);

            String redirectUrl = String.format("%s/auth/callback?status=success", frontendUrl);
            response.sendRedirect(redirectUrl);

        } catch (Exception e) {
            String errorUrl = String.format("%s/auth/callback?error=%s",
                    frontendUrl,
                    java.net.URLEncoder.encode(e.getMessage(), "UTF-8")
            );
            response.sendRedirect(errorUrl);
        }
    }
}
