package com.example.dictionary.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dictionary.model.Word;

import java.util.List;
import java.util.UUID;

@Dao
public interface WordDAO {

    @Update
    void updateWord(Word word);

    @Insert
    void insertWord(Word word);

    @Delete
    void deleteWord(Word word);

    @Insert
    void insertWords(Word... word);

    @Query("SELECT * FROM wordTable")
    List<Word> getWords();

    @Query("SELECT * FROM wordTable WHERE uuid=:inputId")
    Word getWord(UUID inputId);
    
}
