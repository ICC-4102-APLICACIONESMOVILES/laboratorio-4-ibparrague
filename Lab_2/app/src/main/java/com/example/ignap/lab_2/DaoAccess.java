package com.example.ignap.lab_2;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;


@Dao
public interface DaoAccess {

    @Insert
    void insertOnlySingleForm (Form form);
    @Insert
    void insertMultipleForm (List<Form> forms);
    @Query("SELECT * FROM Form WHERE formId = :formId")
    Form fetchOneFormbyFormId (int formId);
    @Update
    void updateForm (Form form);
    @Delete
    void deleteForm (Form form);


    @Query("SELECT * FROM Form ORDER BY formId desc limit 1")
    Form fetchHighestId ();
}