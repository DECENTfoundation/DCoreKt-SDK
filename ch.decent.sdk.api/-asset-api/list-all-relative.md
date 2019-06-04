[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [AssetApi](index.md) / [listAllRelative](./list-all-relative.md)

# listAllRelative

`@JvmOverloads fun listAllRelative(lowerBound: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, limit: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 100): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Asset`](../../ch.decent.sdk.model/-asset/index.md)`>>`

Get assets alphabetically by symbol name.

### Parameters

`lowerBound` - lower bound of symbol names to retrieve

`limit` - maximum number of assets to fetch (must not exceed 100)

**Return**
the assets found

