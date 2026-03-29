package MyFirstAPI.controllers;

import MyFirstAPI.config.JwtUtil;
import MyFirstAPI.models.User;
import MyFirstAPI.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @GetMapping("/success")
    public Map<String, String> loginSuccess(@AuthenticationPrincipal OAuth2User oauth2User) {
        User user = userService.getOrCreateUser(oauth2User);
        String token = jwtUtil.generateToken(user.getGithubId());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("username", user.getUsername());
        response.put("message", "Kopiera token och använd i Bruno som 'Bearer <token>'");

        return response;
    }

    @GetMapping("/user")
    public User getCurrentUser(@AuthenticationPrincipal OAuth2User oauth2User) {
        return userService.getOrCreateUser(oauth2User);
    }
}