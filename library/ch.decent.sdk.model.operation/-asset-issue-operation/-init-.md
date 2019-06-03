[library](../../index.md) / [ch.decent.sdk.model.operation](../index.md) / [AssetIssueOperation](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`AssetIssueOperation(issuer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, assetToIssue: `[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)`, issueToAccount: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, memo: `[`Memo`](../../ch.decent.sdk.model/-memo/index.md)`? = null, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee())`

Issue asset operation constructor. Only the issuer of the asset can issue some funds until maxSupply is reached.

### Parameters

`issuer` - asset issuer account id

`assetToIssue` - asset amount to issue

`issueToAccount` - account id receiving the created funds

`memo` - optional memo for receiver

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough