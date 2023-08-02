package com.app.learningcards.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Ingredients",
        uniqueConstraints = {
            @UniqueConstraint(name = "ingriedient_name_unique", columnNames="name")
        }
)
@Data
@NoArgsConstructor
public class Ingriedient
{
    @Id
    @SequenceGenerator(
            name="ingriedient_sequence",
            sequenceName="ingriedient_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ingriedient_sequence"
    )
    @Column(
            name="id",
            updatable = false
    )
    public Long id;
    private String name;

    public Ingriedient(String name)
    {
        this.name = name;
    }
}