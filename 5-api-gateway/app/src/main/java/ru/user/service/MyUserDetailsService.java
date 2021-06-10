package ru.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.user.repository.UserRepository;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public User loadUserByUsername(String username) {
        ru.user.entity.User user = userRepository.findByUsernameLike(username).orElseThrow(() -> new UsernameNotFoundException("Not found"));
        return new User(username, user.getPwdHash(), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
