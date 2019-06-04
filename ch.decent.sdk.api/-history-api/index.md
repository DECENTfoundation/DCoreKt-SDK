[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [HistoryApi](./index.md)

# HistoryApi

`class HistoryApi : `[`BaseApi`](../-base-api/index.md)

### Inherited Properties

| [api](../-base-api/api.md) | `val api: `[`DCoreApi`](../../ch.decent.sdk/-d-core-api/index.md) |

### Functions

| [findAllOperations](find-all-operations.md) | `fun findAllOperations(accountId: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, assets: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`> = emptyList(), recipientAccount: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`? = null, fromBlock: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)` = 0, toBlock: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)` = 0, startOffset: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)` = 0, limit: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 100): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`BalanceChange`](../../ch.decent.sdk.model/-balance-change/index.md)`>>`<br>Returns the most recent balance operations on the named account. This returns a list of operation history objects, which describe activity on the account. |
| [getOperation](get-operation.md) | `fun getOperation(accountId: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, operationId: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`): Single<`[`BalanceChange`](../../ch.decent.sdk.model/-balance-change/index.md)`>`<br>Returns balance operation on the account and operation id. |
| [listOperations](list-operations.md) | `fun listOperations(accountId: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, startId: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)` = ObjectType.OPERATION_HISTORY_OBJECT.genericId, stopId: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)` = ObjectType.OPERATION_HISTORY_OBJECT.genericId, limit: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 100): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`OperationHistory`](../../ch.decent.sdk.model/-operation-history/index.md)`>>`<br>Get account history of operations. |
| [listOperationsRelative](list-operations-relative.md) | `fun listOperationsRelative(accountId: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, start: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 0, limit: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 100): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`OperationHistory`](../../ch.decent.sdk.model/-operation-history/index.md)`>>`<br>Get account history of operations. |

