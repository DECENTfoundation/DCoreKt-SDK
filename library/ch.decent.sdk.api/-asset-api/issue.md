[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [AssetApi](index.md) / [issue](./issue.md)

# issue

`@JvmOverloads fun issue(credentials: `[`Credentials`](../../ch.decent.sdk.crypto/-credentials/index.md)`, assetIdOrSymbol: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, amount: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, to: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`? = null, memo: `[`Memo`](../../ch.decent.sdk.model/-memo/index.md)`? = null, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee()): Single<`[`TransactionConfirmation`](../../ch.decent.sdk.model/-transaction-confirmation/index.md)`>`

Issue asset. Only the issuer of the asset can issue some funds until maxSupply is reached.

### Parameters

`credentials` - account credentials issuing the asset

`assetIdOrSymbol` - asset to issue

`amount` - raw amount to issue

`to` - optional account id receiving the created funds, issuer account id is used if not defined

`memo` - optional memo for receiver

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough