package org.miracle.service;

import lombok.RequiredArgsConstructor;
import org.miracle.annotation.ForumAudit;
import org.miracle.dto.AnswerDTO;
import org.miracle.exception.ResourceNotFoundException;
import org.miracle.mapper.AnswerMapper;
import org.miracle.model.Answer;
import org.miracle.model.Question;
import org.miracle.model.User;
import org.miracle.repository.AnswerRepository;
import org.miracle.repository.QuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Реализация {@link AnswerService}
 *
 * @author Mark Valiev
 * @since 21.09.2024
 */
@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerMapper mapper;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final UserService userService;


    @ForumAudit("Создание ответа")
    @Override
    @Transactional
    public void create(AnswerDTO dto, Long questionId) {
        Answer answer = mapper.map(dto);
        User user = userService.findUser();
        answer.setAuthor(user);
        var question = questionRepository.findById(questionId).orElseThrow(() -> new ResourceNotFoundException("Вопрса с айди '" + questionId + "' не существует"));
        answer.setQuestion(question);
        var answers = question.getAnswers();
        answers.add(answer);
        answerRepository.save(answer);
    }

    @Override
    public Page<AnswerDTO> showAnswers(Question question, boolean sortByReputation, Integer offset, Integer limit) {
        if (sortByReputation) {
            var answerPage = answerRepository.findAllByReputation(question, PageRequest.of(offset, limit));
            return answerPage.map(mapper::map);
        }
        var answerPage = answerRepository.findAllByCreatedAt(question, PageRequest.of(offset, limit));
        return answerPage.map(mapper::map);
    }

    @ForumAudit("Добавление репутации ответу")
    @Override
    @Transactional
    public void addReputation(Long id) {
        var answer = answerRepository.findById(id).orElseThrow(() ->  new ResourceNotFoundException("Ответа c айди '" + id + "' не существует"));
        answer.setReputation(answer.getReputation() + 1);
    }

    @ForumAudit("Удаление ответа")
    @Override
    public void delete(Long answerId) {
        answerRepository.deleteById(answerId);
    }
}