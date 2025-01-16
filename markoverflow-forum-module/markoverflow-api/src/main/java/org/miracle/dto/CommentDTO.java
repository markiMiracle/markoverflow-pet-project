package org.miracle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO сущности комментария
 *
 * @author Mark Valiev
 * @since 18.09.2024
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private String author;

    private String content;
}
