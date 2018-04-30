package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.w3c.dom.Text;

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

    private void populateUI(Sandwich sandwich) {
        TextView alsoKnownAs_TextView = (TextView) findViewById(R.id.also_known_tv);
        TextView ingredients_TextView = (TextView) findViewById(R.id.ingredients_tv);
        TextView origin_TextView = (TextView) findViewById(R.id.origin_tv);
        TextView description_TextView = (TextView) findViewById(R.id.description_tv);

        StringBuilder alsoKnownBuilder = new StringBuilder();
        List<String> alsoKnownList = sandwich.getAlsoKnownAs();
        if (alsoKnownList.size() > 0) {
            alsoKnownBuilder.append(alsoKnownList.get(0));
            if (alsoKnownList.size() > 1) {
                for (int i = 1; i < alsoKnownList.size(); i++) {
                    alsoKnownBuilder.append(", ");
                    alsoKnownBuilder.append(alsoKnownList.get(i));
                }
            }
            alsoKnownAs_TextView.setText(alsoKnownBuilder.toString());
        } else {
            alsoKnownAs_TextView.setText("N/A");
        }
        StringBuilder ingredientsBuilder = new StringBuilder();
        List<String> ingredientsList = sandwich.getIngredients();
        if (ingredientsList.size() > 0) {
            ingredientsBuilder.append(ingredientsList.get(0));
            if (ingredientsList.size() > 1) {
                for (int i = 1; i < ingredientsList.size(); i++) {
                    ingredientsBuilder.append(", ");
                    ingredientsBuilder.append(ingredientsList.get(i));
                }
            }
            ingredients_TextView.setText(ingredientsBuilder.toString());
        } else {
            alsoKnownAs_TextView.setText("N/A");
        }
        origin_TextView.setText(sandwich.getPlaceOfOrigin());
        description_TextView.setText(sandwich.getDescription());
    }
}
