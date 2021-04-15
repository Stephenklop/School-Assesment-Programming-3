package com.stephenklop.breakingbadassesment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.stephenklop.breakingbadassesment.data.APIService;
import com.stephenklop.breakingbadassesment.domain.Character;
import com.stephenklop.breakingbadassesment.ui.home.CharacterAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Create final variables
    private final APIService apiService;

    // Creating variables for our UI components
    private RecyclerView characterRV;

    // Variable for our adapter class and array list
    private CharacterAdapter mAdapter;
    private List<Character> mCharacters;

    public MainActivity() {
        apiService = new APIService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing our variables
        characterRV = findViewById(R.id.homepage_items);

        Thread charactersThread = new Thread(() -> {
            mCharacters = apiService.getAllCharacters();
        });

        Thread adapterThread = new Thread(() -> {
            buildRecyclerView();
        });

        // Start and join the threads
        try {
            charactersThread.start();
            charactersThread.join();

            adapterThread.start();
            adapterThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    // Method to remove keyboard when clicked outside of input field
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    private void filter(String text) {
        // Creating a new array list to filter our data
        ArrayList<Character> filteredList = new ArrayList<>();

        // Running a for loop to compare elements
        for(Character item : mCharacters) {

            // Checking if the given string matches with the name of any character in our recycler view
            if(item.getmName().toLowerCase().contains(text.toLowerCase())) {

                // If the item matches, we are adding it to our filtered list
                filteredList.add(item);
            }
        }

        if(filteredList.isEmpty()) {
            characterRV.setVisibility(View.GONE);
            findViewById(R.id.homepage_items_notfound).setVisibility(View.VISIBLE);
        } else {
            characterRV.setVisibility(View.VISIBLE);
            findViewById(R.id.homepage_items_notfound).setVisibility(View.GONE);
            mAdapter.filterList(filteredList);
        }
    }

    private void buildRecyclerView() {
        // Initializing our adapter class
        mAdapter = new CharacterAdapter(mCharacters, this);

        // Adding layout manager to our recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        characterRV.setHasFixedSize(true);

        // Setting layout manager to our recycler view
        characterRV.setLayoutManager(layoutManager);

        // Connecting adapter to our recycler view
        characterRV.setAdapter(mAdapter);
    }
}