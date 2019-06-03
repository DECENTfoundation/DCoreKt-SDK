[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [ContentApi](index.md) / [createTransfer](./create-transfer.md)

# createTransfer

`@JvmOverloads fun createTransfer(credentials: `[`Credentials`](../../ch.decent.sdk.crypto/-credentials/index.md)`, id: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, amount: `[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)`, memo: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`? = null, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee()): Single<`[`TransferOperation`](../../ch.decent.sdk.model.operation/-transfer-operation/index.md)`>`

Create amount transfer operation of one asset to content. Amount is transferred to author and co-authors of the content, if they are specified.
Fees are paid by the "from" account.

### Parameters

`credentials` - account credentials

`id` - content id

`amount` - amount to send with asset type

`memo` - optional unencrypted message

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough

**Return**
a transfer operation

