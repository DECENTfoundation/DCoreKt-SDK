[library](../../index.md) / [ch.decent.sdk.model.operation](../index.md) / [AssetUpdateAdvancedOperation](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`AssetUpdateAdvancedOperation(issuer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, assetToUpdate: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, precision: `[`Byte`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte/index.html)`, fixedMaxSupply: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee())`

Update advanced options for the asset.

### Parameters

`issuer` - account id issuing the asset

`assetToUpdate` - asset to update

`precision` - new precision

`fixedMaxSupply` - whether it should be allowed to change max supply, cannot be reverted once set to true

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough