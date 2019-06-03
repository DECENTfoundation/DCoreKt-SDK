[library](../../index.md) / [ch.decent.sdk.model](../index.md) / [Purchase](./index.md)

# Purchase

`data class Purchase`

### Constructors

| [&lt;init&gt;](-init-.md) | `Purchase(id: `[`ChainObject`](../-chain-object/index.md)`, author: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, uri: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, synopsisJson: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, price: `[`AssetAmount`](../-asset-amount/index.md)`, priceBefore: `[`AssetAmount`](../-asset-amount/index.md)`, priceAfter: `[`AssetAmount`](../-asset-amount/index.md)`, seedersAnswered: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ChainObject`](../-chain-object/index.md)`>, size: `[`BigInteger`](http://docs.oracle.com/javase/6/docs/api/java/math/BigInteger.html)`, rating: `[`BigInteger`](http://docs.oracle.com/javase/6/docs/api/java/math/BigInteger.html)`, comment: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, expiration: LocalDateTime, pubElGamalKey: `[`PubKey`](../-pub-key/index.md)`?, keyParticles: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`KeyPart`](../-key-part/index.md)`>, expired: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, delivered: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, deliveryExpiration: LocalDateTime, ratedOrCommented: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, created: LocalDateTime, regionFrom: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`)` |

### Properties

| [author](author.md) | `val author: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [comment](comment.md) | `val comment: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [created](created.md) | `val created: LocalDateTime` |
| [delivered](delivered.md) | `val delivered: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [deliveryExpiration](delivery-expiration.md) | `val deliveryExpiration: LocalDateTime` |
| [expiration](expiration.md) | `val expiration: LocalDateTime` |
| [expired](expired.md) | `val expired: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [id](id.md) | `val id: `[`ChainObject`](../-chain-object/index.md) |
| [keyParticles](key-particles.md) | `val keyParticles: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`KeyPart`](../-key-part/index.md)`>` |
| [price](price.md) | `val price: `[`AssetAmount`](../-asset-amount/index.md) |
| [priceAfter](price-after.md) | `val priceAfter: `[`AssetAmount`](../-asset-amount/index.md) |
| [priceBefore](price-before.md) | `val priceBefore: `[`AssetAmount`](../-asset-amount/index.md) |
| [pubElGamalKey](pub-el-gamal-key.md) | `val pubElGamalKey: `[`PubKey`](../-pub-key/index.md)`?` |
| [ratedOrCommented](rated-or-commented.md) | `val ratedOrCommented: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [rating](rating.md) | `val rating: `[`BigInteger`](http://docs.oracle.com/javase/6/docs/api/java/math/BigInteger.html) |
| [regionFrom](region-from.md) | `val regionFrom: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [seedersAnswered](seeders-answered.md) | `val seedersAnswered: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ChainObject`](../-chain-object/index.md)`>` |
| [size](size.md) | `val size: `[`BigInteger`](http://docs.oracle.com/javase/6/docs/api/java/math/BigInteger.html) |
| [synopsisJson](synopsis-json.md) | `val synopsisJson: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [uri](uri.md) | `val uri: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Functions

| [synopsis](synopsis.md) | `fun synopsis(): `[`Synopsis`](../-synopsis/index.md) |

