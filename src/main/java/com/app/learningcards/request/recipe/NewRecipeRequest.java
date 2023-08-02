package com.app.learningcards.request.recipe;

import com.app.learningcards.models.Ingriedient;
import com.app.learningcards.models.User;
import com.app.learningcards.models.recipe.RecipeCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewRecipeRequest
{
    protected RecipeCategory category;
    protected String name;
    protected String description;
    public List<Ingriedient> ingredients;
    protected int minutesToMake;
}
