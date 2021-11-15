package bstu.fit.baa.myrecipes.modelview;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import bstu.fit.baa.myrecipes.database.dao.RecipeDao;
import bstu.fit.baa.myrecipes.database.repository.AppRepository;
import bstu.fit.baa.myrecipes.entity.Recipe;
import bstu.fit.baa.myrecipes.literalset.SortType;

public class MainActivityState extends AndroidViewModel {

    private AppRepository repository;
    private LiveData<List<Recipe>> recipes;
    private LocalDateTime creationTime;
    private SortType sortType;

    public MainActivityState(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
        sortType = SortType.NO_SORT;
        recipes = repository.getRecipes(sortType);
        creationTime = LocalDateTime.now();
    }

    public void setSortType(SortType sortType) {
        recipes = repository.getRecipes(sortType);
        this.sortType = sortType;
    }

    public SortType getSortType() {
        return sortType;
    }

    public LiveData<List<Recipe>> getRecipes() {
        recipes = repository.getRecipes(sortType);
        return recipes;
    }

    public void insertRecipe(Recipe recipe) {
        repository.insertRecipe(recipe);
    }

    public void removeRecipe(Recipe recipe) {
        repository.removeRecipe(recipe);
    }

    public void removeRecipe(UUID id) {
        repository.removeRecipe(id);
    }

    public void updateRecipe(Recipe newRecipe) {
        repository.updateRecipe(newRecipe);
    }

    @Override
    public int hashCode() {
        return creationTime.hashCode();
    }
}