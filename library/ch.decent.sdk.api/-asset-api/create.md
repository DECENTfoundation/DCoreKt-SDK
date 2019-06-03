[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [AssetApi](index.md) / [create](./create.md)

# create

`@JvmOverloads fun create(credentials: `[`Credentials`](../../ch.decent.sdk.crypto/-credentials/index.md)`, symbol: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, precision: `[`Byte`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte/index.html)`, description: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, options: `[`AssetOptions`](../../ch.decent.sdk.model/-asset-options/index.md)` = AssetOptions(ExchangeRate.forCreateOp(1, 1)), fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee()): Single<`[`TransactionConfirmation`](../../ch.decent.sdk.model/-transaction-confirmation/index.md)`>`

Create a new Asset.

### Parameters

`credentials` - account credentials issuing the asset

`symbol` - the string symbol, 3-16 uppercase chars

`precision` - base unit precision, decimal places used in string representation

`description` - optional description

`options` - asset options

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough