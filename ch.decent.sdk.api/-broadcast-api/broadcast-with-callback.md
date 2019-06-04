[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [BroadcastApi](index.md) / [broadcastWithCallback](./broadcast-with-callback.md)

# broadcastWithCallback

`fun broadcastWithCallback(transaction: `[`Transaction`](../../ch.decent.sdk.model/-transaction/index.md)`): Single<`[`TransactionConfirmation`](../../ch.decent.sdk.model/-transaction-confirmation/index.md)`>`

broadcast transaction to DCore with callback

### Parameters

`transaction` - transaction to broadcast

**Return**
a transaction confirmation

`@JvmOverloads fun broadcastWithCallback(privateKey: `[`ECKeyPair`](../../ch.decent.sdk.crypto/-e-c-key-pair/index.md)`, operations: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`BaseOperation`](../../ch.decent.sdk.model.operation/-base-operation/index.md)`>, expiration: Duration = api.transactionExpiration): Single<`[`TransactionConfirmation`](../../ch.decent.sdk.model/-transaction-confirmation/index.md)`>`

broadcast operations to DCore with callback when applied

### Parameters

`privateKey` - private key

`operations` - operations to be submitted to DCore

`expiration` - transaction expiration in seconds, after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block

**Return**
a transaction confirmation

`@JvmOverloads fun broadcastWithCallback(privateKey: `[`ECKeyPair`](../../ch.decent.sdk.crypto/-e-c-key-pair/index.md)`, operation: `[`BaseOperation`](../../ch.decent.sdk.model.operation/-base-operation/index.md)`, expiration: Duration = api.transactionExpiration): Single<`[`TransactionConfirmation`](../../ch.decent.sdk.model/-transaction-confirmation/index.md)`>`

broadcast operation to DCore with callback when applied

### Parameters

`privateKey` - private key

`operation` - operation to be submitted to DCore

`expiration` - transaction expiration in seconds, after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block

**Return**
a transaction confirmation

`@JvmOverloads fun broadcastWithCallback(privateKey: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, operations: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`BaseOperation`](../../ch.decent.sdk.model.operation/-base-operation/index.md)`>, expiration: Duration = api.transactionExpiration): Single<`[`TransactionConfirmation`](../../ch.decent.sdk.model/-transaction-confirmation/index.md)`>`

broadcast operations to DCore with callback when applied

### Parameters

`privateKey` - private key in base58 format

`operations` - operations to be submitted to DCore

`expiration` - transaction expiration in seconds, after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block

**Return**
a transaction confirmation

`@JvmOverloads fun broadcastWithCallback(privateKey: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, operation: `[`BaseOperation`](../../ch.decent.sdk.model.operation/-base-operation/index.md)`, expiration: Duration = api.transactionExpiration): Single<`[`TransactionConfirmation`](../../ch.decent.sdk.model/-transaction-confirmation/index.md)`>`

broadcast operation to DCore with callback when applied

### Parameters

`privateKey` - private key in base58 format

`operation` - operation to be submitted to DCore

`expiration` - transaction expiration in seconds, after the expiry the transaction is removed from recent pool and will be dismissed if not included in DCore block

**Return**
a transaction confirmation

