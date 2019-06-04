[library](../../index.md) / [ch.decent.sdk.crypto](../index.md) / [ECKeyPair](./index.md)

# ECKeyPair

`class ECKeyPair`

### Types

| [ECDSASignature](-e-c-d-s-a-signature/index.md) | `data class ECDSASignature` |

### Constructors

| [&lt;init&gt;](-init-.md) | `ECKeyPair(secureRandom: `[`SecureRandom`](http://docs.oracle.com/javase/6/docs/api/java/security/SecureRandom.html)`)` |

### Properties

| [compressed](compressed.md) | `val compressed: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`?` |
| [private](private.md) | `val private: `[`BigInteger`](http://docs.oracle.com/javase/6/docs/api/java/math/BigInteger.html)`?` |
| [privateBytes](private-bytes.md) | `val privateBytes: `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html) |
| [public](public.md) | `val public: ECPoint` |

### Functions

| [equals](equals.md) | `fun equals(other: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`?): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hashCode](hash-code.md) | `fun hashCode(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [sign](sign.md) | `fun sign(input: `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)`): `[`ECDSASignature`](-e-c-d-s-a-signature/index.md) |
| [signature](signature.md) | `fun signature(data: `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)`): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>should be called in a loop until a 'canonical' signature is returned, slightly changing input data on every call |

### Companion Object Functions

| [checkCanonicalSignature](check-canonical-signature.md) | `fun checkCanonicalSignature(sigData: `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [fromBase58](from-base58.md) | `fun fromBase58(encoded: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`ECKeyPair`](./index.md) |
| [fromPrivate](from-private.md) | `fun fromPrivate(key: `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)`, compressed: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = true): `[`ECKeyPair`](./index.md) |
| [fromPublic](from-public.md) | `fun fromPublic(key: `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)`): `[`ECKeyPair`](./index.md)<br>`fun fromPublic(point: ECPoint): `[`ECKeyPair`](./index.md) |
| [recoverFromSignature](recover-from-signature.md) | `fun recoverFromSignature(recId: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, sig: `[`ECDSASignature`](-e-c-d-s-a-signature/index.md)`, message: `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)`): `[`ECKeyPair`](./index.md)`?`<br>Given the components of a signature and a selector value, recover and return the public keyPair that generated the signature according to the algorithm in SEC1v2 section 4.1.6. |

### Extension Functions

| [address](../address.md) | `fun `[`ECKeyPair`](./index.md)`.address(): `[`Address`](../-address/index.md) |
| [base58](../base58.md) | `fun `[`ECKeyPair`](./index.md)`.base58(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [dpk](../dpk.md) | `fun `[`ECKeyPair`](./index.md)`.dpk(): `[`DumpedPrivateKey`](../-dumped-private-key/index.md) |
| [privateElGamal](../../ch.decent.sdk.utils/private-el-gamal.md) | `fun `[`ECKeyPair`](./index.md)`.privateElGamal(): `[`PubKey`](../../ch.decent.sdk.model/-pub-key/index.md) |
| [publicElGamal](../../ch.decent.sdk.utils/public-el-gamal.md) | `fun `[`ECKeyPair`](./index.md)`.publicElGamal(): `[`PubKey`](../../ch.decent.sdk.model/-pub-key/index.md) |
| [secret](../../ch.decent.sdk.utils/secret.md) | `fun `[`ECKeyPair`](./index.md)`.secret(address: `[`Address`](../-address/index.md)`, nonce: `[`BigInteger`](http://docs.oracle.com/javase/6/docs/api/java/math/BigInteger.html)`): `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html) |

