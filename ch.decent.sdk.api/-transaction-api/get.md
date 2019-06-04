[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [TransactionApi](index.md) / [get](./get.md)

# get

`fun get(trxId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): Single<`[`ProcessedTransaction`](../../ch.decent.sdk.model/-processed-transaction/index.md)`>`

This method will return the transaction for the given ID or it will return [ch.decent.sdk.exception.ObjectNotFoundException](../../ch.decent.sdk.exception/-object-not-found-exception/index.md).
The ID can be retrieved from [Transaction](../../ch.decent.sdk.model/-transaction/index.md) or [TransactionConfirmation](../../ch.decent.sdk.model/-transaction-confirmation/index.md) objects.

Note: By default these objects are not tracked, the transaction_history_plugin must be loaded for these objects to be maintained.
Just because it is not known does not mean it wasn't included in the DCore.

### Parameters

`trxId` - transaction id

**Return**
a transaction if found, [ch.decent.sdk.exception.ObjectNotFoundException](../../ch.decent.sdk.exception/-object-not-found-exception/index.md) otherwise

`fun get(blockNum: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, trxInBlock: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): Single<`[`ProcessedTransaction`](../../ch.decent.sdk.model/-processed-transaction/index.md)`>`

get applied transaction

### Parameters

`blockNum` - block number

`trxInBlock` - position of the transaction in block

**Return**
a transaction if found, [ch.decent.sdk.exception.ObjectNotFoundException](../../ch.decent.sdk.exception/-object-not-found-exception/index.md) otherwise

`fun get(confirmation: `[`TransactionConfirmation`](../../ch.decent.sdk.model/-transaction-confirmation/index.md)`): Single<`[`ProcessedTransaction`](../../ch.decent.sdk.model/-processed-transaction/index.md)`>`

get applied transaction

### Parameters

`confirmation` - confirmation returned from transaction broadcast

**Return**
a transaction if found, [ch.decent.sdk.exception.ObjectNotFoundException](../../ch.decent.sdk.exception/-object-not-found-exception/index.md) otherwise

