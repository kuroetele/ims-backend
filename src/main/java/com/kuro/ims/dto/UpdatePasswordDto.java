package com.kuro.ims.dto;

import lombok.Data;

@Data
public class UpdatePasswordDto
{
    private String currentPassword;

    private String newPassword;
}
