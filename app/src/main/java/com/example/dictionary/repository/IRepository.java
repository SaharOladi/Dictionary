package com.example.dictionary.repository;

import com.example.dictionary.model.Word;

import java.util.List;
import java.util.UUID;

public interface IRepository {

    List<Word> getWords();
    Word getWord(UUID wordId);
    void insertWord(Word word);
    void updateWord(Word word);
    void deleteWord(Word word);
    int getPosition(UUID wordId);

}
