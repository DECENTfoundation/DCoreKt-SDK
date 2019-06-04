[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [MiningApi](index.md) / [vote](./vote.md)

# vote

`fun vote(credentials: `[`Credentials`](../../ch.decent.sdk.crypto/-credentials/index.md)`, minerIds: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`>, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee()): Single<`[`TransactionConfirmation`](../../ch.decent.sdk.model/-transaction-confirmation/index.md)`>`

Vote for miner.

### Parameters

`credentials` - account credentials

`minerIds` - list of miner account ids

`fee` - {@link AssetAmount} fee for the operation or asset id, if left undefined the fee will be computed in DCT asset.
When set, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough

**Return**
a transaction confirmation

