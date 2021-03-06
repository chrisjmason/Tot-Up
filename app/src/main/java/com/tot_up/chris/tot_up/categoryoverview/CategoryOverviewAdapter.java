package com.tot_up.chris.tot_up.categoryoverview;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tot_up.chris.tot_up.R;
import com.tot_up.chris.tot_up.categorydetail.CategoryDetailActivity;
import com.tot_up.chris.tot_up.data.model.Category;
import com.tot_up.chris.tot_up.util.DateUtil;

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
        Category category = categoryList.get(position);

        categoryTitle.setText(category
                .getName());

        categoryDate.setText(DateUtil.getDifference(category.getDate()));

        cardView.setOnClickListener(v -> presenter.goToDetail(category));

        cardView.setOnLongClickListener(v -> openDeleteDialog(position));

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

    private boolean openDeleteDialog(int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete")
                .setMessage("Are you sure you want to delete?")
                .setPositiveButton("Yes", (dialog, which) -> presenter.deleteCategory(position))
                .setNegativeButton("Cancel", ((dialog, which) -> dialog.dismiss()))
                .create()
                .show();

        return true;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;

        public ViewHolder(CardView cv){
            super(cv);
            cardView = cv;
        }
    }
}
