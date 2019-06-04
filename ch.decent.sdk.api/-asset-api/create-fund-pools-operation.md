[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [AssetApi](index.md) / [createFundPoolsOperation](./create-fund-pools-operation.md)

# createFundPoolsOperation

`@JvmOverloads fun createFundPoolsOperation(assetIdOrSymbol: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, uia: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, dct: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee()): Single<`[`AssetFundPoolsOperation`](../../ch.decent.sdk.model.operation/-asset-fund-pools-operation/index.md)`>`

Create fund asset pool operation. Any account can fund a pool.

### Parameters

`assetIdOrSymbol` - which asset to fund

`uia` - UIA raw amount

`dct` - DCT raw amount

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough