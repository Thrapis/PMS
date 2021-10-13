package bstu.fit.baa.goodsfinder;

import static bstu.fit.baa.goodsfinder.util.IntentCodeLiteral.ADD_NEW_GOOD_ITEM;
import static bstu.fit.baa.goodsfinder.util.IntentCodeLiteral.EDIT_GOOD_ITEM;
import static bstu.fit.baa.goodsfinder.util.IntentCodeLiteral.GOOD_ITEM_RESULT;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.UUID;

import bstu.fit.baa.goodsfinder.entity.GoodItem;
import bstu.fit.baa.goodsfinder.listener.GoodItemsContainerListener;
import bstu.fit.baa.goodsfinder.util.DatabaseGoodItemsContainer;
import bstu.fit.baa.goodsfinder.util.GoodItemsFavoriteSelection;
import bstu.fit.baa.goodsfinder.util.GoodItemsSortType;

public class MainActivity extends AppCompatActivity {

    private Dialog dialog;
    private GoodItemsContainerListener listener;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new Dialog(this);
        dialog.setTitle("Remove good");
        dialog.setContentView(R.layout.dialog_remove);
        dialog.findViewById(R.id.dialog_no).setOnClickListener(view -> {
            dialog.dismiss();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        newStateOfFragments(null);
    }

    public void newStateOfFragments(GoodItem goodItem) {

        GoodItemListFragment listFragment = new GoodItemListFragment();
        listFragment.setCursor(DatabaseGoodItemsContainer.getGoodItemsListByPatternAsCursor(this));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_view, listFragment).commitAllowingStateLoss();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            if (goodItem != null) {
                fragmentTransaction = getSupportFragmentManager().beginTransaction();

                GoodItemDetailFragment detailFragment = new GoodItemDetailFragment();
                fragmentTransaction.replace(R.id.fragment_detail_view, detailFragment);
                detailFragment.setSelectedGoodItem(goodItem);

                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commitAllowingStateLoss();
            }
        }
        else {
            if (goodItem != null) {
                Intent intent = new Intent(this, InfoGoodItem.class);
                intent.putExtra("good", goodItem);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "Info");
        Boolean favorite = (boolean) v.getTag();
        if (!favorite)
            menu.add(0, v.getId(), 0, "Favored");
        else
            menu.add(0, v.getId(), 0, "Unfavored");
        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 0, "Remove");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.no_sort_settings:
                Toast.makeText(this, "No sort", Toast.LENGTH_SHORT).show();
                DatabaseGoodItemsContainer.setSortType(GoodItemsSortType.NO_SORT);
                newStateOfFragments(null);
                return true;
            case R.id.sort_by_name_settings:
                Toast.makeText(this, "Sort by name", Toast.LENGTH_SHORT).show();
                DatabaseGoodItemsContainer.setSortType(GoodItemsSortType.SORT_BY_NAME);
                newStateOfFragments(null);
                return true;
            case R.id.sort_by_find_date_settings:
                Toast.makeText(this, "Sort by find date", Toast.LENGTH_SHORT).show();
                DatabaseGoodItemsContainer.setSortType(GoodItemsSortType.SORT_BY_FIND_DATE);
                newStateOfFragments(null);
                return true;
            case R.id.standard_favorites_settings:
                Toast.makeText(this, "All", Toast.LENGTH_SHORT).show();
                DatabaseGoodItemsContainer.setFavoriteSelection(GoodItemsFavoriteSelection.ALL);
                newStateOfFragments(null);
                return true;
            case R.id.active_favorites_settings:
                Toast.makeText(this, "Favorites", Toast.LENGTH_SHORT).show();
                DatabaseGoodItemsContainer.setFavoriteSelection(GoodItemsFavoriteSelection.FAVORITES);
                newStateOfFragments(null);
                return true;
            case R.id.disactive_favorites_settings:
                Toast.makeText(this, "No favorites", Toast.LENGTH_SHORT).show();
                DatabaseGoodItemsContainer.setFavoriteSelection(GoodItemsFavoriteSelection.NO_FAVORITES);
                newStateOfFragments(null);
                return true;
            /*case R.id.load_settings:
                Toast.makeText(this, "Loaded", Toast.LENGTH_SHORT).show();
                JsonGoodItemsContainer.importFromJson(this);
                newStateOfFragments(null);
                return true;
            case R.id.save_settings:
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                JsonGoodItemsContainer.exportToJson(this);
                return true;*/
            case R.id.add_settings:
                Intent intent = new Intent(this, InsertGoodItemForm.class);
                startActivityForResult(intent, ADD_NEW_GOOD_ITEM);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);
        int idInList = item.getItemId();
        if (item.getTitle() == "Info") {
            GoodItem goodItem = DatabaseGoodItemsContainer.getGoodItemsListByPattern(this).get(idInList);
            if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                Intent intent = new Intent(this, InfoGoodItem.class);
                intent.putExtra("good", goodItem);
                startActivity(intent);
            }
            else {
                newStateOfFragments(goodItem);
            }
        }
        else if (item.getTitle() == "Favored") {
            GoodItem goodItem = DatabaseGoodItemsContainer.getGoodItemsListByPattern(this).get(idInList);
            goodItem.setFavorite(true);
            DatabaseGoodItemsContainer.updateGoodItem(this, goodItem);
            newStateOfFragments(null);
        }
        else if (item.getTitle() == "Unfavored") {
            GoodItem goodItem = DatabaseGoodItemsContainer.getGoodItemsListByPattern(this).get(idInList);
            goodItem.setFavorite(false);
            DatabaseGoodItemsContainer.updateGoodItem(this, goodItem);
            newStateOfFragments(null);
        }
        else if (item.getTitle() == "Edit") {
            GoodItem goodItem = DatabaseGoodItemsContainer.getGoodItemsListByPattern(this).get(idInList);
            Intent intent = new Intent(this, EditGoodItemForm.class);
            intent.putExtra("good", goodItem);
            startActivityForResult(intent, EDIT_GOOD_ITEM);
        }
        else if (item.getTitle() == "Remove") {
            UUID id = DatabaseGoodItemsContainer.getGoodItemsListByPattern(this).get(idInList).getId();

            TextView text = dialog.findViewById(R.id.good_id);
            text.setText(id.toString());
            dialog.findViewById(R.id.dialog_yes).setOnClickListener(view -> {
                DatabaseGoodItemsContainer.removeGoodItem(this, id);
                dialog.dismiss();
                newStateOfFragments(null);
            });
            dialog.show();
        }
        else {
            return  false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.main_menu, menu);
        getMenuInflater().inflate(R.menu.search_menu, menu);
        getMenuInflater().inflate(R.menu.sort_menu, menu);
        getMenuInflater().inflate(R.menu.new_good_menu, menu);



        MenuItem myActionMenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setQuery(DatabaseGoodItemsContainer.getPattern(), false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                DatabaseGoodItemsContainer.setPattern(query);
                newStateOfFragments(null);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                DatabaseGoodItemsContainer.setPattern(newText);
                newStateOfFragments(null);
                return true;
            }
        });

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NEW_GOOD_ITEM && resultCode == GOOD_ITEM_RESULT) {
            GoodItem goodItem = (GoodItem) data.getSerializableExtra("good");
            DatabaseGoodItemsContainer.addGoodItem(this, goodItem);
        }
        else if (requestCode == EDIT_GOOD_ITEM && resultCode == GOOD_ITEM_RESULT) {
            GoodItem goodItem = (GoodItem) data.getSerializableExtra("good");
            DatabaseGoodItemsContainer.updateGoodItem(this, goodItem);
        }
    }
}