[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [MiningApi](index.md) / [createVoteOperation](./create-vote-operation.md)

# createVoteOperation

`fun createVoteOperation(accountId: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, minerIds: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`>, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee()): Single<`[`AccountUpdateOperation`](../../ch.decent.sdk.model.operation/-account-update-operation/index.md)`>`

Create vote for miner operation.

### Parameters

`accountId` - account object id, 1.2.*

`minerIds` - list of miner account ids

`fee` - {@link AssetAmount} fee for the operation or asset id, if left undefined the fee will be computed in DCT asset.
When set, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough

**Return**
a transaction confirmation

