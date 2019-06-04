[library](../../index.md) / [ch.decent.sdk.model.operation](../index.md) / [AssetReserveOperation](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`AssetReserveOperation(payer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, amount: `[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee())`

Reserve funds operation constructor. Return issued funds to the issuer of the asset.

### Parameters

`payer` - account id providing the funds

`amount` - asset amount to remove from current supply

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough