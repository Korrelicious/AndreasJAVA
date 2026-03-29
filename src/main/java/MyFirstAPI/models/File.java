package MyFirstAPI.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Lob
    @JsonIgnore
    private byte[] content;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    @JsonIgnore
    private Folder folder;

    public File(String name, byte[] content) {
        this.name = name;
        this.content = content;
    }
}