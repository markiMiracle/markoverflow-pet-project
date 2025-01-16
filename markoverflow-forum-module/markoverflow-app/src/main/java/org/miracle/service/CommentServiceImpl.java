package org.miracle.service;

import lombok.RequiredArgsConstructor;
import org.miracle.annotation.ForumAudit;
import org.miracle.dto.CommentDTO;
import org.miracle.exception.ResourceNotFoundException;
import org.miracle.mapper.CommentMapper;
import org.miracle.model.Comment;
import org.miracle.model.User;
import org.miracle.repository.AnswerRepository;
import org.miracle.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Реализация {@link CommentService}
 *
 * @author Mark Valiev
 * @since 21.09.2024
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper mapper;
    private final CommentRepository commentRepository;
    private final AnswerRepository answerRepository;
    private final UserService userService;

    @ForumAudit("Создание комментария")
    @Override
    @Transactional
    public void create(CommentDTO dto, Long answerId) {
        Comment comment = mapper.map(dto);
        User user = userService.findUser();
        var answer = answerRepository.findById(answerId).orElseThrow(() -> new ResourceNotFoundException("Ответа с айди '" + answerId + "' не существует"));

        var comments = answer.getComments();
        comment.setAnswer(answer);
        comment.setAuthor(user);
        comments.add(comment);

        commentRepository.save(comment);
    }

    @ForumAudit("Удаление комментария")
    @Override
    public void delete(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}