package bstu.fit.baa.myrecipes;

import static bstu.fit.baa.myrecipes.literalset.ActivityResultLiteral.TAKE_PHOTO;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.UUID;

import bstu.fit.baa.myrecipes.database.helper.DatabaseHelperSingleton;
import bstu.fit.baa.myrecipes.entity.Recipe;
import bstu.fit.baa.myrecipes.literalset.ActivityResultLiteral;
import bstu.fit.baa.myrecipes.literalset.ExtraLiteral;
import bstu.fit.baa.myrecipes.util.ImageManager;

public class EditRecipeActivity extends AppCompatActivity {

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

        UUID id = (UUID)getIntent().getSerializableExtra(ExtraLiteral.RECIPE_ID);
        recipe = DatabaseHelperSingleton.getDatabaseHelper(this)
                .recipeDao().getById(id);

        Spinner typeField = findViewById(R.id.typeField);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, Recipe.getAvailableTypes());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeField.setAdapter(adapter);

        EditText nameField = findViewById(R.id.nameField);
        ImageView imageField = findViewById(R.id.imageField);
        EditText formulaField = findViewById(R.id.formulaField);

        nameField.setText(recipe.name);
        imageField.setImageURI(Uri.parse(recipe.imageUri));
        formulaField.setText(recipe.formula);
        typeField.setSelection(
                Arrays.asList(Recipe.getAvailableTypes()).indexOf(recipe.type)
        );
    }

    public void done(View view) {
        EditText nameField = findViewById(R.id.nameField);
        EditText formulaField = findViewById(R.id.formulaField);
        Spinner typeField = findViewById(R.id.typeField);

        recipe.name = nameField.getText().toString();
        recipe.formula = formulaField.getText().toString();
        recipe.type = typeField.getSelectedItem().toString();

        //DatabaseHelperSingleton.getDatabaseHelper(this).recipeDao().update(recipe);
        Intent intent = new Intent();
        intent.putExtra(ActivityResultLiteral.RECIPE, recipe);
        setResult(ActivityResultLiteral.EDIT_RECIPE, intent);
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