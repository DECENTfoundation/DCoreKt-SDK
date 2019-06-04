[library](../../index.md) / [ch.decent.sdk.model.operation](../index.md) / [SendMessageOperation](./index.md)

# SendMessageOperation

`class SendMessageOperation : `[`CustomOperation`](../-custom-operation/index.md)

Send message operation.

### Parameters

`messagePayloadJson` - message payload

`payer` - account id to pay for the operation

`requiredAuths` - account ids required to authorize this operation

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough

### Constructors

| [&lt;init&gt;](-init-.md) | `SendMessageOperation(messagePayloadJson: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, payer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, requiredAuths: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`> = listOf(payer), fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee())`<br>Send message operation. |

### Inherited Properties

| [data](../-custom-operation/data.md) | `val data: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>data payload encoded in hex string |
| [id](../-custom-operation/id.md) | `val id: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [payer](../-custom-operation/payer.md) | `val payer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)<br>account which pays for the fee |
| [requiredAuths](../-custom-operation/required-auths.md) | `val requiredAuths: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`>`<br>accounts required to authorize this operation with signatures |

### Inherited Functions

| [toString](../-custom-operation/to-string.md) | `open fun toString(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

