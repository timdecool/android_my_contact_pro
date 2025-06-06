package com.ipi.mycontactpro.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ipi.mycontactpro.pojo.Contact;

import java.util.List;

@Dao
public interface ContactDAO {

    @Query("SELECT * FROM contact WHERE id = :id")
    public Contact find(Long id);

    @Query("SELECT * FROM contact")
    public List<Contact> list();

    @Insert
    public void add(Contact... contacts);

    @Update
    public void update(Contact... contacts);

    @Delete
    public void delete(Contact... contacts);



}
