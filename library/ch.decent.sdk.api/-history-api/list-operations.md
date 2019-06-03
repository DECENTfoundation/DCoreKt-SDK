[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [HistoryApi](index.md) / [listOperations](./list-operations.md)

# listOperations

`@JvmOverloads fun listOperations(accountId: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, startId: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)` = ObjectType.OPERATION_HISTORY_OBJECT.genericId, stopId: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)` = ObjectType.OPERATION_HISTORY_OBJECT.genericId, limit: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 100): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`OperationHistory`](../../ch.decent.sdk.model/-operation-history/index.md)`>>`

Get account history of operations.

### Parameters

`accountId` - object id of the account whose history should be queried, 1.2.*

`limit` - number of entries, max 100

`startId` - id of the history object to start from, use [ObjectType.OPERATION_HISTORY_OBJECT.genericId](../../ch.decent.sdk.model/-object-type/generic-id.md) to ignore

`stopId` - id of the history object to stop at, use [ObjectType.OPERATION_HISTORY_OBJECT.genericId](../../ch.decent.sdk.model/-object-type/generic-id.md) to ignore

**Return**
a list of operations performed by account, ordered from most recent to oldest

