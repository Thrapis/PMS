package bstu.fit.baa.myrecipes;

import static bstu.fit.baa.myrecipes.literalset.ActivityResultLiteral.TAKE_PHOTO;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import bstu.fit.baa.myrecipes.database.helper.DatabaseHelperSingleton;
import bstu.fit.baa.myrecipes.entity.Recipe;
import bstu.fit.baa.myrecipes.literalset.ActivityResultLiteral;
import bstu.fit.baa.myrecipes.util.ImageManager;

public class CreateRecipeActivity extends AppCompatActivity {

    private final int DIALOG_ID_1 = 1;
    private Recipe recipe = new Recipe();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        Spinner typeField = findViewById(R.id.typeField);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, Recipe.getAvailableTypes());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeField.setAdapter(adapter);
    }

    public void done(View view) {
        EditText nameField = findViewById(R.id.nameField);
        EditText formulaField = findViewById(R.id.formulaField);
        Spinner typeField = findViewById(R.id.typeField);

        recipe.name = nameField.getText().toString();
        recipe.formula = formulaField.getText().toString();
        recipe.type = typeField.getSelectedItem().toString();

        if (recipe.imageUri == null) {
            Drawable defaultDrawable = getResources().getDrawable(R.drawable.dish);
            Bitmap defaultBitmap = ((BitmapDrawable)defaultDrawable).getBitmap();
            recipe.imageUri = ImageManager.saveToInternalStorage(this, defaultBitmap);
        }

        //DatabaseHelperSingleton.getDatabaseHelper(this).recipeDao().insert(recipe);
        Intent intent = new Intent();
        intent.putExtra(ActivityResultLiteral.RECIPE, recipe);
        setResult(ActivityResultLiteral.ADD_RECIPE, intent);
        finish();
    }

    public void takePhoto(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), TAKE_PHOTO);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case TAKE_PHOTO: {
                if (data == null) break;
                Uri selectedImageUri = data.getData();
                String internalUriString = ImageManager.saveToInternalStorage(this, selectedImageUri);
                recipe.imageUri = internalUriString;
                ImageView imageField = findViewById(R.id.imageField);
                imageField.setImageURI(Uri.parse(internalUriString));
                break;
            }
        }
    }
}