package com.tot_up.chris.tot_up.categoryoverview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tot_up.chris.tot_up.R;
import com.tot_up.chris.tot_up.categorydetail.CategoryDetailActivity;
import com.tot_up.chris.tot_up.data.model.Category;

import org.w3c.dom.Text;

import java.util.List;
import java.util.function.IntBinaryOperator;

public class CategoryOverviewAdapter extends RecyclerView.Adapter<CategoryOverviewAdapter.ViewHolder> {

    private CategoryOverviewInterface.Presenter presenter;
    private Context context;
    private List<Category> categoryList;

    public CategoryOverviewAdapter(CategoryOverviewInterface.Presenter presenter, Context context){
        this.presenter = presenter;
        this.context = context;
    }

    @Override
    public CategoryOverviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_card, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(CategoryOverviewAdapter.ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView categoryTitle = (TextView) cardView.findViewById(R.id.text_category_name);
        TextView categoryDate = (TextView) cardView.findViewById(R.id.date_text);

        categoryTitle.setText(categoryList
                .get(position)
                .getName());

        categoryDate.setText(categoryList
                .get(position)
                .getDate());

        cardView.setOnClickListener((v) -> {
            Intent intent = new Intent(context, CategoryDetailActivity.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if(categoryList == null){
            return 0;
        }
        return categoryList.size();
    }

    public void setCategoryList(List<Category> categoryList){
        this.categoryList = categoryList;
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
