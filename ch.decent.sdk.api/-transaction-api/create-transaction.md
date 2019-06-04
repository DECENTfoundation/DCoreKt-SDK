[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [TransactionApi](index.md) / [createTransaction](./create-transaction.md)

# createTransaction

`@JvmOverloads fun createTransaction(operations: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`BaseOperation`](../../ch.decent.sdk.model.operation/-base-operation/index.md)`>, expiration: Duration = api.transactionExpiration): Single<`[`Transaction`](../../ch.decent.sdk.model/-transaction/index.md)`>`

create unsigned transaction

### Parameters

`operations` - operations to include in transaction

`expiration` - transaction expiration in seconds, after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block`@JvmOverloads fun createTransaction(operation: `[`BaseOperation`](../../ch.decent.sdk.model.operation/-base-operation/index.md)`, expiration: Duration = api.transactionExpiration): Single<`[`Transaction`](../../ch.decent.sdk.model/-transaction/index.md)`>`

create unsigned transaction

### Parameters

`operation` - operation to include in transaction

`expiration` - transaction expiration in seconds, after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block