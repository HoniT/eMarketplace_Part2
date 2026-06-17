package ge.mziuri.emarket.service;

import ge.mziuri.emarket.model.dto.UserLoginDto;
import ge.mziuri.emarket.model.dto.UserRegisterDto;
import ge.mziuri.emarket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

//    public String register(UserRegisterDto userRegisterDto) {
//
//    }
//
//    public String login(UserLoginDto userLoginDto) {
//
//    }
}
