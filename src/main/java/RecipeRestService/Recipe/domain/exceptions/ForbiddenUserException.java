package RecipeRestService.Recipe.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenUserException extends RuntimeException {

    public ForbiddenUserException() {
    }
}