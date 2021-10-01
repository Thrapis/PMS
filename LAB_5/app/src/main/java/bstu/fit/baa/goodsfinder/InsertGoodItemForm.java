package bstu.fit.baa.goodsfinder;

import static bstu.fit.baa.goodsfinder.util.IntentCodeLiteral.GOOD_ITEM_RESULT;
import static bstu.fit.baa.goodsfinder.util.IntentCodeLiteral.TAKE_PHOTO;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.Date;

import bstu.fit.baa.goodsfinder.entitie.GoodItem;

public class InsertGoodItemForm extends AppCompatActivity {

    GoodItem goodItem = new GoodItem();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_good_item_form);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                //overridePendingTransition(R.anim.in_left, R.anim.out_right);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //overridePendingTransition(R.anim.in_left, R.anim.out_right);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case TAKE_PHOTO: {
                if (data == null)
                    break;
                try {
                    Uri selectedImageUri = data.getData();
                    Bitmap bitmap = null;
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    ImageView imageView = findViewById(R.id.good_image);
                    imageView.setImageBitmap(bitmap);
                    goodItem.setImageFromBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public void takePhoto(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), TAKE_PHOTO);
        }
    }

    public void done(View view) {
        EditText nameField = findViewById(R.id.good_name);
        EditText descriptionField = findViewById(R.id.good_description);
        EditText findPlaceField = findViewById(R.id.good_find_place);
        //ImageView imageField = findViewById(R.id.good_image);
        DatePicker findDateField = findViewById(R.id.good_find_date);
        EditText finderField = findViewById(R.id.good_finder);
        EditText receiptPlaceField = findViewById(R.id.good_receipt_place);

        goodItem.setName(nameField.getText().toString());
        goodItem.setDescription(descriptionField.getText().toString());
        goodItem.setFindPlace(findPlaceField.getText().toString());
        goodItem.setFindDate(new Date(findDateField.getYear(),
                findDateField.getMonth(), findDateField.getDayOfMonth()));
        goodItem.setFinder(finderField.getText().toString());
        goodItem.setReceiptPlace(receiptPlaceField.getText().toString());

        Intent intent = new Intent();
        intent.putExtra("good", goodItem);
        setResult(GOOD_ITEM_RESULT, intent);
        finish();
    }
}