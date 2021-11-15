package bstu.fit.baa.myrecipes.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.UUID;

import bstu.fit.baa.myrecipes.converter.UUIDTypeConverter;

@Entity(tableName = "RECIPE")
public class Recipe implements Serializable {
    @PrimaryKey
    @NonNull
    @TypeConverters(UUIDTypeConverter.class)
    public UUID id;
    public String name;
    public String imageUri;
    public String formula;
    public String type;

    public Recipe() {
        id = UUID.randomUUID();
    }


    public static String[] getAvailableTypes() {
        return new String[] { "Balanced", "Low-Carb", "Low-Fat",
                "Medium-Carb", "Medium-Fat", "High-Carb", "High-Fat"};
    }
}
