[library](../../index.md) / [ch.decent.sdk.model.operation](../index.md) / [AccountUpdateOperation](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`AccountUpdateOperation(account: `[`Account`](../../ch.decent.sdk.model/-account/index.md)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)`)`
`AccountUpdateOperation(account: `[`Account`](../../ch.decent.sdk.model/-account/index.md)`, votes: `[`Set`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)`<`[`VoteId`](../../ch.decent.sdk.model/-vote-id/index.md)`>, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)`)``AccountUpdateOperation(accountId: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, owner: `[`Authority`](../../ch.decent.sdk.model/-authority/index.md)`? = null, active: `[`Authority`](../../ch.decent.sdk.model/-authority/index.md)`? = null, options: `[`AccountOptions`](../../ch.decent.sdk.model/-account-options/index.md)`? = null, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee())`

Request to account update operation constructor

### Parameters

`accountId` - account

`owner` - owner authority

`active` - active authority

`options` - account options

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough