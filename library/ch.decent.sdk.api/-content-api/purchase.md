[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [ContentApi](index.md) / [purchase](./purchase.md)

# purchase

`@JvmOverloads fun purchase(credentials: `[`Credentials`](../../ch.decent.sdk.crypto/-credentials/index.md)`, contentId: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee()): Single<`[`TransactionConfirmation`](../../ch.decent.sdk.model/-transaction-confirmation/index.md)`>`

Purchase a content.

### Parameters

`credentials` - account credentials

`contentId` - object id of the content, 2.13.*

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough

**Return**
a transaction confirmation

`@JvmOverloads fun purchase(credentials: `[`Credentials`](../../ch.decent.sdk.crypto/-credentials/index.md)`, uri: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee()): Single<`[`TransactionConfirmation`](../../ch.decent.sdk.model/-transaction-confirmation/index.md)`>`

Purchase a content.

### Parameters

`credentials` - account credentials

`uri` - uri of the content

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough

**Return**
a transaction confirmation

