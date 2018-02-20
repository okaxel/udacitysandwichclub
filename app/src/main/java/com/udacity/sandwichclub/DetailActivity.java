package com.udacity.sandwichclub;

// To identify my work more easily I made remarks everywhere before or around my modifications.

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich uiSandiwch) {

        // It isn't a big trick, I hope it seems to be a good solution.
        TextView tvAlsoKnownAs = findViewById(R.id.tv_also_known_as);
        TextView tvPlaceOfOrigin = findViewById(R.id.tv_place_of_origin);
        TextView tvDescription = findViewById(R.id.tv_description);
        TextView tvIngredients = findViewById(R.id.tv_ingredients);

        // Some additional variables needed to iterate lists.
        List<String> mAka = uiSandiwch.getAlsoKnownAs();
        String mOutput = "";
        if (! mAka.isEmpty()) {
            for (String string:mAka) {
                mOutput += string + "\n";
            }
            mOutput = mOutput.substring(0,mOutput.length()-1);
            tvAlsoKnownAs.setText(mOutput);
        } else {
            tvAlsoKnownAs.setText(R.string.detail_error_message);
        }

        List<String> mIngredients = uiSandiwch.getIngredients();
        mOutput = "";
        if (! mIngredients.isEmpty()) {
            for (String string:mIngredients) {
                mOutput += string + "\n";
            }
            mOutput = mOutput.substring(0,mOutput.length()-1);
            tvIngredients.setText(mOutput);
        } else {
            tvIngredients.setText(R.string.detail_error_message);
        }

        tvPlaceOfOrigin.setText(uiSandiwch.getPlaceOfOrigin());
        tvDescription.setText(uiSandiwch.getDescription());

    }
}
