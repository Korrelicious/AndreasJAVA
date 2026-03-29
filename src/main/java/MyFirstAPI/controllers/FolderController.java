package MyFirstAPI.controllers;

import MyFirstAPI.dto.FolderDTO;
import MyFirstAPI.models.Folder;
import MyFirstAPI.models.User;
import MyFirstAPI.services.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/folders")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;

    @PostMapping
    public ResponseEntity<FolderDTO> createFolder(
            @RequestParam String name,
            @AuthenticationPrincipal User user) {

        Folder folder = folderService.createFolder(name, user);

        FolderDTO dto = new FolderDTO();
        dto.setId(folder.getId());
        dto.setName(folder.getName());
        dto.add(linkTo(methodOn(FolderController.class).getFolder(folder.getId(), user)).withSelfRel());

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FolderDTO> getFolder(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {

        Folder folder = folderService.getFolderById(id, user);

        FolderDTO dto = new FolderDTO();
        dto.setId(folder.getId());
        dto.setName(folder.getName());
        dto.add(linkTo(methodOn(FolderController.class).getFolder(id, user)).withSelfRel());

        return ResponseEntity.ok(dto);
    }
}