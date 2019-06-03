[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [AssetApi](index.md) / [update](./update.md)

# update

`@JvmOverloads fun update(credentials: `[`Credentials`](../../ch.decent.sdk.crypto/-credentials/index.md)`, assetIdOrSymbol: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, exchangeRate: `[`Pair`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)`<`[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`>? = null, description: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`? = null, exchangeable: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`? = null, maxSupply: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`? = null, newIssuer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`? = null, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee()): Single<`[`TransactionConfirmation`](../../ch.decent.sdk.model/-transaction-confirmation/index.md)`>`

Update asset.

### Parameters

`credentials` - account credentials issuing the asset

`assetIdOrSymbol` - asset to update

`exchangeRate` - new exchange rate, DCT base amount to UIA quote amount pair

`description` - new description

`exchangeable` - enable converting the asset to DCT, so it can be used to pay for fees

`maxSupply` - new max supply

`newIssuer` - a new issuer account id

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough