package com.opalab.sunshine.app.models;

import com.orm.SugarRecord;

public class Book extends SugarRecord {
    String title;
    String edition;

    public Book() {

    }

    public Book(String title, String edition) {
        this.title = title;
        this.edition = edition;
    }
}
