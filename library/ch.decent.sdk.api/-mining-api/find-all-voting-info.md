[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [MiningApi](index.md) / [findAllVotingInfo](./find-all-voting-info.md)

# findAllVotingInfo

`fun findAllVotingInfo(searchTerm: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, order: `[`SearchMinerVotingOrder`](../../ch.decent.sdk.model/-search-miner-voting-order/index.md)` = SearchMinerVotingOrder.NAME_DESC, id: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`? = null, accountName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`? = null, onlyMyVotes: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = false, limit: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 1000): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`MinerVotingInfo`](../../ch.decent.sdk.model/-miner-voting-info/index.md)`>>`

Get miner voting info list by account that match search term.

### Parameters

`searchTerm` - miner name

`order` - available options are defined in [SearchMinerVotingOrder](../../ch.decent.sdk.model/-search-miner-voting-order/index.md)

`id` - the object id of the miner to start searching from, 1.4.* or null when start from beginning

`accountName` - account name or null when searching without account

`onlyMyVotes` - when true it selects only votes given by account

`limit` - maximum number of miners info to fetch (must not exceed 1000)

**Return**
a list of miner voting info

