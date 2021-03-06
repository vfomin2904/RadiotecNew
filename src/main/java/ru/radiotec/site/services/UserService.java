package ru.radiotec.site.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.radiotec.site.entity.Number;
import ru.radiotec.site.entity.User;
import ru.radiotec.site.repository.NumberRepository;
import ru.radiotec.site.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void create (UserRepo userRepo){
        User user = new User();
        user.setLogin(userRepo.getLogin());
        user.setPassword(passwordEncoder.encode(userRepo.getPassword()));
        userRepository.save(user);
    }
}
