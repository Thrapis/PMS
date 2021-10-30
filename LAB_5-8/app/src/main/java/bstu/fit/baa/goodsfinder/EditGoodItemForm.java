package bstu.fit.baa.goodsfinder;

import static bstu.fit.baa.goodsfinder.util.IntentCodeLiteral.GOOD_ITEM_RESULT;
import static bstu.fit.baa.goodsfinder.util.IntentCodeLiteral.TAKE_PHOTO;

import android.app.Dialog;
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
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.Date;

import bstu.fit.baa.goodsfinder.entity.GoodItem;

public class EditGoodItemForm extends AppCompatActivity {

    GoodItem goodItem;
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_good_item_form);

        goodItem = (GoodItem) getIntent().getSerializableExtra("good");

        EditText nameField = findViewById(R.id.good_name);
        EditText descriptionField = findViewById(R.id.good_description);
        EditText findPlaceField = findViewById(R.id.good_find_place);
        ImageView imageField = findViewById(R.id.good_image);
        DatePicker findDateField = findViewById(R.id.good_find_date);
        EditText finderField = findViewById(R.id.good_finder);
        EditText receiptPlaceField = findViewById(R.id.good_receipt_place);

        nameField.setText(goodItem.getName());
        descriptionField.setText(goodItem.getDescription());
        findPlaceField.setText(goodItem.getFindPlace());
        imageField.setImageBitmap(goodItem.getImageAsBitmap());
        Date d = goodItem.getFindDate();
        findDateField.updateDate(d.getYear(), d.getMonth(), d.getDate());
        finderField.setText(goodItem.getFinder());
        receiptPlaceField.setText(goodItem.getReceiptPlace());

        dialog = new Dialog(this);
        dialog.setTitle("Edit good");
        dialog.setContentView(R.layout.dialog_edit);
        dialog.findViewById(R.id.dialog_no).setOnClickListener(view -> {
            dialog.dismiss();
        });
        TextView text = dialog.findViewById(R.id.good_id);
        text.setText(goodItem.getId().toString());

        dialog.findViewById(R.id.dialog_yes).setOnClickListener(view -> {
            dialog.dismiss();

            //String name = nameField.getText() != null ? nameField.getText().toString() : "";

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
        });


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

    public void edit(View button) {
        dialog.show();
    }
}