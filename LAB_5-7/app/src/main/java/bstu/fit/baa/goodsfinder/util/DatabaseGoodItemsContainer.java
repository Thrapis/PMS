package bstu.fit.baa.goodsfinder.util;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import bstu.fit.baa.goodsfinder.database.DatabaseHelper;
import bstu.fit.baa.goodsfinder.database.DatabaseHelperSingleton;
import bstu.fit.baa.goodsfinder.database.DateTypeConverter;
import bstu.fit.baa.goodsfinder.database.UUIDTypeConverter;
import bstu.fit.baa.goodsfinder.entity.GoodItem;
import bstu.fit.baa.goodsfinder.listener.GoodItemsContainerListener;

public class DatabaseGoodItemsContainer {

    private static GoodItemsSortType sortType = GoodItemsSortType.NO_SORT;
    private static GoodItemsFavoriteSelection favoriteSelection = GoodItemsFavoriteSelection.ALL;
    private static String pattern = "";

    private static List<GoodItemsContainerListener> listeners = new ArrayList<>();

    private static void handleListeners() {
        for (GoodItemsContainerListener listener : listeners) {
            listener.containerChanged();
        }
    }

    public static void addListener(GoodItemsContainerListener listener) {
        listeners.add(listener);
    }

    public static void removeListener(GoodItemsContainerListener listener) {
        listeners.remove(listener);
    }

    public static void addGoodItem(Context context, GoodItem goodItem) {
        DatabaseHelper db = DatabaseHelperSingleton.getDatabaseHelper(context);
        if (goodItem.getId() == null || db.goodItemDao().contains(goodItem.getId())) {
            UUID uuid = UUID.randomUUID();
            goodItem.setId(uuid);
        }
        db.goodItemDao().insert(goodItem);
        handleListeners();
    }

    public static void updateGoodItem(Context context, GoodItem newGoodItem) {
        DatabaseHelper db = DatabaseHelperSingleton.getDatabaseHelper(context);
        if (newGoodItem.getId() != null && db.goodItemDao().contains(newGoodItem.getId())) {
            db.goodItemDao().update(newGoodItem);
        }
        handleListeners();
    }

    public static void removeGoodItem(Context context, UUID id) {
        DatabaseHelper db = DatabaseHelperSingleton.getDatabaseHelper(context);
        //goodItems.removeIf(t -> t.getId() == null);
        if (id != null)
            db.goodItemDao().delete(id);
        handleListeners();
    }

    public static GoodItem getGoodItem(Context context, UUID id) {
        DatabaseHelper db = DatabaseHelperSingleton.getDatabaseHelper(context);
        if (id != null)
            return db.goodItemDao().getById(id);
        else
            throw new NullPointerException();
    }

    public static List<GoodItem> getGoodItemsList(Context context) {
        DatabaseHelper db = DatabaseHelperSingleton.getDatabaseHelper(context);
        List<GoodItem> result = null;
        switch (sortType) {
            case SORT_BY_NAME:
                result = db.goodItemDao().getAllSortedByName();
            case SORT_BY_FIND_DATE:
                result = db.goodItemDao().getAllSortedByFindDate();
            default:
                result = db.goodItemDao().getAll();
        }
        return result;
    }

    public static List<GoodItem> getGoodItemsListByPattern(Context context) {
        DatabaseHelper db = DatabaseHelperSingleton.getDatabaseHelper(context);
        String filter = "(.*)" + pattern.toLowerCase() + "(.*)";
        List<GoodItem> result = new ArrayList<>();
        Cursor c = null;
        switch (sortType) {
            case SORT_BY_NAME:
                //result = db.goodItemDao().getAllSortedByNameWithPattern(pattern);
                c = db.goodItemDao().getAllSortedByNameAsCursor();
                break;
            case SORT_BY_FIND_DATE:
                //result = db.goodItemDao().getAllSortedByFindDateWithPattern(pattern);
                c = db.goodItemDao().getAllSortedByFindDateAsCursor();
                break;
            default:
                //result = db.goodItemDao().getAllWithPattern(pattern);
                c = db.goodItemDao().getAllAsCursor();
        }

        for (boolean hasStart = c.moveToFirst(); hasStart && !c.isAfterLast(); c.moveToNext()) {
            int nameIndex = c.getColumnIndex("name");
            String name = c.getString(nameIndex);
            int favoriteIndex = c.getColumnIndex("favorite");
            boolean favorite = c.getInt(favoriteIndex) == 1;
            if (name.matches(filter) && favoriteSelection.matches(favorite)) {
                GoodItem goodItem = new GoodItem(
                        UUIDTypeConverter.toUUID( c.getString(0)),
                        c.getString(1), c.getString(2), c.getString(3),
                        c.getBlob(4), DateTypeConverter.toDate(c.getLong(5)),
                        c.getString(6), c.getString(7), c.getInt(8) == 1);
                result.add(goodItem);
            }
        }
        return result;
    }

    public static Cursor getGoodItemsListByPatternAsCursor(Context context) {
        DatabaseHelper db = DatabaseHelperSingleton.getDatabaseHelper(context);
        Cursor cursor = null;
        switch (sortType) {
            case SORT_BY_NAME:
                cursor = db.goodItemDao().getAllSortedByNameWithPatternAsCursor(
                        favoriteSelection.getFavoriteStatements(), pattern);
                break;
            case SORT_BY_FIND_DATE:
                cursor = db.goodItemDao().getAllSortedByFindDateWithPatternAsCursor(
                        favoriteSelection.getFavoriteStatements(), pattern);
                break;
            default:
                cursor = db.goodItemDao().getAllWithPatternAsCursor(
                        favoriteSelection.getFavoriteStatements(), pattern);
        }
        return cursor;
    }

    public static GoodItemsSortType getSortType() {
        return sortType;
    }

    public static void setSortType(GoodItemsSortType newSortType) {
        sortType = newSortType;
        handleListeners();
    }

    public static GoodItemsFavoriteSelection getFavoriteSelection() {
        return favoriteSelection;
    }

    public static void setFavoriteSelection(GoodItemsFavoriteSelection favoriteSelection) {
        DatabaseGoodItemsContainer.favoriteSelection = favoriteSelection;
    }

    public static String getPattern() {
        return pattern;
    }

    public static void setPattern(String newPattern) {
        pattern = newPattern;
        handleListeners();
    }
}
