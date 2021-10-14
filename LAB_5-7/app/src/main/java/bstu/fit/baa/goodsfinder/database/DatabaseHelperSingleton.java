package bstu.fit.baa.goodsfinder.database;


import android.content.Context;

import androidx.room.Room;

public class DatabaseHelperSingleton {

    private static DatabaseHelper database;

    public static DatabaseHelper getDatabaseHelper(Context context) {
        if (database == null)
            database = Room.databaseBuilder(context, DatabaseHelper.class, "database").build();

        return database;
    }
}

