[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [MiningApi](index.md) / [listMinersRelative](./list-miners-relative.md)

# listMinersRelative

`@JvmOverloads fun listMinersRelative(lowerBound: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = "", limit: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 1000): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`MinerId`](../../ch.decent.sdk.model/-miner-id/index.md)`>>`

lookup names and IDs for registered miners

### Parameters

`lowerBound` - lower bound of the first name

`limit` - max 1000

**Return**
list of found miner ids

