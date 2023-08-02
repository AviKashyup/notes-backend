package com.avikashyup.notes.controller;

import com.avikashyup.notes.exception.NoteNotFoundException;
import com.avikashyup.notes.model.Note;
import com.avikashyup.notes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000/")
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    @PostMapping("/notes")
    public Note newNote(@RequestBody Note newNote) {
        newNote.setLastModified(LocalDateTime.now());
        return noteRepository.save(newNote);
    }

    @GetMapping("/notes")
    public List<Note> getAllNotes() {
    return noteRepository.findAllByOrderByLastModifiedDesc();
    }

    @GetMapping("/notes/{id}")
    public Note getNoteById(@PathVariable Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));
    }

    @PutMapping("/notes/{id}")
    public Note updateNote(@RequestBody Note newNote, @PathVariable Long id) {
        return noteRepository.findById(id)
                .map(note-> {
                    note.setNoteTitle(newNote.getNoteTitle());
                    note.setNoteBody(newNote.getNoteBody());
                    note.setLastModified(LocalDateTime.now());

                    return noteRepository.save(note);
                }).orElseThrow(()-> new NoteNotFoundException(id));
    }

    @DeleteMapping("/notes/{id}")
    public String deleteNote(@PathVariable Long id) {
        if(!noteRepository.existsById(id)) {
            throw new NoteNotFoundException(id);
        }
        noteRepository.deleteById(id);
        return "Note with Id " + id + " has been deleted";
    }

}
