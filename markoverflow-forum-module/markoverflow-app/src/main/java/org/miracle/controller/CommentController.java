package org.miracle.controller;

import lombok.AllArgsConstructor;
import org.miracle.dto.CommentDTO;
import org.miracle.service.CommentService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для работы с сущностью комментариев
 *
 * @author Mark Valiev
 * @since 23.09.2024
 */
@RestController
@AllArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService service;

    @PostMapping
    public void create(@RequestParam("answerId") Long answerId, @RequestBody CommentDTO dto) {
        service.create(dto, answerId);
    }

    @DeleteMapping
    public void delete(@RequestParam("commentId") Long commentId) {
        service.delete(commentId);
    }

}
