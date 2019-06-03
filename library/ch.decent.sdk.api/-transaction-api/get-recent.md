[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [TransactionApi](index.md) / [getRecent](./get-recent.md)

# getRecent

`fun getRecent(trxId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): Single<`[`ProcessedTransaction`](../../ch.decent.sdk.model/-processed-transaction/index.md)`>`

If the transaction has not expired, this method will return the transaction for the given ID or it will return [ch.decent.sdk.exception.ObjectNotFoundException](../../ch.decent.sdk.exception/-object-not-found-exception/index.md).
Just because it is not known does not mean it wasn't included in the DCore. The ID can be retrieved from [Transaction](../../ch.decent.sdk.model/-transaction/index.md) or [TransactionConfirmation](../../ch.decent.sdk.model/-transaction-confirmation/index.md) objects.

### Parameters

`trxId` - transaction id

**Return**
a transaction if found, [ch.decent.sdk.exception.ObjectNotFoundException](../../ch.decent.sdk.exception/-object-not-found-exception/index.md) otherwise

