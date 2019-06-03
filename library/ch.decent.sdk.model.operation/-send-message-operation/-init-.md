[library](../../index.md) / [ch.decent.sdk.model.operation](../index.md) / [SendMessageOperation](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`SendMessageOperation(messagePayloadJson: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, payer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, requiredAuths: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`> = listOf(payer), fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee())`

Send message operation.

### Parameters

`messagePayloadJson` - message payload

`payer` - account id to pay for the operation

`requiredAuths` - account ids required to authorize this operation

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough