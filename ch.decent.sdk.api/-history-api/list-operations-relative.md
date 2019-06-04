[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [HistoryApi](index.md) / [listOperationsRelative](./list-operations-relative.md)

# listOperationsRelative

`fun listOperationsRelative(accountId: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, start: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 0, limit: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 100): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`OperationHistory`](../../ch.decent.sdk.model/-operation-history/index.md)`>>`

Get account history of operations.

### Parameters

`accountId` - object id of the account whose history should be queried, 1.2.*

`start` - sequence number of the most recent operation to retrieve. 0 is default, which will start querying from the most recent operation

`limit` - maximum number of operations to retrieve (must not exceed 100)

**Return**
a list of operations performed by account, ordered from most recent to oldest

