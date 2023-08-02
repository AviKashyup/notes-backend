package com.avikashyup.notes.exception;

public class NoteNotFoundException extends RuntimeException{
    public NoteNotFoundException(Long Id) {
        super("Could not found Note with ID: " + Id);
    }
}
