package com.redmadrobot.sample;

import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;

import com.redmadrobot.inputmask.MaskedTextChangedListener;
import com.redmadrobot.inputmask.helper.AffinityCalculationStrategy;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Home screen for the sample app.
 *
 * @author taflanidi
 */
public final class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupPrefixSample();
        setupSuffixSample();
    }

    private void setupPrefixSample() {
        final EditText editText = findViewById(R.id.prefix_edit_text);
        final CheckBox checkBox = findViewById(R.id.prefix_check_box);
        final List<String> affineFormats = new ArrayList<>();
        affineFormats.add("8 ([000]) [000]-[00]-[00]");

        final MaskedTextChangedListener listener = MaskedTextChangedListener.Companion.installOn(
            editText,
            "+7 ([000]) [000]-[00]-[00]",
            affineFormats,
            AffinityCalculationStrategy.PREFIX,
            new MaskedTextChangedListener.ValueListener() {
                @Override
                public void onTextChanged(boolean maskFilled, @NonNull final String extractedValue, @NonNull String formattedText) {
                    logValueListener(maskFilled, extractedValue, formattedText);
                    checkBox.setChecked(maskFilled);
                }
            }
        );

        editText.setHint(listener.placeholder());
    }

    private void setupSuffixSample() {
        final EditText editText = findViewById(R.id.suffix_edit_text);
        final CheckBox checkBox = findViewById(R.id.suffix_check_box);
        final List<String> affineFormats = new ArrayList<>();
        affineFormats.add("+7 ([000]) [000]-[00]-[00]#[000]");

        final MaskedTextChangedListener listener = MaskedTextChangedListener.Companion.installOn(
                editText,
                "+7 ([000]) [000]-[00]-[00]",
                affineFormats,
                AffinityCalculationStrategy.WHOLE_STRING,
                new MaskedTextChangedListener.ValueListener() {
                    @Override
                    public void onTextChanged(boolean maskFilled, @NonNull final String extractedValue, @NonNull String formattedText) {
                        logValueListener(maskFilled, extractedValue, formattedText);
                        checkBox.setChecked(maskFilled);
                    }
                }
        );

        editText.setHint(listener.placeholder());
    }

    private void logValueListener(boolean maskFilled, @NonNull String extractedValue, @NonNull String formattedText) {
        final String className = MainActivity.class.getSimpleName();
        Log.d(className, extractedValue);
        Log.d(className, String.valueOf(maskFilled));
        Log.d(className, String.valueOf(formattedText));
    }

}
