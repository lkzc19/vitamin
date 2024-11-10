package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "e_book")
public class Book extends BaseModel {

    private String title;

    private String author;

    @ColumnDefault("true")
    private Boolean enable;
}