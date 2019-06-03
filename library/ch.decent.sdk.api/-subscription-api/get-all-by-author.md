[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [SubscriptionApi](index.md) / [getAllByAuthor](./get-all-by-author.md)

# getAllByAuthor

`fun getAllByAuthor(author: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, count: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Subscription`](../../ch.decent.sdk.model/-subscription/index.md)`>>`

Get a list of subscriptions by account (author).

### Parameters

`author` - author account object id, 1.2.*

`count` - maximum number of subscription objects to fetch (must not exceed 100)

**Return**
a list of subscription objects

