package ru.user.service;

import lombok.RequiredArgsConstructor;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.user.dto.UserDto;
import ru.user.dto.UserInfoDto;
import ru.user.entity.User;
import ru.user.exception.BusinessException;
import ru.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Long createUser(UserDto dto) throws BusinessException {
        if(userRepository.findByUsernameLike(dto.getUsername()).isPresent()) {
            throw new BusinessException("Exists");
        }
        User user = User.builder()
                .username(dto.getUsername())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .pwdHash(passwordEncoder.encode(dto.getPassword()))
                .build();
        user = userRepository.save(user);
        return user.getId();
    }

    @Override
    public void updateUser(UserDto dto) throws BusinessException {
        String login = SecurityUtils.getLogin();
        User user = userRepository.findByUsernameLike(login).orElseThrow(() -> new UsernameNotFoundException("Not found"));
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        //user.setUsername(dto.getUsername());
        user.setPhone(dto.getPhone());
        if(StringUtils.isNotBlank(dto.getPassword())) {
            user.setPwdHash(passwordEncoder.encode(dto.getPassword()));
        }
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) throws BusinessException {
        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException("User not found"));
        userRepository.delete(user);
    }

    @Override
    public UserInfoDto getUserInfo() throws BusinessException {
        String login = SecurityUtils.getLogin();
        User user = userRepository.findByUsernameLike(login).orElseThrow(() -> new UsernameNotFoundException("Not found"));
        return UserInfoDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
