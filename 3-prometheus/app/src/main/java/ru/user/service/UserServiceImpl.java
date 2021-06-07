package ru.user.service;

import lombok.RequiredArgsConstructor;
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

    @Override
    public Long createUser(UserDto dto) throws BusinessException {
        User user = User.builder()
                .firstName(dto.getFirstName())
                .email(dto.getEmail())
                .lastName(dto.getLastName())
                .phone(dto.getPhone())
                .build();
        user = userRepository.save(user);
        return user.getId();
    }

    @Override
    public void updateUser(Long userId, UserDto dto) throws BusinessException {
        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException("User not found"));
        user.setFirstName(dto.getFirstName());
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPhone(dto.getPhone());
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) throws BusinessException {
        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException("User not found"));
        userRepository.delete(user);
    }

    @Override
    public UserInfoDto getUserInfo(Long userId) throws BusinessException {
        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException("User not found"));
        return UserInfoDto.builder()
                .id(userId)
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
