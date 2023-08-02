package com.app.learningcards.request.recipe;

import java.util.List;
import com.app.learningcards.models.recipe.RecipeCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipesByCategory
{
    protected List<RecipeCategory> categories;
}
