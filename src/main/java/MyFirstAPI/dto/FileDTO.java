package MyFirstAPI.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Setter
@Getter
public class FileDTO extends RepresentationModel<FileDTO> {
    private Long id;
    private String name;
    private Long folderId;
}
