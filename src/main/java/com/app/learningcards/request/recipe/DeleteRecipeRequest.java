package com.app.learningcards.request.recipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteRecipeRequest extends RecipesByCategory
{
    private Long recipeId;
}
