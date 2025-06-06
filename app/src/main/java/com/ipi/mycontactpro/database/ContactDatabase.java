package com.ipi.mycontactpro.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ipi.mycontactpro.dao.ContactDAO;
import com.ipi.mycontactpro.pojo.Contact;

@Database(entities = {Contact.class}, version = 1)
public abstract class ContactDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "contact";

    public static ContactDatabase getDb(Context context) {
        return Room.databaseBuilder(context, ContactDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
    }

    public abstract ContactDAO contactDAO();
}
