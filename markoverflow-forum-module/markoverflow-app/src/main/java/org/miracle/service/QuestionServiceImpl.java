package org.miracle.service;

import lombok.RequiredArgsConstructor;
import org.miracle.annotation.ForumAudit;
import org.miracle.annotation.SecurityAudit;
import org.miracle.dto.QuestionDTO;
import org.miracle.exception.ResourceNotFoundException;
import org.miracle.mapper.QuestionMapper;
import org.miracle.model.Question;
import org.miracle.model.User;
import org.miracle.repository.QuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Реализация {@link QuestionService}
 *
 * @author Mark Valiev
 * @since 21.09.2024
 */
@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionMapper mapper;
    private final QuestionRepository repository;
    private final UserService userService;
    private final AnswerService answerService;

    private final HttpServletRequest request;

    @ForumAudit("Создание вопроса")
    @Override
    public void create(QuestionDTO dto) {
        Question question = mapper.map(dto);
        User user = userService.findUser();
        question.setAuthor(user);
        repository.save(question);
    }

    @Override
    @Transactional
    public QuestionDTO show(Long id, boolean sortByReputation, Integer offset, Integer limit) {
        var question = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Вопроса c айди '" + id + "' не существует"));
        question.setViewed(question.getViewed() + 1);
        var answers = answerService.showAnswers(question, sortByReputation, offset, limit);
        var questionDTO = mapper.map(question);
        questionDTO.setAnswers(answers.toList());
        return questionDTO;
    }

    @Override
    public Page<QuestionDTO> showAll(boolean sortByReputation, Integer offset, Integer limit) {
        if (sortByReputation) {
            var questionPage = repository.findAllByReputation(PageRequest.of(offset, limit));
            return questionPage.map(mapper::map);
        }
        var questionPage = repository.findAllByCreatedAt(PageRequest.of(offset, limit));
        return questionPage.map(mapper::map);
    }

    @ForumAudit("Добаление репутации вопросу")
    @Override
    @Transactional
    public void addReputation(Long id) {
        var question = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Вопроса c айди '" + id + "' не существует"));
        question.setReputation(question.getReputation() + 1);
    }

    @ForumAudit("Удаление вопроса")
    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

}