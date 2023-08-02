package com.app.learningcards.services;

import com.app.learningcards.models.Ingriedient;
import com.app.learningcards.models.User;
import com.app.learningcards.models.recipe.Recipe;
import com.app.learningcards.models.recipe.RecipeCategory;
import com.app.learningcards.repository.RecipeRepository;
import com.app.learningcards.request.recipe.RecipeResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RecipeService
{
    private RecipeRepository recipeRepository;
    private IngriedientService ingriedientService;

    public Recipe getRecipe(Long id)
    {
        Recipe recipe = recipeRepository.getReferenceById(id);
        return recipe;
    }
    public List<Recipe> getRecipes(RecipeCategory category)
    {
        return recipeRepository.findByCategory(category);
    }

    public List<Recipe> getRecipes(List<RecipeCategory> categories)
    {
        List<Recipe> recipes = new LinkedList<>();
        if(categories != null)
            categories.forEach( c ->
            {
                recipes.addAll(recipeRepository.findByCategory(c));
            });
        return recipes;
    }

    public Recipe saveRecipe(Recipe recipe)
    {
        return recipeRepository.save(recipe);
    }

    public List<Recipe> getAllRecipes()
    {
        return recipeRepository.findAll();
    }

    public List<Recipe> deleteRecipe(Long recipeId, List<RecipeCategory> categories, User user)
    {
        if(Objects.equals(recipeRepository.getReferenceById(recipeId).getCreatorId(), user.getId()))
            recipeRepository.deleteById(recipeId);
        return getRecipes(categories);
    }

    public void addIngriedients(Recipe recipe, List<Ingriedient> ingriedients)
    {
        ingriedients.forEach(ing -> {
            Ingriedient ingCandidate = ingriedientService.getIngredientByName(ing.getName());
            if(ingCandidate != null)
            {
                recipe.addIngriedient(ingCandidate);
            }
            else
            {
                Ingriedient newIngriedient = new Ingriedient(ing.getName());
                recipe.addIngriedient(newIngriedient);
            }
        });
    }

    public List<Recipe> getFavRecipes(User user)
    {
        return recipeRepository.getFavRecipes(user.getId());
    }
}