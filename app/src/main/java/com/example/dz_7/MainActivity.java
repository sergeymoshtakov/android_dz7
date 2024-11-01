package com.example.dz_7;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.dz_7.adapters.CarAdapter;
import com.example.dz_7.models.Car;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int SEARCH_REQUEST_CODE = 1;
    private List<Car> carList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CarAdapter carAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        initCarList();

        carAdapter = new CarAdapter(carList);
        recyclerView.setAdapter(carAdapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();
        if (id == R.id.action_search) {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivityForResult(intent, SEARCH_REQUEST_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initCarList() {
        carList.add(new Car("Toyota", "Corolla", 2010, "Well maintained", 5000, R.drawable.toyota_corolla));
        carList.add(new Car("Honda", "Civic", 2012, "Sporty model", 7000, R.drawable.honda_civic));
        // Add more cars as needed
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SEARCH_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<Car> filteredCars = data.getParcelableArrayListExtra("filteredCars");
            if (filteredCars != null) {
                carAdapter = new CarAdapter(filteredCars);
                recyclerView.setAdapter(carAdapter);
            }
        }
    }
}
