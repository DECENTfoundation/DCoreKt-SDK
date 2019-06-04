[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [SubscriptionApi](./index.md)

# SubscriptionApi

`class SubscriptionApi : `[`BaseApi`](../-base-api/index.md)

### Inherited Properties

| [api](../-base-api/api.md) | `val api: `[`DCoreApi`](../../ch.decent.sdk/-d-core-api/index.md) |

### Functions

| [get](get.md) | `fun get(id: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`): Single<`[`Subscription`](../../ch.decent.sdk.model/-subscription/index.md)`>`<br>Get a subscription object by ID. |
| [getAllActiveByAuthor](get-all-active-by-author.md) | `fun getAllActiveByAuthor(author: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, count: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Subscription`](../../ch.decent.sdk.model/-subscription/index.md)`>>`<br>Get a list of active (not expired) subscriptions by account (author). |
| [getAllActiveByConsumer](get-all-active-by-consumer.md) | `fun getAllActiveByConsumer(consumer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, count: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Subscription`](../../ch.decent.sdk.model/-subscription/index.md)`>>`<br>Get a list of active (not expired) subscriptions by account (consumer). |
| [getAllByAuthor](get-all-by-author.md) | `fun getAllByAuthor(author: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, count: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Subscription`](../../ch.decent.sdk.model/-subscription/index.md)`>>`<br>Get a list of subscriptions by account (author). |
| [getAllByConsumer](get-all-by-consumer.md) | `fun getAllByConsumer(consumer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, count: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Subscription`](../../ch.decent.sdk.model/-subscription/index.md)`>>`<br>Get a list of subscriptions by account (consumer). |

