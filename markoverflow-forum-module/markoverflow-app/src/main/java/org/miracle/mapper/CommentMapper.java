package org.miracle.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.miracle.dto.CommentDTO;
import org.miracle.model.Comment;
import org.miracle.model.User;


/**
 * Маппер сущности {@link Comment}
 *
 * @author Mark Valiev
 * @since 18.09.2024
 */
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CommentMapper {

    @Mapping(source = "author.login", target = "author")
    CommentDTO map(Comment model);

    Comment map(CommentDTO dto);

    @Mapping(target = "login")
    User map(String login);

}