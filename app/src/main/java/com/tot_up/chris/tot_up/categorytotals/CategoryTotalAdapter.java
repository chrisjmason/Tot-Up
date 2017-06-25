package com.tot_up.chris.tot_up.categorytotals;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tot_up.chris.tot_up.R;
import com.tot_up.chris.tot_up.data.model.Category;
import com.tot_up.chris.tot_up.util.StringFormatterUtil;

import java.util.List;

public class CategoryTotalAdapter extends RecyclerView.Adapter<CategoryTotalAdapter.ViewHolder> {

    private CategoryTotalInterface.Presenter presenter;
    private Context context;
    private List<Category> categoryList;

    public CategoryTotalAdapter(CategoryTotalInterface.Presenter presenter, Context context) {
        this.presenter = presenter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.totals_card, parent, false);
        return new CategoryTotalAdapter.ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardView cardView = holder.cardView;

        TextView categoryTitle = (TextView) cardView.findViewById(R.id.totals_category_name);
        TextView categoryTotal = (TextView) cardView.findViewById(R.id.totals_category_total);

        Category category = categoryList.get(position);
        String categoryTitleString = category.getName();
        String categoryTotalString = category.getTotal().toString();

        cardView.setOnClickListener((v -> presenter.goToCategory(categoryTitleString)));

        categoryTitle.setText(categoryTitleString);
        categoryTotal.setText(StringFormatterUtil.addCurrencySignToString(categoryTotalString));

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

    //move this to own class
    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;

        public ViewHolder(CardView cv){
            super(cv);
            cardView = cv;
        }
    }

}
