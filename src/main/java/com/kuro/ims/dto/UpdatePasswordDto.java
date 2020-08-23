package com.kuro.ims.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdatePasswordDto
{
    @NotBlank(message = "current password is required")
    private String currentPassword;

    @NotBlank(message = "new password is required")
    private String newPassword;
}
