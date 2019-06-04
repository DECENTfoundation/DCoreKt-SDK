[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [HistoryApi](index.md) / [findAllOperations](./find-all-operations.md)

# findAllOperations

`fun findAllOperations(accountId: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, assets: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`> = emptyList(), recipientAccount: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`? = null, fromBlock: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)` = 0, toBlock: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)` = 0, startOffset: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)` = 0, limit: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 100): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`BalanceChange`](../../ch.decent.sdk.model/-balance-change/index.md)`>>`

Returns the most recent balance operations on the named account.
This returns a list of operation history objects, which describe activity on the account.

### Parameters

`accountId` - object id of the account whose history should be queried, 1.2.*

`assets` - list of asset object ids to filter or empty for all assets

`recipientAccount` - partner account object id to filter transfers to specific account, 1.2.* or null

`fromBlock` - filtering parameter, starting block number (can be determined from time) or zero when not used

`toBlock` - filtering parameter, ending block number or zero when not used

`startOffset` - starting offset from zero

`limit` - the number of entries to return (starting from the most recent), max 100

**Return**
a list of balance changes

