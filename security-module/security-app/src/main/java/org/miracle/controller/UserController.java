package org.miracle.controller;

import lombok.AllArgsConstructor;
import org.miracle.dto.UserDTO;
import org.miracle.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * Класс, являющийся обработчиком маршрутов для сущности пользователя
 * @author Mark Valiev
 * @since 09.08.2024
 */
@RestController
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("/registration")
    public void registration(@RequestHeader(name = "device") String device, @RequestHeader(name = "ip") String ip,
                             @Valid @RequestBody UserDTO dto) {
        userService.createUser(dto);
    }

    @PostMapping("/login")
    public void login(@RequestHeader(name = "device") String device, @RequestHeader(name = "ip") String ip,
                      @Valid @RequestBody UserDTO dto) {
        userService.login(dto);
    }

}