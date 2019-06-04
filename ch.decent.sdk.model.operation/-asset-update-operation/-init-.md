[library](../../index.md) / [ch.decent.sdk.model.operation](../index.md) / [AssetUpdateOperation](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`AssetUpdateOperation(issuer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, assetToUpdate: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, newDescription: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, newIssuer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`?, maxSupply: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, coreExchangeRate: `[`ExchangeRate`](../../ch.decent.sdk.model/-exchange-rate/index.md)`, exchangeable: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee())`

Update asset operation constructor.

### Parameters

`issuer` - account id issuing the asset

`assetToUpdate` - asset to update

`coreExchangeRate` - new exchange rate

`newDescription` - new description

`exchangeable` - enable converting the asset to DCT, so it can be used to pay for fees

`maxSupply` - new max supply

`newIssuer` - a new issuer account id

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough