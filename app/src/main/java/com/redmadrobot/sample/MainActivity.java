package com.redmadrobot.sample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.redmadrobot.inputmask.MaskedTextChangedListener;
import com.redmadrobot.inputmask.PolyMaskTextChangedListener;

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
        final EditText editText = (EditText) findViewById(R.id.edit_text);
        final List<String> affineFormats = new ArrayList<>();
        affineFormats.add("8 ([000]) [000] [000] [00]");

        final MaskedTextChangedListener listener = new PolyMaskTextChangedListener(
            "+7 ([000]) [000] [00] [00]",
            affineFormats,
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

//        uncomment in case need to disable of showing default softKeyboard, for example when need to use custom keyboard
//        editText.setOnClickListener(listener);
//        listener.showSoftKeyboard(false);
    }

}
