[library](../../index.md) / [ch.decent.sdk.model.operation](../index.md) / [AssetFundPoolsOperation](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`AssetFundPoolsOperation(from: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, uia: `[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)`, dct: `[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee())`

Fund asset pool operation constructor. Any account can fund a pool.

### Parameters

`from` - account id funding the pool

`uia` - the uia asset value to fund

`dct` - the dct asset value to fund

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough