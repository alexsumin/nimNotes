package ru.alexsumin.nimnotes;

import ru.alexsumin.nimnotes.model.Note;

import java.util.List;

public class NoteBean {
    private List<Note> list;


    public List<Note> getList() {
        return list;
    }

    public void setList(List<Note> list) {
        this.list = list;
    }
}
