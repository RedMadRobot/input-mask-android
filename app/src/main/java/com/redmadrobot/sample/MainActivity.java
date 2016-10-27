package com.redmadrobot.sample;

import com.redmadrobot.inputmask.MaskedTextChangedListener;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

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
        final EditText editText = (EditText) findViewById(R.id.edit_text);

        final MaskedTextChangedListener listener = new MaskedTextChangedListener(
            "+7 ([000]) [000] [00] [00]",
            true,
            editText,
            null,
            new MaskedTextChangedListener.ValueListener() {
                @Override
                public void onExtracted(String value) {
                    Log.d(MainActivity.class.getSimpleName(), value);
                }

                @Override
                public void onMandatoryCharactersFilled(boolean complete) {
                    Log.d(MainActivity.class.getSimpleName(), String.valueOf(complete));
                }
            }
        );

        editText.addTextChangedListener(listener);
        editText.setOnFocusChangeListener(listener);

        editText.setHint(listener.placeholder());
    }

}
