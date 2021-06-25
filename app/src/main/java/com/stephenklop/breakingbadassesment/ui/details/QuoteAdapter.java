package com.stephenklop.breakingbadassesment.ui.details;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stephenklop.breakingbadassesment.R;
import com.stephenklop.breakingbadassesment.domain.Quote;

import org.w3c.dom.Text;

import java.util.List;

public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.ViewHolder> {
    private final String LOG_TAG = this.getClass().getSimpleName();

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
        Log.d(LOG_TAG, "Create viewholder details page");

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quote, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuoteAdapter.ViewHolder holder, int position) {
        Log.d(LOG_TAG, "Bind viewholder details page");
        Quote quote = mQuotes.get(position);

        // Set value
        holder.quoteValue.setText(quote.getmQuote());
    }

    @Override
    public int getItemCount() {
        Log.d(LOG_TAG, "Get item count of quotes on the detail page");

        // Returning the size of the array list
        return mQuotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // Creating variables for our views
        TextView quoteValue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize our views with their id's
            quoteValue = itemView.findViewById(R.id.item_quote_value);
        }
    }
}
