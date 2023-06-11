package com.cmpe425.wordguess;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "word_database";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "words";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_WORD = "word";

    private static final String[] WORDS = {
            "Apple", "Angel", "Beach", "Bread", "Chair", "Cloud", "Dance", "Earth", "Fruit", "Ghost",
            "Happy", "House", "Juice", "Light", "Money", "Music", "Ocean", "Party", "Pizza", "Plant",
            "Quiet", "River", "Smile", "Snake", "Space", "Spoon", "Storm", "Sugar", "Sunny", "Sweet",
            "Tiger", "Train", "Water", "World"
    };

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_WORD + " TEXT)";
        db.execSQL(createTableQuery);
        insertWords(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Remove empty onUpgrade function for now
    }

    private void insertWords(SQLiteDatabase db) {
        for (int i = 0; i < WORDS.length; i++) {
            String word = WORDS[i];
            String insertQuery = "INSERT INTO " + TABLE_NAME +
                    " (" + COLUMN_ID + ", " + COLUMN_WORD + ") VALUES (" +
                    i + ", '" + word + "')";
            db.execSQL(insertQuery);
        }
    }
    public String getRandomWord() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + COLUMN_WORD + " FROM " + TABLE_NAME +
                " ORDER BY RANDOM() LIMIT 1";
        Cursor cursor = db.rawQuery(selectQuery, null);
        String word = null;
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_WORD);
            if (columnIndex != -1) {
                word = cursor.getString(columnIndex);
            }
        }
        cursor.close();
        return word;
    }


}
