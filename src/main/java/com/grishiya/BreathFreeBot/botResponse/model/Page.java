package com.grishiya.BreathFreeBot.botResponse.model;

import java.util.Objects;

public class Page {
    private String photoName;
    private String text;

    public Page(String photoName, String text) {
        this.photoName = photoName;
        this.text = text;
    }

    public String getPhotoName() {
        return photoName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page = (Page) o;
        return Objects.equals(photoName, page.photoName) && Objects.equals(text, page.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(photoName, text);
    }

    public String getText() {
        return text;



    }
}
