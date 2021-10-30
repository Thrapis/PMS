package bstu.fit.baa.goodsfinder.util;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import bstu.fit.baa.goodsfinder.database.DatabaseHelper;
import bstu.fit.baa.goodsfinder.database.DatabaseHelperSingleton;
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
        List<GoodItem> list = null;
        switch (sortType) {
            case SORT_BY_NAME:
                list = db.goodItemDao().getAllSortedByNameWithPattern(
                        favoriteSelection.getFavoriteStatements(), pattern);
                break;
            case SORT_BY_FIND_DATE:
                list = db.goodItemDao().getAllSortedByFindDateWithPattern(
                        favoriteSelection.getFavoriteStatements(), pattern);
                break;
            default:
                list = db.goodItemDao().getAllWithPattern(
                        favoriteSelection.getFavoriteStatements(), pattern);
        }
        return list;
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
