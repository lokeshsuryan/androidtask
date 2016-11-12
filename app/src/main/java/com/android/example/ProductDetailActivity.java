package com.android.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.example.Constants.Constants;

/**
 * Created by Lokesh on 11/11/2016.
 */

public class ProductDetailActivity extends AppCompatActivity implements Constants {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ((TextView) findViewById(R.id.tvTitle)).setText(bundle.getString(TITLE));
            ((TextView) findViewById(R.id.tvProductDesc)).setText(bundle.getString(DESCRIPTION));
        }

    }

}
