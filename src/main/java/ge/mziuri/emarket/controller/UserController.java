package ge.mziuri.emarket.controller;

import ge.mziuri.emarket.model.dto.UserLoginDto;
import ge.mziuri.emarket.model.dto.UserRegisterDto;
import ge.mziuri.emarket.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody UserRegisterDto registerDto) {
        userService.register(registerDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDto loginDto) {
        String token = userService.login(loginDto);
        return ResponseEntity.ok(token);
    }

    // We'll just delete the cookie in the frontend for the logout logic
}
