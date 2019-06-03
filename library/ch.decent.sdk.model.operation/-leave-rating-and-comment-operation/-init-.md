[library](../../index.md) / [ch.decent.sdk.model.operation](../index.md) / [LeaveRatingAndCommentOperation](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`LeaveRatingAndCommentOperation(uri: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, consumer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, rating: `[`Byte`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte/index.html)`, comment: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee())`

Leave comment and rating operation constructor

### Parameters

`uri` - uri of the content

`consumer` - chain object id of the buyer's account

`rating` - 1-5 stars

`comment` - max 100 chars

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough