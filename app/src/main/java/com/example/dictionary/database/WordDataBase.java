package com.example.dictionary.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.dictionary.model.Word;

@Database(entities = Word.class, version = 1)
@TypeConverters({Converters.class})
public abstract class WordDataBase extends RoomDatabase {
    public abstract WordDAO getWordDataBaseDAO();
}
