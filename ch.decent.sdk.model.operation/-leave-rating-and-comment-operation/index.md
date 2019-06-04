[library](../../index.md) / [ch.decent.sdk.model.operation](../index.md) / [LeaveRatingAndCommentOperation](./index.md)

# LeaveRatingAndCommentOperation

`class LeaveRatingAndCommentOperation : `[`BaseOperation`](../-base-operation/index.md)

Leave comment and rating operation constructor

### Parameters

`uri` - uri of the content

`consumer` - chain object id of the buyer's account

`rating` - 1-5 stars

`comment` - max 100 chars

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough

### Constructors

| [&lt;init&gt;](-init-.md) | `LeaveRatingAndCommentOperation(uri: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, consumer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, rating: `[`Byte`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte/index.html)`, comment: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee())`<br>Leave comment and rating operation constructor |

### Properties

| [comment](comment.md) | `val comment: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>max 100 chars |
| [consumer](consumer.md) | `val consumer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)<br>chain object id of the buyer's account |
| [rating](rating.md) | `val rating: `[`Byte`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte/index.html)<br>1-5 stars |
| [uri](uri.md) | `val uri: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>uri of the content |

### Inherited Properties

| [fee](../-base-operation/fee.md) | `var fee: `[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)<br>Fee for the operation, must be set to valid value when broadcasting to network |
| [fetchFee](../-base-operation/fetch-fee.md) | `var fetchFee: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Defines whether to fetch required fee for the operation when using the API methods to create transaction or broadcast operation |
| [type](../-base-operation/type.md) | `var type: `[`OperationType`](../-operation-type/index.md) |

### Functions

| [toString](to-string.md) | `fun toString(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Inherited Functions

| [equals](../-base-operation/equals.md) | `open fun equals(other: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`?): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hashCode](../-base-operation/hash-code.md) | `open fun hashCode(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

