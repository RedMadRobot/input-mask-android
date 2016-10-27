# InputMask

## Description
The library allows to format user input on the fly according to the provided mask and to extract valueable characters.  

To add library to your project simply add the following code to dependencies section of your build.gradle file:

`compile 'com.redmadrobot:inputmask:1.0.0'`

Masks consist of blocks of symbols, which may include:

* `[]` — a block for valueable symbols written by user. 

Square brackets block may contain any number of special symbols:

1. `0` — mandatory digit. For instance, `[000]` mask will allow user to enter three numbers: `123`.
2. `9` — optional digit . For instance, `[00099]` mask will allow user to enter from three to five numbers.
3. `А` — mandatory letter. `[AAA]` mask will allow user to enter three letters: `abc`.
4. `а` — optional letter. `[АААааа]` mask will allow to enter from three to six letters.
5. `_` — mandatory symbol (digit or letter).
6. `-` — optional symbol (digit or letter).

Blocks cannot contain mixed types of symbols; such that, `[000AA]` will cause a mask initialization error.
Instead, the block should be divided: `[000][AA]`.

Symbols outside the square brackets will take a place in the output.
For instance, `+7 ([000]) [000]-[0000]` mask will format the input field to the form of `+7 (123) 456-7890`. 

* `{}` — a block for valueable yet fixed symbols, which could not be altered by the user.

Symbols within the square and curly brackets form an extracted value (valueable characters).
In other words, `[00]-[00]` and `[00]{-}[00]` will format the input to the same form of `12-34`, 
but in the first case the value, extracted by the library, will be equal to `1234`, and in the second case it will result in `12-34`. 

Mask format examples:

1. [00000000000]
2. {401}-[000]-[00]-[00]
3. [000999999]
4. {818}-[000]-[00]-[00]
5. [009999]
6. [A-----------------------------------------------------]
7. [000000000099999]
8. [A_______________________________________________________________]
9. [A__________________________________________________________________] 
10. 8 [0000000000] 
11. [A_____________________________________________________] 
12. [000000000999]
13. 8([000])[000]-[00]-[00]
14. [0000]{-}[00]
15. +1 ([000]) [000] [00] [00]

# Installation
## Gradle

`compile 'com.redmadrobot:inputmask:1.0.0'`

# Usage
## Simple EditText for the phone numbers

Listening to the text change events of `EditText` and simultaneously altering the entered text could be a bit tricky as
long as you need to add, remove and replace symbols intelligently preserving the cursor position.

Thus, the library provides corresponding `MaskedTextChangedListener` class.

`MaskedTextChangedListener` conforms to `TextWatcher` and `OnFocusChangeListener` interfaces and encaspulates logic to process text edit events.
The object might be instantiated via code and then wired with the corresponding `EditText`.

`MaskedTextChangedListener` has his own listener `MaskedTextChangedListener.ValueListener`, which allows capturing extracted value.
All the `TextWatcher` calls from the client `EditText` are forwarded to the decorated `TextWatcher` object (you may provide one when initializing `MaskedTextChangedListener`).

```
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
                public void onExtracted(@NotNull String value) {
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
```

# License

The library is distributed under the MIT [LICENSE](https://opensource.org/licenses/MIT).
