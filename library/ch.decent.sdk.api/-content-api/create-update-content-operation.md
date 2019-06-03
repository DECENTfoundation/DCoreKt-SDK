[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [ContentApi](index.md) / [createUpdateContentOperation](./create-update-content-operation.md)

# createUpdateContentOperation

`@JvmOverloads fun createUpdateContentOperation(content: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee()): Single<`[`AddOrUpdateContentOperation`](../../ch.decent.sdk.model.operation/-add-or-update-content-operation/index.md)`>`

Create request to update content operation. Fills the model with actual content values.

### Parameters

`content` - content id

`fee` - {@link AssetAmount} fee for the operation or asset id, if left undefined the fee will be computed in DCT asset.
When set, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough`@JvmOverloads fun createUpdateContentOperation(content: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee()): Single<`[`AddOrUpdateContentOperation`](../../ch.decent.sdk.model.operation/-add-or-update-content-operation/index.md)`>`

Create request to update content operation. Fills the model with actual content values.

### Parameters

`content` - content uri

`fee` - {@link AssetAmount} fee for the operation or asset id, if left undefined the fee will be computed in DCT asset.
When set, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough