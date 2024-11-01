package com.example.dz_7;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dz_7.models.Car;
import com.example.dz_7.repository.CarRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchActivity extends AppCompatActivity {
    private AutoCompleteTextView brandInput, modelInput;
    private Spinner yearFromSpinner, yearToSpinner;
    private EditText priceFromInput, priceToInput;
    private Button matchesButton;
    private List<Car> carList = new ArrayList<>();
    private List<Car> filteredCars = new ArrayList<>();  // Для сохранения отфильтрованных автомобилей

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        brandInput = findViewById(R.id.brandInput);
        modelInput = findViewById(R.id.modelInput);
        yearFromSpinner = findViewById(R.id.yearFromSpinner);
        yearToSpinner = findViewById(R.id.yearToSpinner);
        priceFromInput = findViewById(R.id.priceFromInput);
        priceToInput = findViewById(R.id.priceToInput);
        matchesButton = findViewById(R.id.matchesButton);

        // Инициализация списка автомобилей
        initCarList();
        setupAutocompleteFields();
        setupYearSpinners();

        // Добавляем наблюдателей для обновления фильтрации при изменении ввода
        setupSearchListeners();

        matchesButton.setOnClickListener(v -> returnSearchResults());
    }

    private void initCarList() {
        // Получаем список автомобилей (например, из Singleton или Intent)
        carList = CarRepository.getInstance().getCarList();  // Пример получения через Singleton
    }

    private void setupAutocompleteFields() {
        Set<String> brands = new HashSet<>();
        Set<String> models = new HashSet<>();

        for (Car car : carList) {
            brands.add(car.getBrand());
            models.add(car.getModel());
        }

        ArrayAdapter<String> brandAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<>(brands));
        brandInput.setAdapter(brandAdapter);

        ArrayAdapter<String> modelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<>(models));
        modelInput.setAdapter(modelAdapter);
    }

    private void setupYearSpinners() {
        Set<Integer> years = new HashSet<>();
        for (Car car : carList) years.add(car.getYear());

        ArrayAdapter<Integer> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>(years));
        yearFromSpinner.setAdapter(yearAdapter);
        yearToSpinner.setAdapter(yearAdapter);
    }

    private void setupSearchListeners() {
        TextWatcher filterWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchCars();  // Обновляем поиск при изменении
            }
            @Override
            public void afterTextChanged(Editable s) {}
        };

        brandInput.addTextChangedListener(filterWatcher);
        modelInput.addTextChangedListener(filterWatcher);
        priceFromInput.addTextChangedListener(filterWatcher);
        priceToInput.addTextChangedListener(filterWatcher);
    }

    private void searchCars() {
        String selectedBrand = brandInput.getText().toString().trim();
        String selectedModel = modelInput.getText().toString().trim();
        int yearFrom = yearFromSpinner.getSelectedItem() != null ? (int) yearFromSpinner.getSelectedItem() : Integer.MIN_VALUE;
        int yearTo = yearToSpinner.getSelectedItem() != null ? (int) yearToSpinner.getSelectedItem() : Integer.MAX_VALUE;
        int priceFrom = !priceFromInput.getText().toString().isEmpty() ? Integer.parseInt(priceFromInput.getText().toString()) : Integer.MIN_VALUE;
        int priceTo = !priceToInput.getText().toString().isEmpty() ? Integer.parseInt(priceToInput.getText().toString()) : Integer.MAX_VALUE;

        filteredCars.clear();

        for (Car car : carList) {
            if ((!selectedBrand.isEmpty() && !car.getBrand().equalsIgnoreCase(selectedBrand))
                    || (!selectedModel.isEmpty() && !car.getModel().equalsIgnoreCase(selectedModel))
                    || (car.getYear() < yearFrom || car.getYear() > yearTo)
                    || (car.getCost() < priceFrom || car.getCost() > priceTo)) {
                continue;
            }
            filteredCars.add(car);
        }

        // Делаем кнопку кликабельной, если есть совпадения
        matchesButton.setEnabled(!filteredCars.isEmpty());
    }

    private void returnSearchResults() {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra("filteredCars", new ArrayList<>(filteredCars)); // Now compatible
        setResult(RESULT_OK, intent);
        finish();
    }
}
