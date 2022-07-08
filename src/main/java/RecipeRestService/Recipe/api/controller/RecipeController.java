package RecipeRestService.Recipe.api.controller;

import RecipeRestService.Recipe.service.impl.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import RecipeRestService.Recipe.api.dto.IdDTO;
import RecipeRestService.Recipe.api.dto.RecipeDTO;
import RecipeRestService.Recipe.api.mapper.IdMapper;
import RecipeRestService.Recipe.api.mapper.RecipeMapper;
import RecipeRestService.Recipe.domain.entity.Recipe;
import RecipeRestService.Recipe.domain.entity.User;
import RecipeRestService.Recipe.service.RecipeService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RecipeController {

    RecipeMapper recipeMapper;
    IdMapper idMapper;
    RecipeService recipeService;

    public RecipeController(RecipeMapper recipeMapper, IdMapper idMapper, RecipeService recipeService) {
        this.recipeMapper = recipeMapper;
        this.idMapper = idMapper;
        this.recipeService = recipeService;
    }

    @GetMapping("/api/recipe/{id}")
    public RecipeDTO getRecipe(@PathVariable long id) {
        return recipeMapper.toRecipeDTO(recipeService.getRecipeById(id).get());
    }

    @PostMapping("/api/recipe/new")
    public IdDTO postRecipe(@Valid @RequestBody Recipe recipe, @AuthenticationPrincipal UserDetailsImpl user) {
        return idMapper.toIdDTO(recipeService.saveNewRecipes(recipe, user));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/api/recipe/{id}")
    public void deleteRecipe(@PathVariable long id) {
        recipeService.deleteRecipeById(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/api/recipe/{id}")
    public void updateRecipe(@PathVariable long id, @Valid @RequestBody Recipe recipe) {
        recipeService.updateRecipe(id, recipe);
    }

    @RequestMapping(value = "/api/recipe/search", params = "name")
    public List<RecipeDTO> searchRecipesByName(@RequestParam String name) {
        return recipeService.findRecipeByName(name).stream()
                .map(rec -> recipeMapper.toRecipeDTO(rec)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/api/recipe/search", params = "category")
    public List<RecipeDTO> searchRecipesByCategory(@RequestParam String category) {
        return recipeService.findRecipeByCategory(category).stream()
                .map(rec -> recipeMapper.toRecipeDTO(rec)).collect(Collectors.toList());
    }

    @PostMapping(value = "/api/register")
    public void register(@Valid @RequestBody User user) {
        recipeService.saveNewUser(user);
    }
}
