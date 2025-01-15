package org.miracle.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.miracle.dto.SessionDTO;
import org.miracle.model.Session;


/**
 * Класс, для преобразования сущности сессии в Data Transfer Object
 * @author Mark Valiev
 * @since 03.08.2024
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SessionMapper {

    SessionDTO map(Session model);

}

