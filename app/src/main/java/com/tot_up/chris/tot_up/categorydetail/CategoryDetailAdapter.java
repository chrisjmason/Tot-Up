package com.tot_up.chris.tot_up.categorydetail;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tot_up.chris.tot_up.R;
import com.tot_up.chris.tot_up.data.model.Expense;

import java.util.List;


public class CategoryDetailAdapter extends RecyclerView.Adapter<CategoryDetailAdapter.ViewHolder> {

    private List<Expense> expenseList;
    private Context context;
    private CategoryDetailInterface.Presenter presenter;

    public CategoryDetailAdapter(CategoryDetailInterface.Presenter presenter, Context context) {
        this.presenter = presenter;
        this.context = context;
    }

    @Override
    public CategoryDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_card, parent, false);
        return new CategoryDetailAdapter.ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(CategoryDetailAdapter.ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView expensePrice = (TextView) cardView.findViewById(R.id.expense_price_text);
        TextView expenseDate = (TextView) cardView.findViewById(R.id.expense_date_text);
        ImageView expenseImage = (ImageView) cardView.findViewById(R.id.expense_image);

        Expense expense = expenseList.get(position);

        expensePrice.setText(expense.getDecimalPrice().toString());
        expenseDate.setText(expense.getDate());

        Log.d("Image src", expense.getImageSrc());

        Glide.with(context)
                .load(expense.getImageSrc())
                .into(expenseImage);
    }

    @Override
    public int getItemCount() {
        if(expenseList == null){
            return 0;
        }
        return expenseList.size();
    }

    public void setExpenseList(List<Expense> expenseList){
        this.expenseList = expenseList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;

        public ViewHolder(CardView cv){
            super(cv);
            cardView = cv;
        }
    }
}