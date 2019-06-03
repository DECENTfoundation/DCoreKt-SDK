[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [PurchaseApi](index.md) / [createRateAndCommentOperation](./create-rate-and-comment-operation.md)

# createRateAndCommentOperation

`fun createRateAndCommentOperation(uri: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, consumer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, rating: `[`Byte`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte/index.html)`, comment: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee()): Single<`[`LeaveRatingAndCommentOperation`](../../ch.decent.sdk.model.operation/-leave-rating-and-comment-operation/index.md)`>`

Create a rate and comment content operation.

### Parameters

`uri` - a uri of the content

`consumer` - object id of the account, 1.2.*

`rating` - 1-5 stars

`comment` - max 100 chars

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough

**Return**
a rate and comment content operation

