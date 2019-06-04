[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [MessagingApi](index.md) / [send](./send.md)

# send

`fun send(credentials: `[`Credentials`](../../ch.decent.sdk.crypto/-credentials/index.md)`, messages: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Pair`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)`<`[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>>, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee()): Single<`[`TransactionConfirmation`](../../ch.decent.sdk.model/-transaction-confirmation/index.md)`>`

Send messages to receivers

### Parameters

`credentials` - sender account credentials

`messages` - pairs of receiver account id and message

**Return**
a transaction confirmation

