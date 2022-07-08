package RecipeRestService.Recipe.api.mapper;

import org.springframework.stereotype.Component;
import RecipeRestService.Recipe.api.dto.RecipeDTO;
import RecipeRestService.Recipe.domain.entity.Recipe;

@Component
public class RecipeMapper {
    public RecipeDTO toRecipeDTO(Recipe recipe) {
        return RecipeDTO.builder()
                .name(recipe.getName())
                .description(recipe.getDescription())
                .ingredients(recipe.getIngredients())
                .directions(recipe.getDirections())
                .category(recipe.getCategory())
                .date(recipe.getDate())
                .build();
    }
}