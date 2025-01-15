package org.miracle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Обьект передачи данных пользователя
 * @author Mark Valiev
 * @since 1.08.2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotBlank
    private String login;

    @NotBlank
    private String password;

}
