[library](../../index.md) / [ch.decent.sdk.model.operation](../index.md) / [CustomOperation](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`CustomOperation(type: `[`CustomOperationType`](../-custom-operation-type/index.md)`, payer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, requiredAuths: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`>, data: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee())`

Custom operation

### Parameters

`type` - custom operation subtype

`payer` - account which pays for the fee

`requiredAuths` - accounts required to authorize this operation with signatures

`data` - data payload encoded in hex string

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough