[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [MiningApi](index.md) / [getFeedsByMiner](./get-feeds-by-miner.md)

# getFeedsByMiner

`fun getFeedsByMiner(account: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, count: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 100): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>>`

Get a list of published price feeds by a miner.

### Parameters

`account` - account object id, 1.2.*

`count` - maximum number of price feeds to fetch (must not exceed 100)

**Return**
a list of price feeds published by the miner

