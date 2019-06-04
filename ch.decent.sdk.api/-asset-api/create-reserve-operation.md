[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [AssetApi](index.md) / [createReserveOperation](./create-reserve-operation.md)

# createReserveOperation

`@JvmOverloads fun createReserveOperation(assetIdOrSymbol: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, amount: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee()): Single<`[`AssetReserveOperation`](../../ch.decent.sdk.model.operation/-asset-reserve-operation/index.md)`>`

Create reserve funds operation. Return issued funds to the issuer of the asset.

### Parameters

`assetIdOrSymbol` - which asset to reserve from

`amount` - raw amount to remove from current supply

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough