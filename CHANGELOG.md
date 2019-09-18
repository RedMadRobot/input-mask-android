# Changelog

### `5.0.0`

#### Removed:

* `CaretStringIterator::beforeCaret()`

This method is now replaced with `::insertionAffectsCaret()` and `::deletionAffectsCaret()` calls. 

#### Added:

* `CaretString` instances now contain caret gravity.

Caret gravity affects caret movement when `Mask` adds characters precisely at the caret position during formatting. It is important to retain caret position after text deletion/backspacing.

Default `CaretGravity` is `.FORWARD`. Set caret gravity to `.BACKWARD` only when user hits backspace.

* `CaretStringIterator::insertionAffectsCaret()` and `CaretStringIterator::deletionAffectsCaret()`

These methods allow to incorporate new caret gravity setting.

* Right-to-left masks. 

### `4.3.0`

#### Added:

* `AffinityCalculationStrategy.EXTRACTED_VALUE_CAPACITY` option allowing to have radically different mask format depending on the extracted value length

### `4.2.0`

#### Added:

* `AffinityCalculationStrategy.CAPACITY` affinity calculation option allowing to have radically different mask format depending on the input length

### `4.1.0`

#### Added:

* `formattedText` to `MaskedTextChangedListener.ValueListener::onTextChanged`
	* by [Nikita Barishok](https://github.com/nbarishok) in [PR#73](https://github.com/RedMadRobot/input-mask-android/pull/73) 
* `Mask::isValid(format:customNotations:)` method for format checks

#### Fixed:

* Optional blocks of symbols are now ignored when extracted value completeness is calculated, see [#70](https://github.com/RedMadRobot/input-mask-android/issues/70)
