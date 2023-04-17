<img src="Documentation/Assets/logo.png" alt="Input Mask" height="102" />

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Input%20Mask-brightgreen.svg?style=for-the-badge)](https://android-arsenal.com/details/1/4642) [![](https://img.shields.io/jitpack/version/com.redmadrobot/input-mask-android?style=for-the-badge)](https://jitpack.io/#RedMadRobot/input-mask-android) [![Awesome](https://img.shields.io/badge/-mentioned_in_awesome_android-CCA6C4.svg?colorA=CCA6C4&colorB=261120&logoWidth=20&logo=data%3Aimage%2Fsvg%2Bxml%3Bbase64%2CPHN2ZyB3aWR0aD0iMjAiIGhlaWdodD0iMTAiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI%2BICAgIDxwYXRoIGZpbGw9IiMyNjExMjAiIGQ9Ik0xOS4xNCA0LjVMMTQuMjMgMGwtLjY5Ljc1IDQuMDkgMy43NUgxLjUxTDUuNi43NSA0LjkxIDAgMCA0LjV2Mi45N0MwIDguODEgMS4yOSA5LjkgMi44OCA5LjloMy4wM2MxLjU5IDAgMi44OC0xLjA5IDIuODgtMi40M1Y1LjUyaDEuNTd2MS45NWMwIDEuMzQgMS4yOSAyLjQzIDIuODggMi40M2gzLjAzYzEuNTkgMCAyLjg4LTEuMDkgMi44OC0yLjQzbC0uMDEtMi45N3oiLz48L3N2Zz4%3D&style=for-the-badge)](https://github.com/JStumpp/awesome-android) [![Actions](https://img.shields.io/github/actions/workflow/status/RedMadRobot/input-mask-android/android.yml?style=for-the-badge)](https://github.com/RedMadRobot/input-mask-android/actions/workflows/android.yml) [![iOS Version](https://img.shields.io/badge/-ios_version-red?color=teal&logo=ios&style=for-the-badge)](https://github.com/RedMadRobot/input-mask-ios) [![Telegram](https://img.shields.io/badge/-telegram_author-red?color=blue&logo=telegram&style=for-the-badge)](https://t.me/jeorge_taflanidi) [![license](https://img.shields.io/github/license/mashape/apistatus.svg?style=for-the-badge)](#license)

Input masks restrict data input and allow you to guide users to enter correct values.  
Check out our [wiki](https://github.com/RedMadRobot/input-mask-android/wiki) for quick start and further reading.

## ⚙️ Features

- Apply formatting to your text fields, see [examples](#examples)
- Filter out nonessential symbols (e.g. extract `0123456` from `+1 (999) 012-34-56`)
- For international phone numbers 
    - guess the country from the entered digits
    - apply corresponding value restrictions (e.g. a 🇺🇸US phone will have a format like `+1 201 456-7890`)
- Apply number/currency formatting

<a name="examples" />

## 💳 Examples

- Phone numbers: `+1 ([000]) [000] [00] [00]`
- Dates: `[00]{.}[00]{.}[9900]`
- Serial numbers: `[AA]-[00000099]`
- IPv4: `[099]{.}[099]{.}[099]{.}[099]`
- Visa/MasterCard numbers: `[0000] [0000] [0000] [0000]`
- UK IBAN: `GB[00] [____] [0000] [0000] [0000] [00]`

<a name="installation" />

## 🛠️ Installation

### Gradle

Make sure you've added Kotlin support to your project.

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.redmadrobot:input-mask-android:7.2.0'
    
    implementation 'org.jetbrains.kotlin:kotlin-stdlib:$latest_version'
}
```

## 📢 Communication, Questions & Issues

Please take a closer look at our [Known issues](#knownissues) section before you incorporate our library into your project.

For your bugreports and feature requests please file new issues [via GitHub](https://github.com/RedMadRobot/input-mask-android/issues/new/choose).

Should you have any questions, please search for closed [issues](https://github.com/RedMadRobot/input-mask-android/issues?q=is%3Aclosed) or ask questions at **[StackOverflow](https://stackoverflow.com/questions/tagged/input-mask)** with the `input-mask` tag.

<a name="knownissues" />

## ❗Known issues

## InputMask vs. `NoClassDefFoundError`

```
java.lang.NoClassDefFoundError: Failed resolution of: Lkotlin/jvm/internal/Intrinsics;
```
Receiving this error might mean you haven't configured Kotlin for your Java only project. Consider explicitly adding the following to the list of your project dependencies:
```
implementation 'org.jetbrains.kotlin:kotlin-stdlib:$latest_version'
```
— where `latest_version` is the current version of `kotlin-stdlib`.

## InputMask vs. `android:inputType` and `IndexOutOfBoundsException`

Be careful when specifying field's `android:inputType`. 
The library uses native `Editable` variable received on `afterTextChange` event in order to replace text efficiently. Because of that, field's `inputType` is actually considered when the library is trying to mutate the text. 

For instance, having a field with `android:inputType="numeric"`, you cannot put spaces and dashes into the mentioned `Editable` variable by default. Doing so will cause an out of range exception when the `MaskedTextChangedListener` will try to reposition the cursor.

Still, you may use a workaround by putting the `android:digits` value beside your `android:inputType`; there, you should specify all the acceptable symbols:
```xml
<EditText
    android:inputType="number"
    android:digits="0123456789 -."
    ... />
```
— such that, you'll have the SDK satisfied.

Alternatively, if you are using a programmatic approach without XML files, you may consider configuring a `KeyListener` like this:
```java
editText.setInputType(InputType.TYPE_CLASS_NUMBER);
editText.setKeyListener(DigitsKeyListener.getInstance("0123456789 -.")); // modify character set for your case, e.g. add "+()"
```

## InputMask vs. autocorrection & prediction
> (presumably fixed by [PR50](https://github.com/RedMadRobot/input-mask-android/pull/50))

Symptoms: 
* You've got a wildcard template like `[________]`, allowing user to write any kind of symbols;
* Cursor jumps to the beginning of the line or to some random position while user input.

In this case text autocorrection & prediction might be a root cause of your problem, as it behaves somewhat weirdly in case when field listener tries to change the text during user input.

If so, consider disabling text suggestions by using corresponding input type:
```xml
<EditText
    ...
    android:inputType="textNoSuggestions" />
```
Additionally be aware that some of the third-party keyboards ignore `textNoSuggestions` setting; the recommendation is to use an extra workaround by setting the `inputType` to `textVisiblePassword`.

## InputMask vs. `android:textAllCaps`
> Kudos to [Weiyi Li](https://github.com/li2) for [reporting](https://github.com/RedMadRobot/input-mask-android/issues/85) this issue

Please be advised that `android:textAllCaps` is [not meant](https://developer.android.com/reference/android/widget/TextView.html#setAllCaps(boolean)) to work with `EditText` instances:

> This setting will be ignored if this field is editable or selectable.

Enabling this setting on editable and/or selectable fields leads to weird and unpredictable behaviour and sometimes even [crashes](https://twitter.com/dimsuz/status/731117910337441793). Instead, consider using `android:inputType="textCapCharacters"` or workaround by adding an `InputFilter`:

```java
final InputFilter[] filters = { new InputFilter.AllCaps() };
editText.setFilters(filters);
```

Bare in mind, you might have to befriend this solution with your existing `android:digits` [property](#inputmask-vs-androidinputtype-and-indexoutofboundsexception) in case your text field accepts both digits and letters. 

## 🙏 Special thanks

These folks rock:

* Artem [Fi5t](https://github.com/Fi5t) Kulakov
* Nikita [nbarishok](https://github.com/nbarishok) Barishok
* Roman [yatsinar](https://github.com/yatsinar) Iatcyna
* Alexander [xanderblinov](https://github.com/xanderblinov) Blinov
* Vladislav [Shipaaaa](https://github.com/Shipaaaa) Shipugin
* Vadim [vkotovv](https://github.com/vkotovv) Kotov

## ♻️ License

The library is distributed under the MIT [LICENSE](https://github.com/RedMadRobot/input-mask-android/blob/master/LICENSE).
