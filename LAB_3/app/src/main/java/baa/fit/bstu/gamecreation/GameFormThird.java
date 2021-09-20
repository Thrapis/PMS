package baa.fit.bstu.gamecreation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Date;

import baa.fit.bstu.gamecreation.util.ActivityCode;
import baa.fit.bstu.gamecreation.util.GameLiteral;

public class GameFormThird extends AppCompatActivity {

    private final String[] Reviews = {"Overwhelmingly Positive", "Very Positive", "Positive",
            "Mostly Positive", "Mixed", "Mostly Negative", "Negative"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_form_third);

        prepareSpinner();
        
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void prepareSpinner() {
        Spinner spinner = findViewById(R.id.game_review);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Reviews);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                overridePendingTransition(R.anim.in_left, R.anim.out_right);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_left, R.anim.out_right);
    }

    public void toNext(View view) {
        DatePicker dateField = findViewById(R.id.game_date);
        Date date = new Date(dateField.getYear(), dateField.getMonth(), dateField.getDayOfMonth());
        Spinner reviewField = findViewById(R.id.game_review);
        EditText versionField = findViewById(R.id.game_version);

        Intent intent = new Intent(this, GameFormFourth.class);
        intent.putExtras(getIntent().getExtras());
        intent.putExtra(GameLiteral.DATE, date);
        intent.putExtra(GameLiteral.VERSION, versionField.getText().toString());
        intent.putExtra(GameLiteral.REVIEW, reviewField.getSelectedItem().toString());

        startActivityForResult(intent, ActivityCode.GAME_RESULT);
        overridePendingTransition(R.anim.in_right, R.anim.out_left);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode){
            case ActivityCode.GAME_RESULT: {
                setResult(ActivityCode.GAME_RESULT, data);
                finish();
                break;
            }
        }
    }
}