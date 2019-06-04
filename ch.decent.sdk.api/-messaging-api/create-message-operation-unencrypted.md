[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [MessagingApi](index.md) / [createMessageOperationUnencrypted](./create-message-operation-unencrypted.md)

# createMessageOperationUnencrypted

`fun createMessageOperationUnencrypted(credentials: `[`Credentials`](../../ch.decent.sdk.crypto/-credentials/index.md)`, messages: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Pair`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)`<`[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>>, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee()): Single<`[`SendMessageOperation`](../../ch.decent.sdk.model.operation/-send-message-operation/index.md)`>`

Create message operation, send messages to multiple receivers, unencrypted

### Parameters

`credentials` - sender account credentials

`messages` - a list of pairs of receiver account id and message

**Return**
send message operation

