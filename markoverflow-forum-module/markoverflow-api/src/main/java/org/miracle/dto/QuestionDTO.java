package org.miracle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
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
public class QuestionDTO {

    private String author;

    private List<AnswerDTO> answers;

    private String title;

    private String content;

    private LocalDate createdAt;

    private Integer reputation;

    private Integer viewed;

}