package com.kuro.ims.dto;

import com.kuro.ims.type.Role;
import lombok.Data;

@Data
public class UpdateUserDto
{
    private String name;

    private String address;

    private String phone;

    private String image;

    private Role role;
}
