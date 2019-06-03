[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [AssetApi](index.md) / [updateAdvanced](./update-advanced.md)

# updateAdvanced

`@JvmOverloads fun updateAdvanced(credentials: `[`Credentials`](../../ch.decent.sdk.crypto/-credentials/index.md)`, assetIdOrSymbol: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, precision: `[`Byte`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte/index.html)`? = null, fixedMaxSupply: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`? = null, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee()): Single<`[`TransactionConfirmation`](../../ch.decent.sdk.model/-transaction-confirmation/index.md)`>`

Update advanced options for the asset.

### Parameters

`credentials` - account credentials issuing the asset

`assetIdOrSymbol` - asset to update

`precision` - new precision

`fixedMaxSupply` - whether it should be allowed to change max supply, cannot be reverted once set to true

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough