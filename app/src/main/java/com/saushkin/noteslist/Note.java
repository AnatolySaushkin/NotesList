package com.saushkin.noteslist;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

public class Note implements Parcelable {
    private String noteTitle;
    private String noteContent;
    private Calendar dateOfCreation;

    public Note(String noteTitle, String noteContent, Calendar dateOfCreation) {
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.dateOfCreation = dateOfCreation;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    protected Note(Parcel in) {
        noteTitle = in.readString();
        noteContent = in.readString();
        dateOfCreation = (Calendar) in.readSerializable();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(noteTitle);
        dest.writeString(noteContent);
        dest.writeSerializable(dateOfCreation);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public Calendar getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Calendar dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }
}
