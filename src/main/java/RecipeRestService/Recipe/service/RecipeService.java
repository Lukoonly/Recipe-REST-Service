package RecipeRestService.Recipe.service;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import RecipeRestService.Recipe.domain.Role;
import RecipeRestService.Recipe.domain.exceptions.BadRequestEmailRegistrationException;
import RecipeRestService.Recipe.domain.exceptions.EntityNofFoundIdException;
import RecipeRestService.Recipe.service.impl.UserDetailsImpl;
import RecipeRestService.Recipe.api.mapper.RecipeEditMapper;
import RecipeRestService.Recipe.domain.entity.Recipe;
import RecipeRestService.Recipe.domain.entity.User;
import RecipeRestService.Recipe.domain.exceptions.ForbiddenUserException;
import RecipeRestService.Recipe.domain.repository.RecipeRep;
import RecipeRestService.Recipe.domain.repository.UserRep;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class RecipeService implements UserDetailsService {

    @Autowired
    private Recipe recipe;

    @Autowired
    private RecipeRep recipeRep;

    @Autowired
    private UserRep userRep;

    @Autowired
    private RecipeEditMapper recipeEditMapper;

    @Autowired
    private PasswordEncoder encoder;

    public long saveNewRecipes(Recipe recipe, UserDetailsImpl user) {
        Recipe recipeForSave = Recipe.builder().user(user.getUser())
                .category(recipe.getCategory())
                .date(LocalDateTime.now())
                .description(recipe.getDescription())
                .directions(recipe.getDirections())
                .ingredients(recipe.getIngredients())
                .name(recipe.getName()).build();
        recipeRep.save(recipeForSave);
        return recipeForSave.getId();
    }

    public void saveNewUser(User user) {
        if (userRep.findUserByEmail(user.getEmail()) != null) {
            throw new BadRequestEmailRegistrationException();
        }
        userRep.save(User.builder()
                .email(user.getEmail())
                .password(encoder.encode(user.getPassword()))
                .role(Role.USER.name())
                .build());
    }

    public void deleteRecipeById(long id) {
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals(getRecipeById(id).get().getUser().getEmail())) {
            recipeRep.delete(getRecipeById(id).orElseThrow(EntityNofFoundIdException::new));
        } else {
            throw new ForbiddenUserException();
        }
    }

    public Optional<Recipe> getRecipeById(long id) {
        Optional<Recipe> recipeOpt = recipeRep.findById(id);

        if (recipeOpt.isPresent()) {
            return recipeOpt;
        } else {
            throw new EntityNofFoundIdException();
        }
    }

    public void updateRecipe(long id, Recipe recipe) {
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals(getRecipeById(id).get().getUser().getEmail())) {
            getRecipeById(id)
                    .map(currentRecipe -> recipeEditMapper.updateFromDTO(currentRecipe, recipe))
                    .map(currentRecipe -> recipeRep.save(currentRecipe))
                    .orElseThrow(EntityNofFoundIdException::new);
        } else {
            throw new ForbiddenUserException();
        }
    }

    public List<Recipe> findRecipeByName(String name) {
        return recipeRep.findAllByNameContainingIgnoreCaseOrderByDateDesc(name);
    }

    public List<Recipe> findRecipeByCategory(String category) {
        return recipeRep.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRep.findUserByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Not found: " + email);
        }
        return new UserDetailsImpl(user);
    }
}
