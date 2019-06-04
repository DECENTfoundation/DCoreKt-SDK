[library](../../index.md) / [ch.decent.sdk.model](../index.md) / [AmountWithAsset](./index.md)

# AmountWithAsset

`data class AmountWithAsset : `[`AssetFormatter`](../-asset-formatter/index.md)

### Constructors

| [&lt;init&gt;](-init-.md) | `AmountWithAsset(asset: `[`Asset`](../-asset/index.md)`, amount: `[`AssetAmount`](../-asset-amount/index.md)`)` |

### Properties

| [amount](amount.md) | `val amount: `[`AssetAmount`](../-asset-amount/index.md) |
| [asset](asset.md) | `val asset: `[`Asset`](../-asset/index.md) |

### Functions

| [format](format.md) | `fun format(formatter: `[`NumberFormat`](http://docs.oracle.com/javase/6/docs/api/java/text/NumberFormat.html)`): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>`fun format(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>`fun format(formatter: `[`NumberFormat`](http://docs.oracle.com/javase/6/docs/api/java/text/NumberFormat.html)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [fromRaw](from-raw.md) | `fun fromRaw(): `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html) |

