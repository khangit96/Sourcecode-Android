package com.demonoteapp.Model;

import java.io.Serializable;

/**
 * Created by Administrator on 2/4/2017.
 */

public class Note implements Serializable {
    public int noteId;
    public String noteTitle;
    public String noteContent;

    public Note(String noteTitle, String noteContent) {
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
    }

    public Note() {

    }
}
