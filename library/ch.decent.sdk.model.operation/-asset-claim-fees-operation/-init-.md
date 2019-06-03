[library](../../index.md) / [ch.decent.sdk.model.operation](../index.md) / [AssetClaimFeesOperation](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`AssetClaimFeesOperation(issuer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, uia: `[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)`, dct: `[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee())`

Claim fees operation constructor. Claim funds from asset pool, only the asset issuer can clam.

### Parameters

`issuer` - account id issuing the asset

`uia` - the uia asset value to claim

`dct` - the dct asset value to claim

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough