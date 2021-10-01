package bstu.fit.baa.goodsfinder;

import static bstu.fit.baa.goodsfinder.util.IntentCodeLiteral.ADD_NEW_GOOD_ITEM;
import static bstu.fit.baa.goodsfinder.util.IntentCodeLiteral.EDIT_GOOD_ITEM;
import static bstu.fit.baa.goodsfinder.util.IntentCodeLiteral.GOOD_ITEM_RESULT;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

import bstu.fit.baa.goodsfinder.adapter.GoodAdapter;
import bstu.fit.baa.goodsfinder.entitie.GoodItem;
import bstu.fit.baa.goodsfinder.util.GoodItemsContainer;
import bstu.fit.baa.goodsfinder.util.GoodItemsSortType;

public class MainActivity extends AppCompatActivity {

    Dialog dialog;
    String pattern = "";

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

        updateList();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("pattern", pattern);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pattern = savedInstanceState.getString("pattern");
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "Info");
        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 0, "Remove");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int idInList = item.getItemId();
        if (item.getTitle() == "Info") {
            GoodItem goodItem = GoodItemsContainer.getGoodItemsList().get(idInList);
            Intent intent = new Intent(this, InfoGoodItem.class);
            intent.putExtra("good", goodItem);
            startActivity(intent);
        }
        else if (item.getTitle() == "Edit") {
            GoodItem goodItem = GoodItemsContainer.getGoodItemsList().get(idInList);
            Intent intent = new Intent(this, EditGoodItemForm.class);
            intent.putExtra("good", goodItem);
            startActivityForResult(intent, EDIT_GOOD_ITEM);
            updateList();
        }
        else if (item.getTitle() == "Remove") {
            UUID id = GoodItemsContainer.getGoodItemsList().get(idInList).getId();

            TextView text = dialog.findViewById(R.id.good_id);
            text.setText(id.toString());
            dialog.findViewById(R.id.dialog_yes).setOnClickListener(view -> {
                GoodItemsContainer.removeGoodItem(id);
                dialog.dismiss();
                updateList();
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
        getMenuInflater().inflate(R.menu.main_menu, menu);
        getMenuInflater().inflate(R.menu.sort_menu, menu);
        getMenuInflater().inflate(R.menu.new_good_menu, menu);

        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Toast.makeText(MainActivity.this, "TextSubmit: " + query, Toast.LENGTH_SHORT).show();
                pattern = query;
                updateList();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Toast.makeText(MainActivity.this, "TextChange: " + newText, Toast.LENGTH_SHORT).show();
                pattern = newText;
                updateList();
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.no_sort_settings:
                Toast.makeText(this, "No sort", Toast.LENGTH_SHORT).show();
                GoodItemsContainer.setSortType(GoodItemsSortType.NO_SORT);
                updateList();
                return true;
            case R.id.sort_by_name_settings:
                Toast.makeText(this, "No sort", Toast.LENGTH_SHORT).show();
                GoodItemsContainer.setSortType(GoodItemsSortType.SORT_BY_NAME);
                updateList();
                return true;
            case R.id.sort_by_find_date_settings:
                Toast.makeText(this, "No sort", Toast.LENGTH_SHORT).show();
                GoodItemsContainer.setSortType(GoodItemsSortType.SORT_BY_FIND_DATE);
                updateList();
                return true;
            case R.id.load_settings:
                Toast.makeText(this, "Loaded", Toast.LENGTH_SHORT).show();
                GoodItemsContainer.importFromJson(this);
                updateList();
                return true;
            case R.id.save_settings:
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                GoodItemsContainer.exportToJson(this);
                updateList();
                return true;
            case R.id.add_settings:
                Intent intent = new Intent(this, InsertGoodItemForm.class);
                startActivityForResult(intent, ADD_NEW_GOOD_ITEM);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void updateList() {
        ListView list = findViewById(R.id.itemsList);
        list.removeAllViewsInLayout();
        GoodAdapter adapter = new GoodAdapter(this, GoodItemsContainer.getGoodItemsList(pattern));
        list.setAdapter(adapter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NEW_GOOD_ITEM && resultCode == GOOD_ITEM_RESULT) {
            GoodItem goodItem = (GoodItem) data.getSerializableExtra("good");
            GoodItemsContainer.addGoodItem(goodItem);
            updateList();
        }
        else if (requestCode == EDIT_GOOD_ITEM && resultCode == GOOD_ITEM_RESULT) {
            GoodItem goodItem = (GoodItem) data.getSerializableExtra("good");
            GoodItemsContainer.updateGoodItem(goodItem);
            updateList();
        }
    }
}