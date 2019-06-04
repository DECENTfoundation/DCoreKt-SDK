[library](../../index.md) / [ch.decent.sdk.model](../index.md) / [Asset](./index.md)

# Asset

`data class Asset : `[`AssetFormatter`](../-asset-formatter/index.md)

### Constructors

| [&lt;init&gt;](-init-.md) | `Asset(id: `[`ChainObject`](../-chain-object/index.md)`, symbol: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, precision: `[`Byte`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte/index.html)`, issuer: `[`ChainObject`](../-chain-object/index.md)`, description: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, options: `[`AssetOptions`](../-asset-options/index.md)`, dataId: `[`ChainObject`](../-chain-object/index.md)`, monitoredAssetOpts: `[`MonitoredAssetOptions`](../-monitored-asset-options/index.md)`? = null)` |

### Properties

| [dataId](data-id.md) | `val dataId: `[`ChainObject`](../-chain-object/index.md) |
| [description](description.md) | `val description: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [id](id.md) | `val id: `[`ChainObject`](../-chain-object/index.md) |
| [issuer](issuer.md) | `val issuer: `[`ChainObject`](../-chain-object/index.md) |
| [monitoredAssetOpts](monitored-asset-opts.md) | `val monitoredAssetOpts: `[`MonitoredAssetOptions`](../-monitored-asset-options/index.md)`?` |
| [options](options.md) | `val options: `[`AssetOptions`](../-asset-options/index.md) |
| [precision](precision.md) | `val precision: `[`Byte`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte/index.html) |
| [symbol](symbol.md) | `val symbol: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Inherited Properties

| [defaultFormatter](../-asset-formatter/default-formatter.md) | `open val defaultFormatter: `[`NumberFormat`](http://docs.oracle.com/javase/6/docs/api/java/text/NumberFormat.html)<br>default formatter with max number of decimals per precision of asset |

### Functions

| [convertFromDCT](convert-from-d-c-t.md) | `fun convertFromDCT(amount: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, roundingMode: `[`RoundingMode`](http://docs.oracle.com/javase/6/docs/api/java/math/RoundingMode.html)`): `[`AssetAmount`](../-asset-amount/index.md)<br>Converts DCT [amount](convert-from-d-c-t.md#ch.decent.sdk.model.Asset$convertFromDCT(kotlin.Long, java.math.RoundingMode)/amount) according conversion rate. Throws an [IllegalArgumentException](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-illegal-argument-exception/index.html) if the quote or base [amount](convert-from-d-c-t.md#ch.decent.sdk.model.Asset$convertFromDCT(kotlin.Long, java.math.RoundingMode)/amount) is not greater then zero. |
| [convertToDCT](convert-to-d-c-t.md) | `fun convertToDCT(amount: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, roundingMode: `[`RoundingMode`](http://docs.oracle.com/javase/6/docs/api/java/math/RoundingMode.html)`): `[`AssetAmount`](../-asset-amount/index.md)<br>Converts asset [amount](convert-to-d-c-t.md#ch.decent.sdk.model.Asset$convertToDCT(kotlin.Long, java.math.RoundingMode)/amount) to DCT according conversion rate. Throws an [IllegalArgumentException](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-illegal-argument-exception/index.html) if the quote or base [amount](convert-to-d-c-t.md#ch.decent.sdk.model.Asset$convertToDCT(kotlin.Long, java.math.RoundingMode)/amount) is not greater then zero. |

### Inherited Functions

| [amount](../-asset-formatter/amount.md) | `open fun amount(value: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`AssetAmount`](../-asset-amount/index.md)<br>parse string unit asset value to AssetAmount`open fun amount(value: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)`): `[`AssetAmount`](../-asset-amount/index.md)<br>parse decimal unit asset value to AssetAmount |
| [format](../-asset-formatter/format.md) | `open fun format(value: `[`BigDecimal`](http://docs.oracle.com/javase/6/docs/api/java/math/BigDecimal.html)`, formatter: `[`NumberFormat`](http://docs.oracle.com/javase/6/docs/api/java/text/NumberFormat.html)`): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>`open fun format(value: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)`, formatter: `[`NumberFormat`](http://docs.oracle.com/javase/6/docs/api/java/text/NumberFormat.html)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)` = {}): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>format asset unit value with asset symbol`open fun format(value: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, formatter: `[`NumberFormat`](http://docs.oracle.com/javase/6/docs/api/java/text/NumberFormat.html)`): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>`open fun format(value: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, formatter: `[`NumberFormat`](http://docs.oracle.com/javase/6/docs/api/java/text/NumberFormat.html)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)` = {}): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>format raw value with asset symbol |
| [fromRaw](../-asset-formatter/from-raw.md) | `open fun fromRaw(value: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)<br>get asset unit value, eg. 100000000 = 1DCT |
| [toRaw](../-asset-formatter/to-raw.md) | `open fun toRaw(value: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)`): `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)<br>get raw value, eg. 1DCT = 100000000 |

### Companion Object Functions

| [isValidSymbol](is-valid-symbol.md) | `fun isValidSymbol(symbol: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |

