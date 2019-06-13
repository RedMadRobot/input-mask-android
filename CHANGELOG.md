# Changelog

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
