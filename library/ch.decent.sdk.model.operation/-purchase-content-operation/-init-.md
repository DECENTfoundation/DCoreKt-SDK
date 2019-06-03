[library](../../index.md) / [ch.decent.sdk.model.operation](../index.md) / [PurchaseContentOperation](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`PurchaseContentOperation(credentials: `[`Credentials`](../../ch.decent.sdk.crypto/-credentials/index.md)`, content: `[`Content`](../../ch.decent.sdk.model/-content/index.md)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)`)``PurchaseContentOperation(uri: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, consumer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, price: `[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)`, publicElGamal: `[`PubKey`](../../ch.decent.sdk.model/-pub-key/index.md)`, regionCode: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)` = Regions.ALL.id, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee())`

Request to purchase content operation constructor

### Parameters

`uri` - uri of the content

`consumer` - chain object id of the buyer's account

`price` - price of content, can be equal to or higher then specified in content

`publicElGamal` - public el gamal key

`regionCode` - region code of the consumer

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough