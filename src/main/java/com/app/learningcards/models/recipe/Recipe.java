package com.app.learningcards.models.recipe;

import com.app.learningcards.models.Ingriedient;
import com.app.learningcards.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Recipes")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recipe
{
    @Id
    @SequenceGenerator(
            name = "recipe_sequence",
            sequenceName = "recipe_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "recipe_sequence"
    )
    public Long id;
    @Enumerated(EnumType.STRING)
    protected RecipeCategory category;
    protected String name;
    protected String description;
    protected String imagePath;
    @ManyToMany(
            cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.PERSIST},
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "recipe_ingriedient",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingriedient_id")
    )
    public List<Ingriedient> ingredients;
    protected int minutesToMake;
    protected int ingriedientNumber;
    protected int likes;
    @JsonIgnore
    @ManyToOne(
            cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(
            cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "user_fav_recipe",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> favUsers;
    public boolean equals(Object obj)
    {
        if(! (obj instanceof Recipe other))
            return false;
        return Objects.equals(other.id, id);
    }

    public Recipe addIngriedient(Ingriedient ingriedient)
    {
        if(this.ingredients == null)
            this.ingredients = new LinkedList<Ingriedient>();
        ingredients.add(ingriedient);
        return this;
    }
}