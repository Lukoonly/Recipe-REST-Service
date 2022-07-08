package RecipeRestService.Recipe.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import RecipeRestService.Recipe.domain.entity.User;

@Repository
public interface UserRep extends CrudRepository<User, Long> {
    User findUserByEmail(String email);
}