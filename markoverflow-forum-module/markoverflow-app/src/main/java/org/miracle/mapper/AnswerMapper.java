package org.miracle.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.miracle.dto.AnswerDTO;
import org.miracle.model.Answer;

/**
 * Маппер сущности {@link Answer}
 *
 * @author Mark Valiev
 * @since 18.09.2024
 */
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = CommentMapper.class
)
public interface AnswerMapper {

    @Mapping(source = "author.login", target = "author")
    AnswerDTO map(Answer model);

    Answer map(AnswerDTO dto);

}