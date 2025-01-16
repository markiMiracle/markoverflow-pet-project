package org.miracle.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.miracle.dto.QuestionDTO;
import org.miracle.model.Question;

import java.time.LocalDate;

/**
 * Маппер сущности {@link Question}
 *
 * @author Mark Valiev
 * @since 18.09.2024
 */
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {AnswerMapper.class, CommentMapper.class}
)
public interface QuestionMapper {

    @Mapping(source = "author.login", target = "author")
    QuestionDTO map(Question model);

    @Mapping(source = "createdAt", qualifiedByName = "getDefaultLocalDate", target = "createdAt")
    Question map(QuestionDTO dto);

    @Named("getDefaultLocalDate")
    default LocalDate getDefaultLocalDate(LocalDate value) {
        return LocalDate.now();
    }

}
