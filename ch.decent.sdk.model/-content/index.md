[library](../../index.md) / [ch.decent.sdk.model](../index.md) / [Content](./index.md)

# Content

`data class Content`

### Constructors

| [&lt;init&gt;](-init-.md) | `Content(id: `[`ChainObject`](../-chain-object/index.md)`, author: `[`ChainObject`](../-chain-object/index.md)`, coAuthors: `[`CoAuthors`](../-co-authors/index.md)`, expiration: LocalDateTime, created: LocalDateTime, price: `[`PricePerRegion`](../-price-per-region/index.md)`, synopsis: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, size: `[`BigInteger`](http://docs.oracle.com/javase/6/docs/api/java/math/BigInteger.html)`, quorum: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, uri: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, keyParts: `[`Map`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)`<`[`ChainObject`](../-chain-object/index.md)`, `[`KeyPart`](../-key-part/index.md)`>, lastProof: `[`Map`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)`<`[`ChainObject`](../-chain-object/index.md)`, LocalDateTime>, seederPrice: `[`Map`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)`<`[`ChainObject`](../-chain-object/index.md)`, `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`>, isBlocked: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, hash: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, rating: `[`BigInteger`](http://docs.oracle.com/javase/6/docs/api/java/math/BigInteger.html)`, ratingCount: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, timesBought: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, publishingFeeEscrow: `[`AssetAmount`](../-asset-amount/index.md)`, custodyData: `[`CustodyData`](../-custody-data/index.md)`)` |

### Properties

| [author](author.md) | `val author: `[`ChainObject`](../-chain-object/index.md) |
| [coAuthors](co-authors.md) | `val coAuthors: `[`CoAuthors`](../-co-authors/index.md) |
| [created](created.md) | `val created: LocalDateTime` |
| [custodyData](custody-data.md) | `val custodyData: `[`CustodyData`](../-custody-data/index.md) |
| [expiration](expiration.md) | `val expiration: LocalDateTime` |
| [hash](hash.md) | `val hash: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [id](id.md) | `val id: `[`ChainObject`](../-chain-object/index.md) |
| [isBlocked](is-blocked.md) | `val isBlocked: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [keyParts](key-parts.md) | `val keyParts: `[`Map`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)`<`[`ChainObject`](../-chain-object/index.md)`, `[`KeyPart`](../-key-part/index.md)`>` |
| [lastProof](last-proof.md) | `val lastProof: `[`Map`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)`<`[`ChainObject`](../-chain-object/index.md)`, LocalDateTime>` |
| [price](price.md) | `val price: `[`PricePerRegion`](../-price-per-region/index.md) |
| [publishingFeeEscrow](publishing-fee-escrow.md) | `val publishingFeeEscrow: `[`AssetAmount`](../-asset-amount/index.md) |
| [quorum](quorum.md) | `val quorum: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [rating](rating.md) | `val rating: `[`BigInteger`](http://docs.oracle.com/javase/6/docs/api/java/math/BigInteger.html) |
| [ratingCount](rating-count.md) | `val ratingCount: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [seederPrice](seeder-price.md) | `val seederPrice: `[`Map`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)`<`[`ChainObject`](../-chain-object/index.md)`, `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`>` |
| [size](size.md) | `val size: `[`BigInteger`](http://docs.oracle.com/javase/6/docs/api/java/math/BigInteger.html) |
| [synopsis](synopsis.md) | `val synopsis: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [timesBought](times-bought.md) | `val timesBought: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [uri](uri.md) | `val uri: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Functions

| [price](price.md) | `fun price(): `[`AssetAmount`](../-asset-amount/index.md) |

