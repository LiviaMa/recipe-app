package com.manesculivia.receipe.controller;

import com.manesculivia.receipe.model.User;
import com.manesculivia.receipe.model.request.UserRequestDto;
import com.manesculivia.receipe.model.response.UserResponseDto;
import com.manesculivia.receipe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

import static com.manesculivia.receipe.model.response.UserResponseDto.from;
import static org.springframework.http.HttpStatus.CREATED;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public Principal retrievePrincipal(Principal principal) {
        return principal;
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        User user = userService.createUser(userRequestDto, userRequestDto.getRole());
        return new ResponseEntity<>(from(user), CREATED);
    }

}
