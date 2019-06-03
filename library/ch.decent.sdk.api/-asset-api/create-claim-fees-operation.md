[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [AssetApi](index.md) / [createClaimFeesOperation](./create-claim-fees-operation.md)

# createClaimFeesOperation

`@JvmOverloads fun createClaimFeesOperation(assetIdOrSymbol: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, uia: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, dct: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee()): Single<`[`AssetClaimFeesOperation`](../../ch.decent.sdk.model.operation/-asset-claim-fees-operation/index.md)`>`

Create claim fees operation. Claim funds from asset pool, only the asset issuer can claim.

### Parameters

`assetIdOrSymbol` - which asset to claim from

`uia` - UIA raw amount

`dct` - DCT raw amount

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough