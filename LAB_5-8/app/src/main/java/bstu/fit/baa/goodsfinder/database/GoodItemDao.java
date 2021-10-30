package bstu.fit.baa.goodsfinder.database;


import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import java.util.List;
import java.util.UUID;

import bstu.fit.baa.goodsfinder.entity.GoodItem;

@Dao
public interface GoodItemDao {

    @Query("SELECT * FROM GOODITEM")
    List<GoodItem> getAll();

    @Query("SELECT * FROM GOODITEM ORDER BY NAME")
    List<GoodItem> getAllSortedByName();

    @Query("SELECT * FROM GOODITEM ORDER BY FINDDATE")
    List<GoodItem> getAllSortedByFindDate();


    @Query("SELECT * FROM GOODITEM WHERE NAME LIKE '%'||:pattern||'%'")
    List<GoodItem> getAllWithPattern(String pattern);

    @Query("SELECT * FROM GOODITEM WHERE NAME LIKE '%'||:pattern||'%' ORDER BY NAME")
    List<GoodItem> getAllSortedByNameWithPattern(String pattern);

    @Query("SELECT * FROM GOODITEM WHERE NAME LIKE '%'||:pattern||'%' ORDER BY FINDDATE")
    List<GoodItem> getAllSortedByFindDateWithPattern(String pattern);

    @Query("SELECT * FROM GOODITEM WHERE favorite IN (:favoriteStatements) AND NAME LIKE '%'||:pattern||'%'")
    List<GoodItem> getAllWithPattern(int[] favoriteStatements, String pattern);

    @Query("SELECT * FROM GOODITEM WHERE favorite IN (:favoriteStatements) AND NAME LIKE '%'||:pattern||'%' ORDER BY NAME")
    List<GoodItem> getAllSortedByNameWithPattern(int[] favoriteStatements, String pattern);

    @Query("SELECT * FROM GOODITEM WHERE favorite IN (:favoriteStatements) AND NAME LIKE '%'||:pattern||'%' ORDER BY FINDDATE")
    List<GoodItem> getAllSortedByFindDateWithPattern(int[] favoriteStatements, String pattern);

    //------------------------As Cursor--------------------------------

    @Query("SELECT id as _id, name, description, findPlace," +
            " image, findDate, finder, receiptPlace, favorite" +
            " FROM GOODITEM")
    Cursor getAllAsCursor();

    @Query("SELECT id as _id, name, description, findPlace," +
            " image, findDate, finder, receiptPlace, favorite" +
            " FROM GOODITEM ORDER BY NAME")
    Cursor getAllSortedByNameAsCursor();

    @Query("SELECT id as _id, name, description, findPlace," +
            " image, findDate, finder, receiptPlace, favorite" +
            " FROM GOODITEM ORDER BY FINDDATE")
    Cursor getAllSortedByFindDateAsCursor();

    @Query("SELECT id as _id, name, description, findPlace," +
            " image, findDate, finder, receiptPlace, favorite" +
            " FROM GOODITEM WHERE favorite IN (:favoriteStatements)" +
            " AND NAME LIKE '%'||:pattern||'%'")
    Cursor getAllWithPatternAsCursor(int[] favoriteStatements, String pattern);

    @Query("SELECT id as _id, name, description, findPlace," +
            " image, findDate, finder, receiptPlace, favorite" +
            " FROM GOODITEM WHERE  favorite IN (:favoriteStatements) AND" +
            " NAME LIKE '%'||:pattern||'%' ORDER BY NAME")
    Cursor getAllSortedByNameWithPatternAsCursor(int[] favoriteStatements, String pattern);

    @Query("SELECT id as _id, name, description, findPlace," +
            " image, findDate, finder, receiptPlace, favorite" +
            " FROM GOODITEM WHERE  favorite IN (:favoriteStatements) AND" +
            " NAME LIKE '%'||:pattern||'%' ORDER BY FINDDATE")
    Cursor getAllSortedByFindDateWithPatternAsCursor(int[] favoriteStatements, String pattern);

    //-------------------------------------------------------------

    @Query("SELECT * FROM GOODITEM WHERE ID = :id")
    GoodItem getById(@TypeConverters(UUIDTypeConverter.class) UUID id);

    @Query("SELECT COUNT(*) FROM GOODITEM WHERE ID = :id")
    boolean contains(@TypeConverters(UUIDTypeConverter.class) UUID id);

    @Insert
    void insert(GoodItem goodItem);

    @Update
    void update(GoodItem goodItem);

    @Delete
    void delete(GoodItem goodItem);

    @Query("DELETE FROM GOODITEM WHERE ID = :id")
    void delete(@TypeConverters(UUIDTypeConverter.class) UUID id);
}

