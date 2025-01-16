package org.miracle.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.miracle.dto.UserDTO;
import org.miracle.model.User;

/**
 * Маппер сущности {@link User}
 *
 * @author Mark Valiev
 * @since 18.09.2024
 */
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {

    UserDTO map(User model);

    User map(UserDTO dto);

}