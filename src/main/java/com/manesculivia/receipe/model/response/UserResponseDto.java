package com.manesculivia.receipe.model.response;

import com.manesculivia.receipe.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import static java.util.stream.Collectors.joining;

@Getter
@Setter
@ToString
public class UserResponseDto {

    private String username;
    private String roles;

    public static UserResponseDto from(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setRoles(user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(joining(",")));
        return userResponseDto;
    }

}
