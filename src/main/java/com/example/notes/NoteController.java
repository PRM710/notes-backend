package notes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*") // allow all origins for frontend testing
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    // Simple login: check if email and password match a dummy user
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        // For assignment: accept any dummy password, e.g., "1234"
        if(password.equals("1234")) return "success";
        return "fail";
    }

    // Create note
    @PostMapping("/create")
    public Note createNote(@RequestBody Note note) {
        return noteRepository.save(note);
    }

    // Get all notes for a user
    @GetMapping("/user/{email}")
    public List<Note> getUserNotes(@PathVariable String email) {
        return noteRepository.findByOwnerEmail(email);
    }

    // Get all public notes
    @GetMapping("/public")
    public List<Note> getPublicNotes() {
        return noteRepository.findByIsPublicTrue();
    }

    // Get note by id (for sharing)
    @GetMapping("/{id}")
    public Optional<Note> getNoteById(@PathVariable String id) {
        return noteRepository.findById(id);
    }

    // Update note
    @PutMapping("/update/{id}")
    public Note updateNote(@PathVariable String id, @RequestBody Note updatedNote) {
        return noteRepository.findById(id).map(note -> {
            note.setTitle(updatedNote.getTitle());
            note.setContent(updatedNote.getContent());
            note.setPublic(updatedNote.isPublic());
            return noteRepository.save(note);
        }).orElse(null);
    }

    // Delete note
    @DeleteMapping("/delete/{id}")
    public String deleteNote(@PathVariable String id) {
        noteRepository.deleteById(id);
        return "deleted";
    }
}
