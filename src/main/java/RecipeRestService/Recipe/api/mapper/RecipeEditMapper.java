package RecipeRestService.Recipe.api.mapper;

import org.springframework.stereotype.Component;
import RecipeRestService.Recipe.api.dto.RecipeDTO;
import RecipeRestService.Recipe.domain.entity.Recipe;

import java.time.LocalDateTime;

@Component
public class RecipeEditMapper {

    public Recipe updateFromDTO(Recipe recipe, Recipe newRecipeForUpdate) {
        recipe.setCategory(newRecipeForUpdate.getCategory());
        recipe.setName(newRecipeForUpdate.getName());
        recipe.setDate(LocalDateTime.now());
        recipe.setDescription(newRecipeForUpdate.getDescription());
        recipe.setIngredients(newRecipeForUpdate.getIngredients());
        recipe.setDirections(newRecipeForUpdate.getDirections());
        return recipe;
    }
}
