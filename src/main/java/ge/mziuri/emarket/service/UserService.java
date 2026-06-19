package ge.mziuri.emarket.service;

import ge.mziuri.emarket.model.dto.UserLoginDto;
import ge.mziuri.emarket.model.dto.UserRegisterDto;
import ge.mziuri.emarket.model.entity.User;
import ge.mziuri.emarket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final TokenService tokenService;

    public void register(UserRegisterDto dto) {
        if (Period.between(dto.getBirthday(), LocalDate.now()).getYears() <= 13) {
            throw new IllegalArgumentException("Age must be strictly greater than 13");
        }

        if (userRepository.existsByUsername(dto.getUsername()) || userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Username or Email already exists!");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setBirthday(dto.getBirthday());

        // Ensure the password gets hashed before it reaches the database
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        userRepository.save(user);
    }

    public String login(UserLoginDto dto) {
        // This automatically delegates to the CustomUserDetailsService and checks the hashed password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        // Generate and return JWT
        return tokenService.generateToken(authentication.getName());
    }
}
