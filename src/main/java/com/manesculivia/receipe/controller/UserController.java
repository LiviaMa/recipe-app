package com.manesculivia.receipe.controller;

import com.manesculivia.receipe.model.User;
import com.manesculivia.receipe.model.request.UserRequestDto;
import com.manesculivia.receipe.model.response.UserResponseDto;
import com.manesculivia.receipe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.manesculivia.receipe.model.response.UserResponseDto.from;
import static org.springframework.http.HttpStatus.CREATED;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        User user = userService.createUser(userRequestDto, userRequestDto.getRole());
        return new ResponseEntity<>(from(user), CREATED);
    }

}
