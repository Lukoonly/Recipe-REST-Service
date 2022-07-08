package RecipeRestService.Recipe.api.mapper;

import org.springframework.stereotype.Component;
import RecipeRestService.Recipe.api.dto.IdDTO;

@Component
public class IdMapper {

    public IdDTO toIdDTO(long id) {
        return IdDTO.builder()
                .id(id)
                .build();
    }
}