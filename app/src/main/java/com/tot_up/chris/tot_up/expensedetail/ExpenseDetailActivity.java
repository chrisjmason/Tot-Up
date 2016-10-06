package com.tot_up.chris.tot_up.expensedetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tot_up.chris.tot_up.R;
import com.tot_up.chris.tot_up.categorydetail.CategoryDetailActivity;

public class ExpenseDetailActivity extends AppCompatActivity implements ExpenseDetailInterface.View {

    RelativeLayout overlay;
    ImageView imageView;
    TextView priceText;
    String imagePath;
    String expensePrice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_expense_detail);
        getExtras();
        setUpUi();
    }

    private void getExtras(){
        imagePath = getIntent().getStringExtra(CategoryDetailActivity.IMAGE_PATH);
        expensePrice = getIntent().getStringExtra(CategoryDetailActivity.EXPENSE_PRICE);
    }

    private void setUpUi(){
        setUpOverlayLayout();
        setUpImage();
        setUpPriceText();
    }

    private void setUpOverlayLayout(){
        overlay = (RelativeLayout) findViewById(R.id.expense_detail_overlay);
    }

    private void setUpImage(){
        imageView = (ImageView) findViewById(R.id.expense_detail_image);

        imageView.setOnClickListener(v -> {
            if(overlay.getVisibility() == View.GONE){
                overlay.setVisibility(View.VISIBLE);
            }else{
                overlay.setVisibility(View.GONE);
            }
        });

        Glide.with(this)
                .load(imagePath)
                .into(imageView);
    }

    private void setUpPriceText(){
        priceText = (TextView) findViewById(R.id.expense_detail_text);
        priceText.setText(expensePrice);
    }
}
