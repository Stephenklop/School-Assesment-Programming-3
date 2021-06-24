package com.stephenklop.breakingbadassesment.ui.details;

import android.content.Context;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stephenklop.breakingbadassesment.domain.Quote;

import java.util.List;

public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.ViewHolder> {

    // Creating variables for the array list and context
    private List<Quote> mQuotes;
    private Context context;

    // Creating a constructor for our variables
    public QuoteAdapter(List<Quote> mQuotes, Context context) {
        this.mQuotes = mQuotes;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



//        View view = LayoutInflater.from(parent.getContext()).inflate();
//        return new ViewHolder(view);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull QuoteAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mQuotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
