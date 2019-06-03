[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [ContentApi](index.md) / [listAllPublishersRelative](./list-all-publishers-relative.md)

# listAllPublishersRelative

`fun listAllPublishersRelative(lowerBound: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, limit: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 100): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`>>`

Get a list of accounts holding publishing manager status.

### Parameters

`lowerBound` - the name of the first account to return. If the named account does not exist, the list will start at the account that comes after lowerbound

`limit` - the maximum number of accounts to return (max: 100)

**Return**
a list of publishing managers

