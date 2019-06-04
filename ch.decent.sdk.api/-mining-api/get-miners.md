[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [MiningApi](index.md) / [getMiners](./get-miners.md)

# getMiners

`fun getMiners(minerIds: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`>): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Miner`](../../ch.decent.sdk.model/-miner/index.md)`>>`

Returns list of miners by their Ids

### Parameters

`minerIds` - miner ids

**Return**
a list of miners

`fun getMiners(): Single<`[`Map`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, `[`Miner`](../../ch.decent.sdk.model/-miner/index.md)`>>`

Returns map of the first 1000 miners by their name to miner account

**Return**
a map of miner name to miner account

