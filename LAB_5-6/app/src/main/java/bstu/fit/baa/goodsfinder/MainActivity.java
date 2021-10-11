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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.UUID;

import bstu.fit.baa.goodsfinder.entity.GoodItem;
import bstu.fit.baa.goodsfinder.listener.GoodItemsContainerListener;
import bstu.fit.baa.goodsfinder.util.GoodItemsContainer;
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

        listener = this::updateFragments;
        GoodItemsContainer.addListener(listener);

        orientationCheck();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateFragments();
    }

    private void orientationCheck() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            /*Fragment details = getSupportFragmentManager().findFragmentById(R.id.fragment_detail_view);
            if (GoodItemsContainer.peekSelectedGoodItem() != null) {
                getSupportFragmentManager().beginTransaction().attach(details).commitAllowingStateLoss();
            }
            else {
                getSupportFragmentManager().beginTransaction().detach(details).commitAllowingStateLoss();
            }*/
        }
        else {
            if (GoodItemsContainer.peekSelectedGoodItem() != null) {
                Intent intent = new Intent(this, InfoGoodItem.class);
                intent.putExtra("good", GoodItemsContainer.peekSelectedGoodItem());
                startActivity(intent);
            }
        }
    }

    private void updateFragments() {
        /*List<GoodItem> list = GoodItemsContainer.getGoodItemsListByPattern();
        GoodItem selected = GoodItemsContainer.getSelectedGoodItem();

        GoodItemListFragment listFragment =
                (GoodItemListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);

        GoodItemDetailFragment detailFragment =
                (GoodItemDetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_detail_view);

        listFragment.updateList(list);*/



        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            Fragment details = getSupportFragmentManager().findFragmentById(R.id.fragment_detail_view);
            Fragment list = getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
            getSupportFragmentManager().beginTransaction().detach(list).commitAllowingStateLoss();
            getSupportFragmentManager().beginTransaction().attach(list).commitAllowingStateLoss();



            if (GoodItemsContainer.peekSelectedGoodItem() == null) {
                getSupportFragmentManager().beginTransaction().detach(details).commitAllowingStateLoss();
            }
            else {
                getSupportFragmentManager().beginTransaction().detach(details).commitAllowingStateLoss();
                getSupportFragmentManager().beginTransaction().attach(details).commitAllowingStateLoss();
            }
            //fTrans.addToBackStack(null);
        }
        else {
            if (GoodItemsContainer.peekSelectedGoodItem() != null) {
                Intent intent = new Intent(this, InfoGoodItem.class);
                intent.putExtra("good", GoodItemsContainer.peekSelectedGoodItem());
                startActivity(intent);
            }
        }

        /*if (selected != null && list.contains(selected)){
            int index = list.indexOf(selected);
            listFragment.switchSelection(index);

            if (detailFragment != null)
                detailFragment.setSelectedGoodItem(selected);
        }*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GoodItemsContainer.removeListener(listener);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        GoodItemsContainer.popSelectedGoodItem();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "Info");
        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 0, "Remove");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.no_sort_settings:
                Toast.makeText(this, "No sort", Toast.LENGTH_SHORT).show();
                GoodItemsContainer.setSortType(GoodItemsSortType.NO_SORT);
                return true;
            case R.id.sort_by_name_settings:
                Toast.makeText(this, "No sort", Toast.LENGTH_SHORT).show();
                GoodItemsContainer.setSortType(GoodItemsSortType.SORT_BY_NAME);
                return true;
            case R.id.sort_by_find_date_settings:
                Toast.makeText(this, "No sort", Toast.LENGTH_SHORT).show();
                GoodItemsContainer.setSortType(GoodItemsSortType.SORT_BY_FIND_DATE);
                return true;
            case R.id.load_settings:
                Toast.makeText(this, "Loaded", Toast.LENGTH_SHORT).show();
                GoodItemsContainer.importFromJson(this);
                return true;
            case R.id.save_settings:
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                GoodItemsContainer.exportToJson(this);
                return true;
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
            GoodItem goodItem = GoodItemsContainer.getGoodItemsList().get(idInList);
            GoodItemsContainer.pushSelectedGoodItem(goodItem);
            if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                Intent intent = new Intent(this, InfoGoodItem.class);
                intent.putExtra("good", goodItem);
                startActivity(intent);
            }
        }
        else if (item.getTitle() == "Edit") {
            GoodItem goodItem = GoodItemsContainer.getGoodItemsList().get(idInList);
            Intent intent = new Intent(this, EditGoodItemForm.class);
            intent.putExtra("good", goodItem);
            startActivityForResult(intent, EDIT_GOOD_ITEM);
        }
        else if (item.getTitle() == "Remove") {
            UUID id = GoodItemsContainer.getGoodItemsList().get(idInList).getId();

            TextView text = dialog.findViewById(R.id.good_id);
            text.setText(id.toString());
            dialog.findViewById(R.id.dialog_yes).setOnClickListener(view -> {
                GoodItemsContainer.removeGoodItem(id);
                dialog.dismiss();
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
                GoodItemsContainer.setPattern(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                GoodItemsContainer.setPattern(newText);
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
            GoodItemsContainer.addGoodItem(goodItem);
        }
        else if (requestCode == EDIT_GOOD_ITEM && resultCode == GOOD_ITEM_RESULT) {
            GoodItem goodItem = (GoodItem) data.getSerializableExtra("good");
            GoodItemsContainer.updateGoodItem(goodItem);
        }
    }
}