# Changelog

## 7.1.1

**🔄 Modified:**

* `minSdkVersion` → `23`

## 7.1.0

**⤵️ Added:**

* New logo :D
* New README :D
* Text listener callbacks now return a `tailPlaceholder` for the value to be completed
* `Country`: a model object representing a country with phone formatting, ISO codes & emojis
* `Country.all`: a dictionary of known countries
* `PhoneInputListener`: a `MaskedTextChangedListener` allowing to enter a phone number of any known country
* `NumberInputListener`: a `MaskedTextChangedListener` allowing to enter currencies and other numbers

**🔄 Modified:**

* `minSdkVersion` → `30`

## 6.1.0

**⤴️ Removed:**

* `IndexOutOfBoundsException`

HUZZAH!

## 6.0.0

**⤴️ Removed:**

* `Mask::apply()`, the `autocomplete` flag

This flag is now a part of the `CaretGravity.FORWARD`.

**🔄 Modified:**

* `CaretGravity` is now a sealed class

**⤵️ Added:**

* `CaretGravity.FORWARD`, the `autocomplete` flag
* `CaretGravity.BACKWARD`, the [`autoskip`](https://github.com/RedMadRobot/input-mask-android/wiki/0.-Mask#autoskip-flag) flag

## 5.0.0

**⤴️ Removed:**

* `CaretStringIterator::beforeCaret()`

This method is now replaced with `::insertionAffectsCaret()` and `::deletionAffectsCaret()` calls. 

**⤵️ Added:**

* `CaretString` instances now contain caret gravity.

Caret gravity affects caret movement when `Mask` adds characters precisely at the caret position during formatting. It is important to retain caret position after text deletion/backspacing.

Default `CaretGravity` is `.FORWARD`. Set caret gravity to `.BACKWARD` only when user hits backspace.

* `CaretStringIterator::insertionAffectsCaret()` and `CaretStringIterator::deletionAffectsCaret()`

These methods allow to incorporate new caret gravity setting.

* Right-to-left masks. 

## 4.3.0

**⤵️ Added:**

* `AffinityCalculationStrategy.EXTRACTED_VALUE_CAPACITY` option allowing to have radically different mask format depending on the extracted value length

## 4.2.0

**⤵️ Added:**

* `AffinityCalculationStrategy.CAPACITY` affinity calculation option allowing to have radically different mask format depending on the input length

## 4.1.0

**⤵️ Added:**

* `formattedText` to `MaskedTextChangedListener.ValueListener::onTextChanged`
	* by [Nikita Barishok](https://github.com/nbarishok) in [PR#73](https://github.com/RedMadRobot/input-mask-android/pull/73) 
* `Mask::isValid(format:customNotations:)` method for format checks

**🔄 Fixed:**

* Optional blocks of symbols are now ignored when extracted value completeness is calculated, see [#70](https://github.com/RedMadRobot/input-mask-android/issues/70)
