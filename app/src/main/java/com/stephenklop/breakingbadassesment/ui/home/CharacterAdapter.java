package com.stephenklop.breakingbadassesment.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.stephenklop.breakingbadassesment.R;
import com.stephenklop.breakingbadassesment.domain.Character;
import com.stephenklop.breakingbadassesment.ui.details.DetailsActivity;
import com.stephenklop.breakingbadassesment.ui.details.FullscreenActivity;

import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {

    // Creating a variable for the array list and context
    private List<Character> mCharacters;
    private Context context;

    // Creating a constructor for our variables
    public CharacterAdapter(List<Character> characters, Context context) {
        this.context = context;
        this.mCharacters = characters;
        System.out.println(mCharacters);
    }

    // Method for filtering our recyclerview items
    public void filterList(List<Character> filterList) {

        // The line below is to add our filtered list in our ticket array list
        mCharacters = filterList;

        // The line below is to notify our adapter to update the recycler view
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // The line below is to inflate our layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homepage, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Character character = mCharacters.get(position);

        // Set Image
        Glide.with(context).load(character.getmImg()).into(holder.poster);

        // Set Name
        holder.name.setText(character.getmName());

        // Set Nickname
        holder.nickName.setText(character.getmNickname());

        // Set Status
        holder.status.setText(character.getmStatus());

        // Make image clickable
        holder.poster.setOnClickListener(v -> {
            Intent intent = new Intent(context, FullscreenActivity.class);
            intent.putExtra("posterImage", character.getmImg());
            intent.putExtra("prevActivity", "com.stephenklop.breakingbadassesment.MainActivity");

            context.startActivity(intent);
        });

        // Make text content clickable
        holder.textLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("characterId", character.getmId());
            intent.putExtra("prevActivity", "com.stephenklop.breakingbadassesment.MainActivity");

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        // Returning the size of the array list
        return mCharacters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Creating variables for the views
        ImageView poster;
        TextView name, nickName, status;
        LinearLayout textLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize our views with their id's
            poster = itemView.findViewById(R.id.item_homepage_img_character);
            name = itemView.findViewById(R.id.item_homepage_tv_name);
            nickName = itemView.findViewById(R.id.item_homepage_tv_nickname);
            status = itemView.findViewById(R.id.item_homepage_tv_status);
            textLayout = itemView.findViewById(R.id.item_homepage_text_content);
        }
    }
}
