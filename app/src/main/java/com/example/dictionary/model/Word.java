package com.example.dictionary.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.UUID;

@Entity(tableName = "wordTable")
public class Word implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long primaryId;

    @ColumnInfo(name = "uuid")
    private UUID mId;

    @ColumnInfo(name = "word title")
    private String mTitle;

    @ColumnInfo(name = "word mean")
    private String mMean;


    public Word() {
        this(UUID.randomUUID());
    }

    public Word(UUID id) {
        mId = id;
    }

    public Word(UUID id, String title, String mean) {
        mId = id;
        mTitle = title;
        mMean = mean;
    }

    public Word(String title, String mean) {
        mTitle = title;
        mMean = mean;
    }

    public long getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(long primaryId) {
        this.primaryId = primaryId;
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getMean() {
        return mMean;
    }

    public void setMean(String mean) {
        mMean = mean;
    }
}
