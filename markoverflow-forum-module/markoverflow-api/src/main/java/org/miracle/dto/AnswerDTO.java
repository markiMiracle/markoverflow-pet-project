package org.miracle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO сущности вопроса
 *
 * @author Mark Valiev
 * @since 18.09.2024
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO {

    private String author;

    private List<CommentDTO> comments;

    private String content;

    private Integer reputation;

}
