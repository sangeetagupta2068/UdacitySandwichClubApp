package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView mDisplayOtherNameView, mDisplayPlaceOfOriginView, mDisplayDescriptionView, mDisplayIngredients ;
    ImageView ingredientsIv;
    TextView mDisplayOtherNameLabel,mDisplayPlaceOfOriginLabel, mDisplayDescriptionLabel, mDisplayIngredientsLabel;
    Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);

        mDisplayOtherNameView = findViewById(R.id.also_known_tv);
        mDisplayPlaceOfOriginView = findViewById(R.id.origin_tv);
        mDisplayDescriptionView = findViewById(R.id.description_tv);
        mDisplayIngredients = findViewById(R.id.ingredients_tv);

        mDisplayOtherNameLabel = findViewById(R.id.also_known_label);
        mDisplayDescriptionLabel = findViewById(R.id.description_label);
        mDisplayPlaceOfOriginLabel = findViewById(R.id.origin_label);
        mDisplayIngredientsLabel = findViewById(R.id.ingredients_label);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        Log.d("DetailActivity",sandwich.getMainName());
        populateUI();

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {


        if(sandwich.getAlsoKnownAs() != null && sandwich.getAlsoKnownAs().size() > 0) {
            StringBuilder otherNameStringBuilder = new StringBuilder();
            for (int count = 0; count < sandwich.getAlsoKnownAs().size(); count++) {
                otherNameStringBuilder.append(sandwich.getAlsoKnownAs().get(count) + ",");
            }
            otherNameStringBuilder.deleteCharAt(otherNameStringBuilder.length()-1);
            mDisplayOtherNameView.setText(otherNameStringBuilder.toString());
        }else{
            mDisplayOtherNameView.setVisibility(View.GONE);
            mDisplayOtherNameLabel.setVisibility(View.GONE);
        }

        if (sandwich.getIngredients() != null && sandwich.getIngredients().size() > 0) {
            StringBuilder ingredientStringBuilder = new StringBuilder();
            for (int count = 0; count < sandwich.getIngredients().size(); count++) {
                ingredientStringBuilder.append(sandwich.getIngredients().get(count) + ",");
            }
            ingredientStringBuilder.deleteCharAt(ingredientStringBuilder.length()-1);
            mDisplayIngredients.setText(ingredientStringBuilder.toString());
        } else{
            mDisplayIngredients.setVisibility(View.GONE);
            mDisplayIngredientsLabel.setVisibility(View.GONE);
        }


        mDisplayDescriptionView.setText(sandwich.getDescription());

        if(!sandwich.getPlaceOfOrigin().isEmpty()) {
            mDisplayPlaceOfOriginView.setText(sandwich.getPlaceOfOrigin());
        } else {
            mDisplayPlaceOfOriginView.setVisibility(View.GONE);
            mDisplayPlaceOfOriginLabel.setVisibility(View.GONE);
        }

        Picasso.with(DetailActivity.this)
                .load(sandwich.getImage())
                .into(ingredientsIv);
    }

}
