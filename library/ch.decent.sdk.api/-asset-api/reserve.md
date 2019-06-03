[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [AssetApi](index.md) / [reserve](./reserve.md)

# reserve

`@JvmOverloads fun reserve(credentials: `[`Credentials`](../../ch.decent.sdk.crypto/-credentials/index.md)`, assetIdOrSymbol: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, amount: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee()): Single<`[`TransactionConfirmation`](../../ch.decent.sdk.model/-transaction-confirmation/index.md)`>`

Reserve funds. Return issued funds to the issuer of the asset.

### Parameters

`credentials` - account credentials returning the asset

`assetIdOrSymbol` - which asset to reserve from

`amount` - raw amount to remove from current supply

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough