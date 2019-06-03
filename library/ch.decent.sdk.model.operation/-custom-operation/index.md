[library](../../index.md) / [ch.decent.sdk.model.operation](../index.md) / [CustomOperation](./index.md)

# CustomOperation

`open class CustomOperation : `[`BaseOperation`](../-base-operation/index.md)

Custom operation

### Parameters

`type` - custom operation subtype

`payer` - account which pays for the fee

`requiredAuths` - accounts required to authorize this operation with signatures

`data` - data payload encoded in hex string

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough

### Constructors

| [&lt;init&gt;](-init-.md) | `CustomOperation(type: `[`CustomOperationType`](../-custom-operation-type/index.md)`, payer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, requiredAuths: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`>, data: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee())`<br>Custom operation |

### Properties

| [data](data.md) | `val data: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>data payload encoded in hex string |
| [id](id.md) | `val id: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [payer](payer.md) | `val payer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)<br>account which pays for the fee |
| [requiredAuths](required-auths.md) | `val requiredAuths: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`>`<br>accounts required to authorize this operation with signatures |

### Inherited Properties

| [fee](../-base-operation/fee.md) | `var fee: `[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)<br>Fee for the operation, must be set to valid value when broadcasting to network |
| [fetchFee](../-base-operation/fetch-fee.md) | `var fetchFee: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Defines whether to fetch required fee for the operation when using the API methods to create transaction or broadcast operation |
| [type](../-base-operation/type.md) | `var type: `[`OperationType`](../-operation-type/index.md) |

### Functions

| [toString](to-string.md) | `open fun toString(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Inherited Functions

| [equals](../-base-operation/equals.md) | `open fun equals(other: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`?): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hashCode](../-base-operation/hash-code.md) | `open fun hashCode(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

### Inheritors

| [SendMessageOperation](../-send-message-operation/index.md) | `class SendMessageOperation : `[`CustomOperation`](./index.md)<br>Send message operation. |

