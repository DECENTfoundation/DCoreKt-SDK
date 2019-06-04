[library](../index.md) / [ch.decent.sdk.utils](./index.md)

## Package ch.decent.sdk.utils

### Types

| [Base58](-base58/index.md) | `object Base58`<br>Base58 is a way to encode Bitcoin addresses (or arbitrary data) as alphanumeric strings. |
| [ElGamal](-el-gamal/index.md) | `object ElGamal` |

### Extensions for External Classes

| [java.math.BigInteger](java.math.-big-integer/index.md) |  |
| [kotlin.ByteArray](kotlin.-byte-array/index.md) |  |
| [kotlin.String](kotlin.-string/index.md) |  |

### Functions

| [createCipher](create-cipher.md) | `fun createCipher(forEncryption: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, iv: `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)`, key: `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)`): PaddedBufferedBlockCipher`<br>create AES cipher with a fixed block size of 128 bits and available key sizes 128/192/256 bits |
| [decryptAes](decrypt-aes.md) | `fun decryptAes(keyWithIv: `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)`, encrypted: `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)`): `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)<br>`fun decryptAes(iv: `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)`, key: `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)`, encrypted: `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)`): `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html) |
| [decryptAesWithChecksum](decrypt-aes-with-checksum.md) | `fun decryptAesWithChecksum(key: `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)`, encrypted: `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)`): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [encryptAes](encrypt-aes.md) | `fun encryptAes(keyWithIv: `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)`, clear: `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)`): `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)<br>`fun encryptAes(iv: `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)`, key: `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)`, clear: `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)`): `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html) |
| [generateEntropy](generate-entropy.md) | `fun generateEntropy(power: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 250): `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html) |
| [generateNonce](generate-nonce.md) | `fun generateNonce(): `[`BigInteger`](http://docs.oracle.com/javase/6/docs/api/java/math/BigInteger.html) |
| [privateElGamal](private-el-gamal.md) | `fun `[`ECKeyPair`](../ch.decent.sdk.crypto/-e-c-key-pair/index.md)`.privateElGamal(): `[`PubKey`](../ch.decent.sdk.model/-pub-key/index.md) |
| [publicElGamal](public-el-gamal.md) | `fun `[`ECKeyPair`](../ch.decent.sdk.crypto/-e-c-key-pair/index.md)`.publicElGamal(): `[`PubKey`](../ch.decent.sdk.model/-pub-key/index.md) |
| [secret](secret.md) | `fun `[`ECKeyPair`](../ch.decent.sdk.crypto/-e-c-key-pair/index.md)`.secret(address: `[`Address`](../ch.decent.sdk.crypto/-address/index.md)`, nonce: `[`BigInteger`](http://docs.oracle.com/javase/6/docs/api/java/math/BigInteger.html)`): `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html) |

