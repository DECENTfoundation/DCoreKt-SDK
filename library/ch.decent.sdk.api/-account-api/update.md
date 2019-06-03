[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [AccountApi](index.md) / [update](./update.md)

# update

`@JvmOverloads fun update(credentials: `[`Credentials`](../../ch.decent.sdk.crypto/-credentials/index.md)`, options: `[`AccountOptions`](../../ch.decent.sdk.model/-account-options/index.md)`? = null, active: `[`Authority`](../../ch.decent.sdk.model/-authority/index.md)`? = null, owner: `[`Authority`](../../ch.decent.sdk.model/-authority/index.md)`? = null, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee()): Single<`[`TransactionConfirmation`](../../ch.decent.sdk.model/-transaction-confirmation/index.md)`>`

Update account

### Parameters

`credentials` - account credentials

`options` - new account options

`active` - new active authority

`owner` - new owner authority

`fee` - {@link AssetAmount} fee for the operation or asset id, if left undefined the fee will be computed in DCT asset.
When set, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough

**Return**
a transaction confirmation

