package com.redmadrobot.sample;

import com.redmadrobot.inputmask.MaskedTextChangedListener;
import com.redmadrobot.inputmask.PolyMaskTextChangedListener;
import com.redmadrobot.inputmask.model.Notation;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Home screen for the sample app.
 *
 * @author taflanidi
 */
public final class MainActivity extends Activity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editText = findViewById(R.id.edit_text);
        final List<String> affineFormats = new ArrayList<>();
        affineFormats.add("8 ([000]) [000] [000] [00]");

        final MaskedTextChangedListener listener = new PolyMaskTextChangedListener(
            "+7 ([000]) [000] [00] [00]",
            new ArrayList<Notation>(),
            affineFormats,
            true,
            editText,
            null,
            new MaskedTextChangedListener.ValueListener() {
                @Override
                public void onTextChanged(boolean maskFilled, @NonNull final String extractedValue) {
                    Log.d(MainActivity.class.getSimpleName(), extractedValue);
                    Log.d(MainActivity.class.getSimpleName(), String.valueOf(maskFilled));
                }
            }
        );

        editText.addTextChangedListener(listener);
        editText.setOnFocusChangeListener(listener);

        editText.setHint(listener.placeholder());
    }

}
