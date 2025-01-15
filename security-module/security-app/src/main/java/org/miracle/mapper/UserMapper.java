package org.miracle.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mindrot.jbcrypt.BCrypt;
import org.miracle.dto.UserDTO;
import org.miracle.model.User;



/**
 * Класс, для преобразования сущности пользователя в Data Transfer Object и обратно
 * @author Mark Valiev
 * @since 03.08.2024
 */
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {

    @Mapping(source = "password", qualifiedByName = "getPasswordHash", target = "passwordDigest")
    User map(UserDTO dto);

    @Mapping(source = "passwordDigest", target = "password")
    UserDTO map(User model);

    @Named("getPasswordHash")
    default String getPasswordHash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}