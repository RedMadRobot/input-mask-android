package com.redmadrobot.sample;

import android.graphics.Paint;
import android.graphics.Rect;
import android.icu.number.NumberFormatter;
import android.icu.util.Currency;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.redmadrobot.inputmask.MaskedTextChangedListener;
import com.redmadrobot.inputmask.NumberInputListener;
import com.redmadrobot.inputmask.PhoneInputListener;
import com.redmadrobot.inputmask.helper.AffinityCalculationStrategy;
import com.redmadrobot.inputmask.model.Country;
import com.redmadrobot.inputmask.model.Notation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Home screen for the sample app.
 *
 * @author taflanidi
 */
public final class MainActivity extends AppCompatActivity {
    private PhoneInputListener listener = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupCurrencySample();
        setupDateSample();
        setupPhoneSample();
    }

    private void setupCurrencySample() {
        final EditText editText = findViewById(R.id.currency_edit_text);
        final CheckBox checkBox = findViewById(R.id.currency_check_box);

        final NumberInputListener listener = NumberInputListener.Companion.installOn(
                editText,
                new MaskedTextChangedListener.ValueListener() {
                    @Override
                    public void onTextChanged(boolean maskFilled, @NonNull String extractedValue, @NonNull String formattedValue, @NonNull String tailPlaceholder) {
                        logValueListener(maskFilled, extractedValue, formattedValue);
                        checkBox.setChecked(maskFilled);
                    }
                }
        );

        listener.setFormatter(
            listener.getFormatter().unit(Currency.getInstance("USD"))
        );

        editText.setHint(listener.placeholder());
    }

    private void setupDateSample() {
        final EditText editText = findViewById(R.id.date_edit_text);
        final CheckBox checkBox = findViewById(R.id.date_check_box);
        final TextView placeholder = findViewById(R.id.date_placeholder_text_view);

        final MaskedTextChangedListener listener = MaskedTextChangedListener.Companion.installOn(
                editText,
                "[90].[90].[0000]",
                new ArrayList<String>(),
                new ArrayList<Notation>(),
                AffinityCalculationStrategy.WHOLE_STRING,
                true,
                true,
                null,
                new MaskedTextChangedListener.ValueListener() {
                    @Override
                    public void onTextChanged(boolean maskFilled, @NonNull String extractedValue, @NonNull String formattedValue, @NonNull String tailPlaceholder) {
                        logValueListener(maskFilled, extractedValue, formattedValue);
                        checkBox.setChecked(maskFilled);

                        placeholder.setText(formattedValue.isEmpty() ? "" : tailPlaceholder);

                        Rect textBounds = new Rect();
                        Paint paint = placeholder.getPaint();
                        paint.getTextBounds(formattedValue, 0, formattedValue.length(), textBounds);
                        int width = textBounds.width();

                        ViewGroup.MarginLayoutParams placeholderParams = (ViewGroup.MarginLayoutParams) placeholder.getLayoutParams();
                        placeholderParams.setMarginStart(width + 16); // 16 is an empirical value
                    }
                }
        );

        editText.setHint(listener.placeholder());
    }

    private void setupPhoneSample() {
        final EditText editText = findViewById(R.id.phone_edit_text);
        final CheckBox checkBox = findViewById(R.id.phone_check_box);
        final TextView countriesTextView = findViewById(R.id.countries_text_view);

        listener = PhoneInputListener.Companion.installOn(
            editText,
            true,
            false,
            null,
            new MaskedTextChangedListener.ValueListener() {
                @Override
                public void onTextChanged(boolean maskFilled, @NonNull String extractedValue, @NonNull String formattedValue, @NonNull String tailPlaceholder) {
                    logValueListener(maskFilled, extractedValue, formattedValue);
                    checkBox.setChecked(maskFilled);

                    StringBuilder countriesList = new StringBuilder();
                    if (listener != null) {
                        for (Country country: listener.getComputedCountries()) {
                            countriesList
                                .append(country.getCountryCode())
                                .append(" ")
                                .append(country.getEmoji())
                                .append(" ")
                                .append(country.getName())
                                .append("\n");
                        }
                    }
                    countriesTextView.setText(countriesList.toString());
                }
            }
        );

        editText.setHint(listener.placeholder());
    }

    private void logValueListener(boolean maskFilled, @NonNull String extractedValue, @NonNull String formattedText) {
        final String className = MainActivity.class.getSimpleName();
        Log.d(className, extractedValue);
        Log.d(className, String.valueOf(maskFilled));
        Log.d(className, formattedText);
    }

}
