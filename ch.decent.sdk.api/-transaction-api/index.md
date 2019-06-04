[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [TransactionApi](./index.md)

# TransactionApi

`class TransactionApi : `[`BaseApi`](../-base-api/index.md)

### Inherited Properties

| [api](../-base-api/api.md) | `val api: `[`DCoreApi`](../../ch.decent.sdk/-d-core-api/index.md) |

### Functions

| [createTransaction](create-transaction.md) | `fun createTransaction(operations: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`BaseOperation`](../../ch.decent.sdk.model.operation/-base-operation/index.md)`>, expiration: Duration = api.transactionExpiration): Single<`[`Transaction`](../../ch.decent.sdk.model/-transaction/index.md)`>`<br>`fun createTransaction(operation: `[`BaseOperation`](../../ch.decent.sdk.model.operation/-base-operation/index.md)`, expiration: Duration = api.transactionExpiration): Single<`[`Transaction`](../../ch.decent.sdk.model/-transaction/index.md)`>`<br>create unsigned transaction |
| [get](get.md) | `fun get(trxId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): Single<`[`ProcessedTransaction`](../../ch.decent.sdk.model/-processed-transaction/index.md)`>`<br>This method will return the transaction for the given ID or it will return [ch.decent.sdk.exception.ObjectNotFoundException](../../ch.decent.sdk.exception/-object-not-found-exception/index.md). The ID can be retrieved from [Transaction](../../ch.decent.sdk.model/-transaction/index.md) or [TransactionConfirmation](../../ch.decent.sdk.model/-transaction-confirmation/index.md) objects.`fun get(blockNum: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, trxInBlock: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): Single<`[`ProcessedTransaction`](../../ch.decent.sdk.model/-processed-transaction/index.md)`>`<br>`fun get(confirmation: `[`TransactionConfirmation`](../../ch.decent.sdk.model/-transaction-confirmation/index.md)`): Single<`[`ProcessedTransaction`](../../ch.decent.sdk.model/-processed-transaction/index.md)`>`<br>get applied transaction |
| [getAllProposed](get-all-proposed.md) | `fun getAllProposed(accountId: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>>`<br>Get the set of proposed transactions relevant to the specified account id. |
| [getHexDump](get-hex-dump.md) | `fun getHexDump(transaction: `[`Transaction`](../../ch.decent.sdk.model/-transaction/index.md)`): Single<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>`<br>Get a hexdump of the serialized binary form of a transaction. |
| [getRecent](get-recent.md) | `fun getRecent(trxId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): Single<`[`ProcessedTransaction`](../../ch.decent.sdk.model/-processed-transaction/index.md)`>`<br>If the transaction has not expired, this method will return the transaction for the given ID or it will return [ch.decent.sdk.exception.ObjectNotFoundException](../../ch.decent.sdk.exception/-object-not-found-exception/index.md). Just because it is not known does not mean it wasn't included in the DCore. The ID can be retrieved from [Transaction](../../ch.decent.sdk.model/-transaction/index.md) or [TransactionConfirmation](../../ch.decent.sdk.model/-transaction-confirmation/index.md) objects. |

