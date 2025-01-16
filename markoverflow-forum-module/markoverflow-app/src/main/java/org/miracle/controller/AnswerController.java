package org.miracle.controller;

import lombok.AllArgsConstructor;
import org.miracle.dto.AnswerDTO;
import org.miracle.service.AnswerService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для работы с сущностью ответов
 *
 * @author Mark Valiev
 * @since 23.09.2024
 */
@RestController
@AllArgsConstructor
@RequestMapping("/answers")
public class AnswerController {

    private final AnswerService service;

    @PostMapping
    public void create(@RequestParam("questionId") Long questionId, @RequestBody AnswerDTO dto) {
        service.create(dto, questionId);
    }

    @PostMapping("/reputation")
    public void addReputation(@RequestParam("answerId") Long answerId) {
        service.addReputation(answerId);
    }

    @DeleteMapping
    public void delete(@RequestParam("answerId") Long answerId) {
        service.delete(answerId);
    }

}
