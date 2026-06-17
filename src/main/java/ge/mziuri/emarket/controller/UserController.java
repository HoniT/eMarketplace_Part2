package ge.mziuri.emarket.controller;

import ge.mziuri.emarket.model.dto.UserLoginDto;
import ge.mziuri.emarket.model.dto.UserRegisterDto;
import ge.mziuri.emarket.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

//    @PostMapping("/register")
//    public ResponseEntity<String> register(@Valid @RequestBody UserRegisterDto userRegisterDto) {
//
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDto userLoginDto) {
//
//    }

    // We'll just delete the cookie in the frontend for the logout logic
}
