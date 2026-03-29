package MyFirstAPI.services;

import MyFirstAPI.models.User;
import MyFirstAPI.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getOrCreateUser(OAuth2User oauth2User) {
        String githubId = oauth2User.getAttribute("id").toString();
        String username = oauth2User.getAttribute("login");
        String email = oauth2User.getAttribute("email");

        return userRepository.findByGithubId(githubId)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setGithubId(githubId);
                    newUser.setUsername(username);
                    newUser.setEmail(email);
                    return userRepository.save(newUser);
                });
    }

    public User getUserByGithubId(String githubId) {
        return userRepository.findByGithubId(githubId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}