package com.example.dictionary.repository;


import android.content.Context;

import androidx.room.Room;

import com.example.dictionary.database.WordDAO;
import com.example.dictionary.database.WordDataBase;
import com.example.dictionary.model.Word;

import java.util.List;
import java.util.UUID;

public class WordDBRepository implements IRepository {

    private static WordDBRepository sInstance;

    private WordDAO mWordDAO;
    private Context mContext;


    private WordDBRepository(Context context) {

        mContext = context.getApplicationContext();
        WordDataBase wordDataBase = Room.databaseBuilder(mContext,
                WordDataBase.class,
                "word.db")
                .allowMainThreadQueries()
                .build();
        mWordDAO = wordDataBase.getWordDataBaseDAO();
    }


    public static WordDBRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new WordDBRepository(context);
        return sInstance;
    }

    @Override
    public List<Word> getWords() {
        return mWordDAO.getWords();
    }

    @Override
    public Word getWord(UUID wordId) {
        return mWordDAO.getWord(wordId);
    }

    @Override
    public void insertWord(Word word) {
        mWordDAO.insertWord(word);
    }

    @Override
    public void updateWord(Word word) {
        mWordDAO.updateWord(word);
    }

    @Override
    public void deleteWord(Word word) {
        mWordDAO.deleteWord(word);
    }

    @Override
    public int getPosition(UUID wordId) {
        List<Word> words = getWords();
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).getId().equals(wordId))
                return i;
        }
        return -1;
    }
}
