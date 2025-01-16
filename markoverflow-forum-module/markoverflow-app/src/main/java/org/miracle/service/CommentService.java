package org.miracle.service;

import org.miracle.dto.CommentDTO;
import org.miracle.model.Comment;

/**
 * Класс, отвечающий за создание и удаление сущности {@link Comment}
 *
 * @author Mark Valiev
 * @since 21.09.2024
 */
public interface CommentService {

    /**
     * Метод, создающий новый комментарий
     * @param dto - DTO комментария пользователя
     * @param answerId - id сущности ответа
     */
    void create(CommentDTO dto, Long answerId);

    /**
     * Метод, удаляющий комментарий по id
     * @param commentId - id сущности комментария
     */
    void delete(Long commentId);

}
