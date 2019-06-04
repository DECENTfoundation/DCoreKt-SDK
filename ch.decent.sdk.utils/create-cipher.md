[library](../index.md) / [ch.decent.sdk.utils](index.md) / [createCipher](./create-cipher.md)

# createCipher

`fun createCipher(forEncryption: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, iv: `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)`, key: `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)`): PaddedBufferedBlockCipher`

create AES cipher with a fixed block size of 128 bits and available key sizes 128/192/256 bits

### Parameters

`forEncryption` - if true the cipher is initialised for encryption, if false for decryption

`iv` - initial vector, 128 bits

`key` - encryption key, 128/192/256 bits