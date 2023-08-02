package com.app.learningcards.repository;

import com.app.learningcards.models.recipe.Recipe;
import com.app.learningcards.models.recipe.RecipeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long>
{
    @Query("SELECT r FROM Recipe r WHERE r.category = :category")
    List<Recipe> findByCategory(@Param("category") RecipeCategory category);

    @Query("SELECT u.favRecipes FROM Users u")
    List<Recipe> getFavRecipes(@Param("id") Long id);
}
