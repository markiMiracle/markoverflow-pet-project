package org.miracle.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.miracle.dto.AuditDTO;
import org.miracle.model.Audit;

/**
 * Маппер для конвертации auditDTO в сущность БД
 * @author Mark Valiev
 * @since 12.09.2024
 */
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AuditMapper {

    Audit map(AuditDTO dto);

}
