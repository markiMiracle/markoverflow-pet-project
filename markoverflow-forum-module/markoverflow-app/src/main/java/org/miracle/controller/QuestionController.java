package org.miracle.controller;

import lombok.AllArgsConstructor;
import org.miracle.dto.QuestionDTO;
import org.miracle.service.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Контроллер для работы с сущностью вопросов
 *
 * @author Mark Valiev
 * @since 23.09.2024
 */
@RestController
@AllArgsConstructor
public class QuestionController {

    private final QuestionService service;

    @PostMapping("/create")
    public void create(@RequestBody QuestionDTO dto) {
        service.create(dto);
    }

    @GetMapping
    public Page<QuestionDTO> index(@RequestParam(required = false, defaultValue = "false") boolean sort,
                                   @RequestParam(name = "offset", defaultValue = "0") @Min(0) Integer offset,
                                   @RequestParam(name = "limit", defaultValue = "20") @Min(1) @Max(100) Integer limit) {
        return service.showAll(sort, offset, limit);
    }

    @PostMapping("/reputation")
    public void addReputation(@RequestParam("questionId") Long questionId) {
        service.addReputation(questionId);
    }

    @GetMapping("/show")
    public QuestionDTO show(@RequestParam("questionId") Long questionId,
                            @RequestParam(required = false, defaultValue = "false") boolean sort,
                            @RequestParam(name = "offset", defaultValue = "0") @Min(0) Integer offset,
                            @RequestParam(name = "limit", defaultValue = "20") @Min(1) @Max(100) Integer limit) {
        return service.show(questionId, sort, offset, limit);
    }

    @DeleteMapping
    public void delete(@RequestParam("questionId") Long questionId) {
        service.delete(questionId);
    }

}