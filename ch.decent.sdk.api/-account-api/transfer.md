[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [AccountApi](index.md) / [transfer](./transfer.md)

# transfer

`@JvmOverloads fun transfer(credentials: `[`Credentials`](../../ch.decent.sdk.crypto/-credentials/index.md)`, nameOrId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, amount: `[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)`, memo: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`? = null, encrypted: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = true, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee()): Single<`[`TransactionConfirmation`](../../ch.decent.sdk.model/-transaction-confirmation/index.md)`>`

Make a transfer.

### Parameters

`credentials` - account credentials

`nameOrId` - account id or account name

`amount` - amount to send with asset type

`memo` - optional message

`encrypted` - encrypted is visible only for sender and receiver, unencrypted is visible publicly

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough

**Return**
a transaction confirmation

