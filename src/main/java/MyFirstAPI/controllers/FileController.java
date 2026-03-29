package MyFirstAPI.controllers;

import MyFirstAPI.dto.FileDTO;
import MyFirstAPI.models.File;
import MyFirstAPI.models.User;
import MyFirstAPI.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<FileDTO> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam Long folderId,
            @AuthenticationPrincipal User user) throws Exception {

        File savedFile = fileService.uploadFile(
                file.getOriginalFilename(),
                file.getBytes(),
                folderId,
                user
        );

        FileDTO dto = new FileDTO();
        dto.setId(savedFile.getId());
        dto.setName(savedFile.getName());
        dto.setFolderId(folderId);
        dto.add(linkTo(methodOn(FileController.class).downloadFile(savedFile.getId(), user)).withRel("download"));
        dto.add(linkTo(methodOn(FileController.class).deleteFile(savedFile.getId(), user)).withRel("delete"));

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {

        File file = fileService.getFileById(id, user);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(file.getContent());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFile(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {

        fileService.deleteFile(id, user);
        return ResponseEntity.ok().build();
    }
}