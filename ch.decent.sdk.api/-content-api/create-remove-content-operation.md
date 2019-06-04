[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [ContentApi](index.md) / [createRemoveContentOperation](./create-remove-content-operation.md)

# createRemoveContentOperation

`@JvmOverloads fun createRemoveContentOperation(content: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee()): Single<`[`RemoveContentOperation`](../../ch.decent.sdk.model.operation/-remove-content-operation/index.md)`>`

Create remove content operation. Sets expiration to head block time, so the content cannot be purchased, but remains in database.

### Parameters

`content` - content id

`fee` - {@link AssetAmount} fee for the operation or asset id, if left undefined the fee will be computed in DCT asset.
When set, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough`@JvmOverloads fun createRemoveContentOperation(content: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee()): Single<`[`RemoveContentOperation`](../../ch.decent.sdk.model.operation/-remove-content-operation/index.md)`>`

Create remove content operation. Sets expiration to head block time, so the content cannot be purchased, but remains in database.

### Parameters

`content` - content uri

`fee` - {@link AssetAmount} fee for the operation or asset id, if left undefined the fee will be computed in DCT asset.
When set, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough