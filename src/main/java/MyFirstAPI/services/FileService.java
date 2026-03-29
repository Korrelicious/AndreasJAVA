package MyFirstAPI.services;

import MyFirstAPI.models.File;
import MyFirstAPI.models.Folder;
import MyFirstAPI.models.User;
import MyFirstAPI.repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final FolderService folderService;

    public File uploadFile(String fileName, byte[] content, Long folderId, User user) {
        Folder folder = folderService.getFolderById(folderId, user);

        File file = new File();
        file.setName(fileName);
        file.setContent(content);
        file.setFolder(folder);

        return fileRepository.save(file);
    }

    public File getFileById(Long id, User user) {
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));

        // Kontrollera att filen tillhör användaren
        if (!file.getFolder().getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        return file;
    }

    public void deleteFile(Long id, User user) {
        File file = getFileById(id, user);
        fileRepository.deleteById(id);
    }
}