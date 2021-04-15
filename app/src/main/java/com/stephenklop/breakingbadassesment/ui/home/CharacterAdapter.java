package com.stephenklop.breakingbadassesment.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stephenklop.breakingbadassesment.R;
import com.stephenklop.breakingbadassesment.domain.Character;

import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {

    // Creating a variable for the array list and context
    private List<Character> mCharacters;
    private Context context;

    // Creating a constructor for our variables
    public CharacterAdapter(List<Character> mCharacters, Context context) {
        this.mCharacters = mCharacters;
        this.context = context;
    }

    // Method for filtering our recyclerview items
    public void filterList(List<Character> filterList) {

        // The line below is to add our filtered list in our ticket array list
        mCharacters = filterList;

        System.out.println(mCharacters);

        // The line below is to notify our adapter to update the recycler view
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // The line below is to inflate our layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_homepage_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Character character = mCharacters.get(position);
    }

    @Override
    public int getItemCount() {
        // Returning the size of the array list
        return mCharacters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize our views with their id's
        }
    }
}
