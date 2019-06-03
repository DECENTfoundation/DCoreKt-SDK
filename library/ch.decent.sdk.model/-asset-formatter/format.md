[library](../../index.md) / [ch.decent.sdk.model](../index.md) / [AssetFormatter](index.md) / [format](./format.md)

# format

`open fun format(value: `[`BigDecimal`](http://docs.oracle.com/javase/6/docs/api/java/math/BigDecimal.html)`, formatter: `[`NumberFormat`](http://docs.oracle.com/javase/6/docs/api/java/text/NumberFormat.html)`): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

format asset unit value with asset symbol

### Parameters

`value` - asset unit value

`formatter` - formatter to use for numeral value

**Return**
asset formatted string

`open fun format(value: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)`, formatter: `[`NumberFormat`](http://docs.oracle.com/javase/6/docs/api/java/text/NumberFormat.html)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)` = {}): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

format asset unit value with asset symbol

### Parameters

`value` - asset unit value

`formatter` - default formatter modifier function

**Return**
asset formatted string

`open fun format(value: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, formatter: `[`NumberFormat`](http://docs.oracle.com/javase/6/docs/api/java/text/NumberFormat.html)`): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

format raw value with asset symbol

### Parameters

`value` - raw value

`formatter` - formatter to use for numeral value

**Return**
asset formatted string

`open fun format(value: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, formatter: `[`NumberFormat`](http://docs.oracle.com/javase/6/docs/api/java/text/NumberFormat.html)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)` = {}): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

format raw value with asset symbol

### Parameters

`value` - raw value

`formatter` - default formatter modifier function

**Return**
asset formatted string

