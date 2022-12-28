package com.demo.redditbackend.service;

import com.demo.redditbackend.dto.SignupRequest;
import com.demo.redditbackend.exception.SpringRedditException;
import com.demo.redditbackend.model.NotificationEmail;
import com.demo.redditbackend.model.User;
import com.demo.redditbackend.model.VerificationToken;
import com.demo.redditbackend.repository.UserRepository;
import com.demo.redditbackend.repository.VerificationTokenRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;

    @Transactional
    public void signup(SignupRequest signupRequest) {
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(this.passwordEncoder.encode(signupRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        this.userRepository.save(user);

        String token = genrateVerificationToken(user);

        try {
            this.mailService.sendEmail(new NotificationEmail("Activate Your Account", user.getEmail(),
                    "Here is your activation link: " +
                            "http://localhost:8080/api/auth/accountVerification/" + token));
            log.info("Email Sent");
        } catch (SpringRedditException e) {
            // TODO: Handle Exception
            log.info(e.toString());
        }
    }

    public void verifyToken(String token) throws SpringRedditException {
        Optional<VerificationToken> verificationToken = this.verificationTokenRepository.getByToken(token);
        verificationToken.orElseThrow(() -> new SpringRedditException("Invalid Token"));
        
        enableUser(verificationToken.get());
    }

    @Transactional
    private void enableUser(VerificationToken verificationToken) throws SpringRedditException {
        String username = verificationToken.getUser().getUsername();
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("User not found"));

        user.setEnabled(true);

        this.userRepository.save(user);
    }

    private String genrateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        this.verificationTokenRepository.save(verificationToken);

        return token;
    }
}
