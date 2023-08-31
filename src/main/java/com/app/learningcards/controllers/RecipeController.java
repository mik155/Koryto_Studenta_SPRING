package com.app.learningcards.controllers;

import java.util.List;
import java.util.Objects;

import com.app.learningcards.models.User;
import com.app.learningcards.models.recipe.Recipe;
import com.app.learningcards.request.recipe.DeleteRecipeRequest;
import com.app.learningcards.request.recipe.NewRecipeRequest;
import com.app.learningcards.request.recipe.RecipesByCategory;
import com.app.learningcards.services.ImageService;
import com.app.learningcards.services.RecipeService;
import com.app.learningcards.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/api/v1/recipe")
@AllArgsConstructor
public class RecipeController
{
    private RecipeService recipeService;
    private UserService userService;

    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestPart NewRecipeRequest request,
                                               @RequestParam("image") MultipartFile image,
                                               @AuthenticationPrincipal User user)
    {
        String imageName = ImageService.uploadImageToFileSystem(image);

        Recipe recipe = Recipe.builder()
                .name(request.getName())
                .category(request.getCategory())
                .description(request.getDescription())
                .minutesToMake(request.getMinutesToMake())
                .ingriedientNumber(request.getIngredients() != null ? request.getIngredients().size() : 0)
                .likes(0)
                .user(user)
                .imagePath(ImageService.getImagePath(imageName))
                .build();
        recipeService.addIngriedients(recipe, request.getIngredients());
        Recipe newRecipe = recipeService.saveRecipe(recipe);
        return ResponseEntity.ok(newRecipe);
    }

    @GetMapping(path="/all")
    public ResponseEntity<List<Recipe>> getAllRecipes(@AuthenticationPrincipal User user)
    {
        List<Recipe> list = recipeService.getAllRecipes();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping(path="/load")
    public ResponseEntity<List<Recipe>> getRecipesByCategory(@RequestBody RecipesByCategory categories,
                                                             @AuthenticationPrincipal User user)
    {
        List<Recipe> recipeList = recipeService.getRecipes(categories.getCategories());
        return new ResponseEntity<>(recipeList, HttpStatus.OK);
    }

    @PutMapping(path="/description/{recipeId}")
    public ResponseEntity<Recipe>  changeRecipe(@PathVariable("recipeId") Long recipeId,
                                                @RequestParam(value = "description", required = true) String description)
    {
            Recipe recipe = recipeService.getRecipe(recipeId);
            recipe.setDescription(description);
            recipeService.saveRecipe(recipe);
            return ResponseEntity.ok(recipe);
    }

    @PutMapping(path="/like/{recipeId}")
    public void  likeRecipe(@PathVariable("recipeId") Long recipeId, @AuthenticationPrincipal User user)
    {
        Recipe recipe = recipeService.getRecipe(recipeId);
        if(!user.getFavRecipes().contains(recipe))
        {
            recipe.setLikes(recipe.getLikes() + 1);
            user.favRecipes.add(recipe);
            user.setFavRecipes(user.getFavRecipes());
            userService.saveUserData(user);
        }
    }

    @PutMapping(path="/dislike/{recipeId}")
    public void  dislikeRecipe(@PathVariable("recipeId") Long recipeId, @AuthenticationPrincipal User user)
    {

        Recipe recipe = recipeService.getRecipe(recipeId);
        if(user.getFavRecipes().contains(recipe))
        {
            recipe.setLikes(recipe.getLikes() - 1);
            user.favRecipes.removeIf( r -> Objects.equals(r.id, recipe.getId()));
            user.setFavRecipes(user.getFavRecipes());
            recipeService.saveRecipe(recipe);
            userService.saveUserData(user);
        }
    }

    @PostMapping(path="/delete")
    public void deleteRecipe(@RequestBody DeleteRecipeRequest request, @AuthenticationPrincipal User user)
    {
        recipeService.deleteRecipe(request.getRecipeId(), request.getCategories(), user);
    }

    @GetMapping(path="/img/{imageName}",
                produces = MediaType.IMAGE_JPEG_VALUE)
    ResponseEntity<byte []> loadImage(@PathVariable("imageName") String imageName)
    {
        byte [] serializedImage = ImageService.downloadImageFromFileSystem(imageName);
        if(serializedImage == null)
            serializedImage = ImageService.downloadImageFromFileSystem("default.jpg");

        return ResponseEntity.ok(serializedImage);
    }
}
