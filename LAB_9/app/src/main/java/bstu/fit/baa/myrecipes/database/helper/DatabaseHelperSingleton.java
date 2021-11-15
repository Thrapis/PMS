package bstu.fit.baa.myrecipes.database.helper;

import android.content.Context;

import androidx.room.Room;

public class DatabaseHelperSingleton {

    private static DatabaseHelper database;

    public static DatabaseHelper getDatabaseHelper(Context context) {
        if (database == null)
            database = Room.databaseBuilder(context, DatabaseHelper.class, "database").allowMainThreadQueries().build();
        return database;
    }
}
