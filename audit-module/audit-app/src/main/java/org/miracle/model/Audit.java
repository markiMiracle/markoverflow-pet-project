package org.miracle.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;


/**
 * Сущность БД аудита
 * @author Mark Valiev
 * @since 12.09.2024
 */
@Entity
@Table(name = "audit")
@Data
@Builder
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String userLogin;

    @NotBlank
    private String userAction;

    @NotBlank
    private String ip;

    @NotBlank
    private String device;

    @NotBlank
    private String sessionId;

    private LocalDate createdAt;

}