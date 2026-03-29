package MyFirstAPI.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Setter
@Getter
public class FolderDTO extends RepresentationModel<FolderDTO> {
    private Long id;
    private String name;
}
