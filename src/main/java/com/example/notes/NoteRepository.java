package notes;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface NoteRepository extends MongoRepository<Note, String> {
    List<Note> findByOwnerEmail(String email);
    List<Note> findByIsPublicTrue();
}
