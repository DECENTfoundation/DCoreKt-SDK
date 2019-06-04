[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [MessagingApi](index.md) / [getAllDecryptedForReceiver](./get-all-decrypted-for-receiver.md)

# getAllDecryptedForReceiver

`fun getAllDecryptedForReceiver(credentials: `[`Credentials`](../../ch.decent.sdk.crypto/-credentials/index.md)`, maxCount: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 1000): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Message`](../../ch.decent.sdk.model/-message/index.md)`>>`

Get all messages for receiver and decrypt

### Parameters

`credentials` - receiver account credentials with decryption keys

`maxCount` - max items to return

**Return**
list of messages

