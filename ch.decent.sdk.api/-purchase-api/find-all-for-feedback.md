[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [PurchaseApi](index.md) / [findAllForFeedback](./find-all-for-feedback.md)

# findAllForFeedback

`fun findAllForFeedback(uri: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, user: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`? = null, count: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 100, startId: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)` = ObjectType.NULL_OBJECT.genericId): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Purchase`](../../ch.decent.sdk.model/-purchase/index.md)`>>`

Search for feedback.

### Parameters

`uri` - content URI

`user` - feedback author account name

`count` - count	maximum number of feedback objects to fetch

`startId` - the id of purchase object to start searching from

**Return**
a list of purchase objects

