package bstu.fit.baa.myrecipes;

import static bstu.fit.baa.myrecipes.literalset.ActivityResultLiteral.ADD_RECIPE;
import static bstu.fit.baa.myrecipes.literalset.ActivityResultLiteral.EDIT_RECIPE;
import static bstu.fit.baa.myrecipes.literalset.ActivityResultLiteral.TAKE_PHOTO;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import bstu.fit.baa.myrecipes.database.dao.RecipeDao;
import bstu.fit.baa.myrecipes.database.helper.DatabaseHelperSingleton;
import bstu.fit.baa.myrecipes.entity.Recipe;
import bstu.fit.baa.myrecipes.literalset.ActivityResultLiteral;
import bstu.fit.baa.myrecipes.literalset.ExtraLiteral;
import bstu.fit.baa.myrecipes.literalset.SortType;
import bstu.fit.baa.myrecipes.modelview.MainActivityState;
import bstu.fit.baa.myrecipes.modelview.factory.MainActivityStateFactory;
import bstu.fit.baa.myrecipes.util.ImageManager;

public class MainActivity extends AppCompatActivity {

    private MainActivityState state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        state = new ViewModelProvider(this, new MainActivityStateFactory(this.getApplication()))
                .get(MainActivityState.class);

        Log.w(" State hash", Integer.valueOf(state.hashCode()).toString());

        state.getRecipes().observe(this, this::updateFragment);
    }

    public void updateFragment(List<Recipe> recipes) {
        Log.w(" SortType", state.getSortType().name());

        DisplayRecipeCardsFragment cardFragment = new DisplayRecipeCardsFragment();
        cardFragment.setList(recipes);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_view, cardFragment).commitAllowingStateLoss();

        ImageManager.clearUnused(this, recipes.stream()
                .map(r -> r.imageUri).collect(Collectors.toList()));
    }

    public void createNew(View view) {
        Intent intent = new Intent(this, CreateRecipeActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.sort_menu, menu);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case ADD_RECIPE: {
                if (data == null) break;
                Recipe recipe = (Recipe) data.getSerializableExtra(ActivityResultLiteral.RECIPE);
                state.insertRecipe(recipe);
                break;
            }
            case EDIT_RECIPE: {
                if (data == null) break;
                Recipe newRecipe = (Recipe) data.getSerializableExtra(ActivityResultLiteral.RECIPE);
                state.updateRecipe(newRecipe);
                break;
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 0, "Remove");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);

        View view = findViewById(item.getItemId());
        UUID id = (UUID) view.getTag();
        switch (item.getTitle().toString()) {
            case "Edit":
                Intent intent = new Intent(this, EditRecipeActivity.class);
                intent.putExtra(ExtraLiteral.RECIPE_ID, id);
                startActivityForResult(intent, 0);
                break;
            case "Remove":
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.remove_recipe);
                builder.setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                    state.removeRecipe(id);
                });
                builder.setNegativeButton(R.string.no, (dialogInterface, i) -> {});
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.no_sort_setting:
                Toast.makeText(this, "No sort", Toast.LENGTH_SHORT).show();
                state.setSortType(SortType.NO_SORT);
                state.getRecipes().removeObservers(this);
                state.getRecipes().observe(this, this::updateFragment);
                return true;
            case R.id.sort_by_name_asc_setting:
                Toast.makeText(this, "Sort by name ascending", Toast.LENGTH_SHORT).show();
                state.setSortType(SortType.NAME_ASC);
                state.getRecipes().removeObservers(this);
                state.getRecipes().observe(this, this::updateFragment);
                return true;
            case R.id.sort_by_name_desc_setting:
                Toast.makeText(this, "Sort by name descending", Toast.LENGTH_SHORT).show();
                state.setSortType(SortType.NAME_DESC);
                state.getRecipes().removeObservers(this);
                state.getRecipes().observe(this, this::updateFragment);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}