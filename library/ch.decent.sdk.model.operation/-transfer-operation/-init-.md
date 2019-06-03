[library](../../index.md) / [ch.decent.sdk.model.operation](../index.md) / [TransferOperation](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`TransferOperation(from: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, to: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, amount: `[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)`, memo: `[`Memo`](../../ch.decent.sdk.model/-memo/index.md)`? = null, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee())`

Transfer operation constructor

### Parameters

`from` - account object id of the sender

`to` - account object id or content object id of the receiver

`amount` - an [AssetAmount](../../ch.decent.sdk.model/-asset-amount/index.md) to transfer

`memo` - optional string note

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough