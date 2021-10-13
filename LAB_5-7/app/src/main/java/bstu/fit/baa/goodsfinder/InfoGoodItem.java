package bstu.fit.baa.goodsfinder;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import bstu.fit.baa.goodsfinder.entity.GoodItem;

public class InfoGoodItem extends AppCompatActivity {

    GoodItem goodItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_good_item);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }

        goodItem = (GoodItem) getIntent().getSerializableExtra("good");

        TextView idField = findViewById(R.id.good_id);
        TextView nameField = findViewById(R.id.good_name);
        TextView descriptionField = findViewById(R.id.good_description);
        TextView findPlaceField = findViewById(R.id.good_find_place);
        //ImageView imageField = findViewById(R.id.good_image);
        TextView findDateField = findViewById(R.id.good_find_date);
        TextView finderField = findViewById(R.id.good_finder);
        TextView receiptPlaceField = findViewById(R.id.good_receipt_place);

        idField.setText(goodItem.getId().toString());
        nameField.setText(goodItem.getName());
        descriptionField.setText(goodItem.getDescription());
        findPlaceField.setText(goodItem.getFindPlace());
        //imageField.setImageBitmap(goodItem.getImageAsBitmap());
        findDateField.setText(goodItem.getFindDateInFormat());
        finderField.setText(goodItem.getFinder());
        receiptPlaceField.setText(goodItem.getReceiptPlace());

        ImageSwitchFragment switchFragment = new ImageSwitchFragment();
        switchFragment.setStartImage(goodItem.getImageAsBitmap());
        getSupportFragmentManager().beginTransaction().replace(R.id.good_image_fragment, switchFragment).commit();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}