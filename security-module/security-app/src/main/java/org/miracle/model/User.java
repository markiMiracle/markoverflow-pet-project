package org.miracle.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

/**
 * Класс, представляющий модель зарегестрированного пользователя
 * @author Mark Valiev
 * @since 02.08.2024
 */
@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(unique = true)
    private String login;

    @NotBlank
    private String passwordDigest;

    @Builder.Default
    private LocalDate updatedAt = LocalDate.now();

    @Builder.Default
    private LocalDate createdAt = LocalDate.now();

}
