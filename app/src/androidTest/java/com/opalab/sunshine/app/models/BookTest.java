package com.opalab.sunshine.app.models;

import android.test.suitebuilder.annotation.MediumTest;

import com.orm.SugarRecord;

import junit.framework.TestCase;

public class BookTest extends TestCase {

    @MediumTest
    public void testBooks() {
        SugarRecord.deleteAll(Book.class);

        Book a = new Book("Oto Book", "1st edition");
        a.save();

        long result = (new Book("x Book", "test")).save();
        assertEquals(2, Book.count(Book.class));
    }
}
