package MyFirstAPI.services;

import MyFirstAPI.models.Folder;
import MyFirstAPI.models.User;
import MyFirstAPI.repositories.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;

    public Folder createFolder(String name, User user) {
        Folder folder = new Folder();
        folder.setName(name);
        folder.setUser(user);
        return folderRepository.save(folder);
    }

    public Folder getFolderById(Long id, User user) {
        Folder folder = folderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Folder not found"));

        // Kontrollera att mappen tillhör användaren
        if (!folder.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        return folder;
    }
}