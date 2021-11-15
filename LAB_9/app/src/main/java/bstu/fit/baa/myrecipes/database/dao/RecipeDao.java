package bstu.fit.baa.myrecipes.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import java.util.List;
import java.util.UUID;

import bstu.fit.baa.myrecipes.converter.UUIDTypeConverter;
import bstu.fit.baa.myrecipes.entity.Recipe;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM RECIPE")
    LiveData<List<Recipe>> getAll();

    @Query("SELECT * FROM RECIPE ORDER BY NAME ASC")
    LiveData<List<Recipe>> getAllSortedByNameAsc();

    @Query("SELECT * FROM RECIPE ORDER BY NAME DESC")
    LiveData<List<Recipe>> getAllSortedByNameDesc();

    @Query("SELECT * FROM RECIPE WHERE ID = :id")
    Recipe getById(@TypeConverters(UUIDTypeConverter.class) UUID id);

    @Query("SELECT COUNT(*) FROM RECIPE WHERE ID = :id")
    boolean contains(@TypeConverters(UUIDTypeConverter.class) UUID id);

    @Insert
    void insert(Recipe goodItem);

    @Update
    void update(Recipe goodItem);

    @Delete
    void delete(Recipe goodItem);

    @Query("DELETE FROM RECIPE WHERE ID = :id")
    void delete(@TypeConverters(UUIDTypeConverter.class) UUID id);
}
