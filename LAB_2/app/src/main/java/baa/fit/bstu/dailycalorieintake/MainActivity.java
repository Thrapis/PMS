package baa.fit.bstu.dailycalorieintake;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prepareActivityComboBox();
        prepareListeners();
    }

    private void prepareActivityComboBox() {
        ArrayAdapter<ActivityLevel> adapter = new ArrayAdapter<ActivityLevel>(this,
                android.R.layout.simple_spinner_item, ActivityLevel.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = findViewById(R.id.ActivityComboBox);
        spinner.setAdapter(adapter);
        spinner.setSelection(2);
    }

    private void prepareListeners() {

        CompoundButton.OnCheckedChangeListener radioButtonListener = new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton toggleButton, boolean isChecked) {
                onValueChanged();
            }
        };

        TextWatcher editTextListener = new TextWatcher() {
            public void afterTextChanged(Editable s) { onValueChanged(); }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
        };

        AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) { onValueChanged(); }
            public void onNothingSelected(AdapterView<?> adapterView) { }
        };

        View.OnClickListener buttonListener = new View.OnClickListener() { public void onClick(View view) { calculate(); } };

        RadioButton sexF = findViewById(R.id.FemaleRadioButton);
        RadioButton sexM = findViewById(R.id.MaleRadioButton);
        EditText height = findViewById(R.id.HeightTextNumber);
        EditText age = findViewById(R.id.AgeTextNumber);
        EditText weight = findViewById(R.id.WeightTextNumber);
        Spinner spinner = findViewById(R.id.ActivityComboBox);
        Button calculate = findViewById(R.id.CalculateButton);

        sexF.setOnCheckedChangeListener(radioButtonListener);
        sexM.setOnCheckedChangeListener(radioButtonListener);
        height.addTextChangedListener(editTextListener);
        age.addTextChangedListener(editTextListener);
        weight.addTextChangedListener(editTextListener);
        spinner.setOnItemSelectedListener(spinnerListener);
        calculate.setOnClickListener(buttonListener);
    }

    private void onValueChanged() {
        CheckBox auto = findViewById(R.id.InTimeCheckBox);
        if (auto.isChecked()){
            calculate();
        } else {
            EditText result = findViewById(R.id.ResultTextView);
            result.setText("");
        }
    }

    private void calculate() {
        RadioButton femaleRadio = findViewById(R.id.FemaleRadioButton);
        Spinner activitySpinner = findViewById(R.id.ActivityComboBox);
        EditText heightField = findViewById(R.id.HeightTextNumber);
        EditText ageField = findViewById(R.id.AgeTextNumber);
        EditText weightField = findViewById(R.id.WeightTextNumber);
        EditText resultField = findViewById(R.id.ResultTextView);

        ActivityLevel al = (ActivityLevel)activitySpinner.getSelectedItem();

        if (heightField.getText().length() == 0
                || ageField.getText().length() == 0
                || weightField.getText().length() == 0) {
            return;
        }

        Double height = Double.parseDouble(heightField.getText().toString());
        Double age = Double.parseDouble(ageField.getText().toString());
        Double weight = Double.parseDouble(weightField.getText().toString());

        Double bmr = 1.0;
        if (femaleRadio.isChecked()){
            bmr = 9.247 * weight + 3.098 * height - 4.330 * age + 447.593;
        } else {
            bmr = 13.397 * weight + 4.799 * height - 5.667 * age + 88.362;
        }
        Double result = new Double(Math.round(bmr * al.GetCoefficient() * 100) / 100);

        resultField.setText(result.toString() + " kcal");
    }
}