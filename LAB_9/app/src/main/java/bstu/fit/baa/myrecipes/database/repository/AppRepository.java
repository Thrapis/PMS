package bstu.fit.baa.myrecipes.database.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.UUID;

import bstu.fit.baa.myrecipes.database.dao.RecipeDao;
import bstu.fit.baa.myrecipes.database.helper.DatabaseHelper;
import bstu.fit.baa.myrecipes.entity.Recipe;
import bstu.fit.baa.myrecipes.literalset.SortType;

public class AppRepository {

    private RecipeDao recipeDao;

    public AppRepository(Application application) {
        DatabaseHelper database = DatabaseHelper.getInstance(application);
        recipeDao = database.recipeDao();
    }

    public LiveData<List<Recipe>> getRecipes(SortType sortType) {
        switch (sortType) {
            case NAME_ASC: return recipeDao.getAllSortedByNameAsc();
            case NAME_DESC: return recipeDao.getAllSortedByNameDesc();
            default: return recipeDao.getAll();
        }
    }

    public void insertRecipe(Recipe recipe) {
        new InsertRecipeAsyncTask(recipeDao).execute(recipe);
    }

    public void removeRecipe(Recipe recipe) {
        new RemoveRecipeAsyncTask(recipeDao).execute(recipe);
    }

    public void removeRecipe(UUID id) {
        new RemoveRecipeAsyncTaskUUID(recipeDao).execute(id);
    }

    public void updateRecipe(Recipe newRecipe) {
        new UpdateRecipeAsyncTask(recipeDao).execute(newRecipe);
    }

    private static class InsertRecipeAsyncTask extends AsyncTask<Recipe, Void, Void> {
        private RecipeDao recipeDao;
        public InsertRecipeAsyncTask(RecipeDao recipeDao) {
            this.recipeDao = recipeDao;
        }
        @Override
        protected Void doInBackground(Recipe... recipes) {
            recipeDao.insert(recipes[0]);
            return null;
        }
    }

    private static class RemoveRecipeAsyncTask extends AsyncTask<Recipe, Void, Void> {
        private RecipeDao recipeDao;
        public RemoveRecipeAsyncTask(RecipeDao recipeDao) {
            this.recipeDao = recipeDao;
        }
        @Override
        protected Void doInBackground(Recipe... recipes) {
            recipeDao.delete(recipes[0]);
            return null;
        }
    }

    private static class RemoveRecipeAsyncTaskUUID extends AsyncTask<UUID, Void, Void> {
        private RecipeDao recipeDao;
        public RemoveRecipeAsyncTaskUUID(RecipeDao recipeDao) {
            this.recipeDao = recipeDao;
        }
        @Override
        protected Void doInBackground(UUID... uuids) {
            recipeDao.delete(uuids[0]);
            return null;
        }
    }

    private static class UpdateRecipeAsyncTask extends AsyncTask<Recipe, Void, Void> {
        private RecipeDao recipeDao;
        public UpdateRecipeAsyncTask(RecipeDao recipeDao) {
            this.recipeDao = recipeDao;
        }
        @Override
        protected Void doInBackground(Recipe... recipes) {
            recipeDao.update(recipes[0]);
            return null;
        }
    }
}
