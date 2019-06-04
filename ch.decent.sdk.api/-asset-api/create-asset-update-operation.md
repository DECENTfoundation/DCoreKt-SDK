[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [AssetApi](index.md) / [createAssetUpdateOperation](./create-asset-update-operation.md)

# createAssetUpdateOperation

`@JvmOverloads fun createAssetUpdateOperation(assetIdOrSymbol: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, newIssuer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`? = null, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee()): Single<`[`AssetUpdateOperation`](../../ch.decent.sdk.model.operation/-asset-update-operation/index.md)`>`

Create update asset operation. Fills model with actual asset values.

### Parameters

`assetIdOrSymbol` - asset to update

`newIssuer` - a new issuer account id

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough