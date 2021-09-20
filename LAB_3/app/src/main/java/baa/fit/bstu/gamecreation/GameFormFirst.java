package baa.fit.bstu.gamecreation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import baa.fit.bstu.gamecreation.util.ActivityCode;

public class GameFormFirst extends AppCompatActivity {

    private final String NAME = "NAME";
    private final String IMAGE = "IMAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_form_first);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                overridePendingTransition(0, R.anim.out_fade);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.out_fade);
    }

    public void toNext(View view) {
        EditText nameField = findViewById(R.id.game_name);
        ImageView imageField = findViewById(R.id.game_image);

        Bitmap bmp = ((BitmapDrawable)imageField.getDrawable()).getBitmap();;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        Intent intent = new Intent(this, GameFormSecond.class);
        //intent.putExtras(getIntent().getExtras());
        intent.putExtra(NAME, nameField.getText().toString());
        intent.putExtra(IMAGE, byteArray);

        startActivityForResult(intent, ActivityCode.GAME_RESULT);
        overridePendingTransition(R.anim.in_right, R.anim.out_left);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case ActivityCode.PICK_IMAGE: {
                if (data == null)
                    break;
                try {
                    Uri selectedImageUri = data.getData();
                    Bitmap bitmap = null;
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    ImageView imageView = findViewById(R.id.game_image);
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }

        switch (resultCode){
            case ActivityCode.GAME_RESULT: {
                setResult(ActivityCode.GAME_RESULT, data);
                finish();
                break;
            }
        }
    }

    public void selectImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), ActivityCode.PICK_IMAGE);
    }
}