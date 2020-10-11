package com.example.dictionary.database;

import androidx.room.TypeConverter;

import java.util.UUID;

public class Converters {

    @TypeConverter
    public static UUID stringToUUID(String value){
        return UUID.fromString(value);
    }

    @TypeConverter
    public static String UUIDToString(UUID uuid){
        return uuid.toString();
    }
}
