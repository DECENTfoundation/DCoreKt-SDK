[library](../../index.md) / [ch.decent.sdk.model](../index.md) / [AssetFormatter](./index.md)

# AssetFormatter

`interface AssetFormatter`

### Properties

| [defaultFormatter](default-formatter.md) | `open val defaultFormatter: `[`NumberFormat`](http://docs.oracle.com/javase/6/docs/api/java/text/NumberFormat.html)<br>default formatter with max number of decimals per precision of asset |
| [id](id.md) | `abstract val id: `[`ChainObject`](../-chain-object/index.md) |
| [precision](precision.md) | `abstract val precision: `[`Byte`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte/index.html) |
| [symbol](symbol.md) | `abstract val symbol: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Functions

| [amount](amount.md) | `open fun amount(value: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`AssetAmount`](../-asset-amount/index.md)<br>parse string unit asset value to AssetAmount`open fun amount(value: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)`): `[`AssetAmount`](../-asset-amount/index.md)<br>parse decimal unit asset value to AssetAmount |
| [format](format.md) | `open fun format(value: `[`BigDecimal`](http://docs.oracle.com/javase/6/docs/api/java/math/BigDecimal.html)`, formatter: `[`NumberFormat`](http://docs.oracle.com/javase/6/docs/api/java/text/NumberFormat.html)`): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>`open fun format(value: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)`, formatter: `[`NumberFormat`](http://docs.oracle.com/javase/6/docs/api/java/text/NumberFormat.html)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)` = {}): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>format asset unit value with asset symbol`open fun format(value: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, formatter: `[`NumberFormat`](http://docs.oracle.com/javase/6/docs/api/java/text/NumberFormat.html)`): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>`open fun format(value: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, formatter: `[`NumberFormat`](http://docs.oracle.com/javase/6/docs/api/java/text/NumberFormat.html)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)` = {}): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>format raw value with asset symbol |
| [fromRaw](from-raw.md) | `open fun fromRaw(value: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)<br>get asset unit value, eg. 100000000 = 1DCT |
| [toRaw](to-raw.md) | `open fun toRaw(value: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)`): `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)<br>get raw value, eg. 1DCT = 100000000 |

### Inheritors

| [AmountWithAsset](../-amount-with-asset/index.md) | `data class AmountWithAsset : `[`AssetFormatter`](./index.md) |
| [Asset](../-asset/index.md) | `data class Asset : `[`AssetFormatter`](./index.md) |

