package RecipeRestService.Recipe.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import RecipeRestService.Recipe.domain.entity.Recipe;

import java.util.List;

@Repository
public interface RecipeRep extends CrudRepository<Recipe, Long> {

    List<Recipe> findAllByNameContainingIgnoreCaseOrderByDateDesc(String name);

    List<Recipe> findAllByCategoryIgnoreCaseOrderByDateDesc(String category);
}