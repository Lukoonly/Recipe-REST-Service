package RecipeRestService.Recipe.api.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class RecipeDTO {
    private String name;
    private String description;
    private String category;
    private List<String> ingredients;
    private List<String> directions;
    private LocalDateTime date;
}