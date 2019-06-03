[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [ContentApi](index.md) / [restoreEncryptionKey](./restore-encryption-key.md)

# restoreEncryptionKey

`fun restoreEncryptionKey(elGamalPrivate: `[`PubKey`](../../ch.decent.sdk.model/-pub-key/index.md)`, purchaseId: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`): Single<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>`

Restores encryption key from key parts stored in buying object.

### Parameters

`elGamalPrivate` - the private El Gamal key

`purchaseId` - the purchase object

**Return**
AES encryption key

