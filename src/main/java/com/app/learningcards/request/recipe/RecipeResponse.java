package com.app.learningcards.request.recipe;

import com.app.learningcards.models.recipe.Recipe;
import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
public class RecipeResponse
{
    public Recipe recipe;
    public boolean isFav;
}
