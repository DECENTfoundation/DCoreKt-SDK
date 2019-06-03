[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [BroadcastApi](index.md) / [broadcast](./broadcast.md)

# broadcast

`fun broadcast(transaction: `[`Transaction`](../../ch.decent.sdk.model/-transaction/index.md)`): Single<`[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`>`

broadcast transaction to DCore

### Parameters

`transaction` - transaction to broadcast`@JvmOverloads fun broadcast(privateKey: `[`ECKeyPair`](../../ch.decent.sdk.crypto/-e-c-key-pair/index.md)`, operations: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`BaseOperation`](../../ch.decent.sdk.model.operation/-base-operation/index.md)`>, expiration: Duration = api.transactionExpiration): Single<`[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`>`

broadcast operations to DCore

### Parameters

`privateKey` - private key

`operations` - operations to be submitted to DCore

`expiration` - transaction expiration in seconds, after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block`@JvmOverloads fun broadcast(privateKey: `[`ECKeyPair`](../../ch.decent.sdk.crypto/-e-c-key-pair/index.md)`, operation: `[`BaseOperation`](../../ch.decent.sdk.model.operation/-base-operation/index.md)`, expiration: Duration = api.transactionExpiration): Single<`[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`>`

broadcast operation to DCore

### Parameters

`privateKey` - private key

`operation` - operation to be submitted to DCore

`expiration` - transaction expiration in seconds, after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block`@JvmOverloads fun broadcast(privateKey: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, operations: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`BaseOperation`](../../ch.decent.sdk.model.operation/-base-operation/index.md)`>, expiration: Duration = api.transactionExpiration): Single<`[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`>`

broadcast operations to DCore

### Parameters

`privateKey` - private key in base58 format

`operations` - operations to be submitted to DCore

`expiration` - transaction expiration in seconds, after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block`@JvmOverloads fun broadcast(privateKey: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, operation: `[`BaseOperation`](../../ch.decent.sdk.model.operation/-base-operation/index.md)`, expiration: Duration = api.transactionExpiration): Single<`[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`>`

broadcast operation to DCore

### Parameters

`privateKey` - private key in base58 format

`operation` - operation to be submitted to DCore

`expiration` - transaction expiration in seconds, after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block