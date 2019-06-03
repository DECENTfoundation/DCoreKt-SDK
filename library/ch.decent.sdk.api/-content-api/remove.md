[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [ContentApi](index.md) / [remove](./remove.md)

# remove

`@JvmOverloads fun remove(credentials: `[`Credentials`](../../ch.decent.sdk.crypto/-credentials/index.md)`, content: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee()): Single<`[`TransactionConfirmation`](../../ch.decent.sdk.model/-transaction-confirmation/index.md)`>`

Remove content. Sets expiration to head block time, so the content cannot be purchased, but remains in database.

### Parameters

`credentials` - author credentials

`content` - content id

`fee` - {@link AssetAmount} fee for the operation or asset id, if left undefined the fee will be computed in DCT asset.
When set, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough`@JvmOverloads fun remove(credentials: `[`Credentials`](../../ch.decent.sdk.crypto/-credentials/index.md)`, content: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee()): Single<`[`TransactionConfirmation`](../../ch.decent.sdk.model/-transaction-confirmation/index.md)`>`

Remove content. Sets expiration to head block time, so the content cannot be purchased, but remains in database.

### Parameters

`credentials` - author credentials

`content` - content uri

`fee` - {@link AssetAmount} fee for the operation or asset id, if left undefined the fee will be computed in DCT asset.
When set, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough