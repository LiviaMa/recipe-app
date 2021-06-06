package com.manesculivia.receipe.model.request;

import com.manesculivia.receipe.model.RoleType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotNull
    private RoleType roleType;

}
