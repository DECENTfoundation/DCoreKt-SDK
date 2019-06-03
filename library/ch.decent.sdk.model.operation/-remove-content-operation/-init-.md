[library](../../index.md) / [ch.decent.sdk.model.operation](../index.md) / [RemoveContentOperation](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`RemoveContentOperation(author: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, uri: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee())`

Remove content operation. Sets expiration to head block time, so the content cannot be purchased, but remains in database.

### Parameters

`author` - content author

`uri` - content uri

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough