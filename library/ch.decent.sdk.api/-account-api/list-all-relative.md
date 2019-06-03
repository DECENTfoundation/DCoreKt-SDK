[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [AccountApi](index.md) / [listAllRelative](./list-all-relative.md)

# listAllRelative

`@JvmOverloads fun listAllRelative(lowerBound: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, limit: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 1000): Single<`[`Map`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`>>`

Get names and IDs for registered accounts.

### Parameters

`lowerBound` - lower bound of the first name to return

`limit` - number of items to get, max 1000

**Return**
map of account names to corresponding IDs

