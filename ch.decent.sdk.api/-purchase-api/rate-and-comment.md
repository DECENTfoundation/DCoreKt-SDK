[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [PurchaseApi](index.md) / [rateAndComment](./rate-and-comment.md)

# rateAndComment

`fun rateAndComment(credentials: `[`Credentials`](../../ch.decent.sdk.crypto/-credentials/index.md)`, uri: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, rating: `[`Byte`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte/index.html)`, comment: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee()): Single<`[`TransactionConfirmation`](../../ch.decent.sdk.model/-transaction-confirmation/index.md)`>`

Rate and comment content operation.

### Parameters

`credentials` - account credentials

`uri` - a uri of the content

`rating` - 1-5 stars

`comment` - max 100 chars

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough

**Return**
a rate and comment content operation

