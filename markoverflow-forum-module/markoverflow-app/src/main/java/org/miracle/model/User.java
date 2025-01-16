package org.miracle.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * Сущность БД пользователя
 * @author Mark Valiev
 * @since 18.09.2024
 */
@Entity(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "author", cascade = CascadeType.MERGE)
    private List<Question> questions;

    @OneToMany(mappedBy = "author", cascade = CascadeType.MERGE)
    private List<Answer> answers;

    @OneToMany(mappedBy = "author", cascade = CascadeType.MERGE)
    private List<Comment> comments;

    @Column(unique = true)
    @NotBlank
    private String login;

}
