package com.manesculivia.receipe.service;

import com.manesculivia.receipe.exception.UsernameAlreadyExistsException;
import com.manesculivia.receipe.model.Authority;
import com.manesculivia.receipe.model.RoleType;
import com.manesculivia.receipe.model.User;
import com.manesculivia.receipe.model.request.UserRequestDto;
import com.manesculivia.receipe.repository.AuthorityRepository;
import com.manesculivia.receipe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.text.MessageFormat.format;
import static java.util.Collections.singletonList;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(format("Not found user with username {0}", username)));
    }

    public User createUser(UserRequestDto userRequestDto, RoleType roleType) {
        String username = userRequestDto.getUsername();
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException(format("{0} is already used", username));
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        Authority authority = authorityRepository.findByRoleType(roleType);
        user.setAuthorities(singletonList(authority));

        return userRepository.save(user);
    }
}