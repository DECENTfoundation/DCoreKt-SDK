[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [AccountApi](index.md) / [create](./create.md)

# create

`@JvmOverloads fun create(registrar: `[`Credentials`](../../ch.decent.sdk.crypto/-credentials/index.md)`, name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, address: `[`Address`](../../ch.decent.sdk.crypto/-address/index.md)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee()): Single<`[`TransactionConfirmation`](../../ch.decent.sdk.model/-transaction-confirmation/index.md)`>`

Create a new account.

### Parameters

`registrar` - credentials used to register the new account

`name` - new account name

`address` - new account public key address

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough

**Return**
a transaction confirmation

