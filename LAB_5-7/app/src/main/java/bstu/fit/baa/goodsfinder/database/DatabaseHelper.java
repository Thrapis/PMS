package bstu.fit.baa.goodsfinder.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import bstu.fit.baa.goodsfinder.entity.GoodItem;

@Database(entities = {GoodItem.class}, version = 1)
public abstract class DatabaseHelper extends RoomDatabase {
    public abstract GoodItemDao goodItemDao();
}
