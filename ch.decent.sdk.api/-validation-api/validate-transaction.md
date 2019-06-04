[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [ValidationApi](index.md) / [validateTransaction](./validate-transaction.md)

# validateTransaction

`fun validateTransaction(transaction: `[`Transaction`](../../ch.decent.sdk.model/-transaction/index.md)`): Single<`[`ProcessedTransaction`](../../ch.decent.sdk.model/-processed-transaction/index.md)`>`

Validates a transaction against the current state without broadcasting it on the network.

### Parameters

`transaction` - signed transaction

**Return**
[ProcessedTransaction](../../ch.decent.sdk.model/-processed-transaction/index.md) or fails with [DCoreException](../../ch.decent.sdk.exception/-d-core-exception.md)

