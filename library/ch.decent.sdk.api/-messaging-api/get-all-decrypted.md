[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [MessagingApi](index.md) / [getAllDecrypted](./get-all-decrypted.md)

# getAllDecrypted

`fun getAllDecrypted(credentials: `[`Credentials`](../../ch.decent.sdk.crypto/-credentials/index.md)`, sender: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`? = null, receiver: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`? = null, maxCount: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 1000): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Message`](../../ch.decent.sdk.model/-message/index.md)`>>`

Get all messages and decrypt

### Parameters

`credentials` - account credentials used for decryption, must be either sender's or receiver's

`sender` - filter by sender account id

`receiver` - filter by receiver account id

`maxCount` - max items to return

**Return**
list of messages

