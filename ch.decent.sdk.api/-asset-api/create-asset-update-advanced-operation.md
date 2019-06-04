[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [AssetApi](index.md) / [createAssetUpdateAdvancedOperation](./create-asset-update-advanced-operation.md)

# createAssetUpdateAdvancedOperation

`@JvmOverloads fun createAssetUpdateAdvancedOperation(assetIdOrSymbol: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee()): Single<`[`AssetUpdateAdvancedOperation`](../../ch.decent.sdk.model.operation/-asset-update-advanced-operation/index.md)`>`

Create update advanced options operation for the asset.

### Parameters

`assetIdOrSymbol` - asset to update

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough