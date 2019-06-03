[library](../../index.md) / [ch.decent.sdk.crypto](../index.md) / [DumpedPrivateKey](./index.md)

# DumpedPrivateKey

`class DumpedPrivateKey`

Parses and generates private keys in the form used by the Bitcoin "dumpprivkey" command. This is the private key
bytes with a header byte and 4 checksum bytes at the end. If there are 33 private keyPair bytes instead of 32, then
the last byte is a discriminator value for the compressed pubkey.

### Constructors

| [&lt;init&gt;](-init-.md) | `DumpedPrivateKey(private: `[`ECKeyPair`](../-e-c-key-pair/index.md)`)` |

### Properties

| [bytes](bytes.md) | `val bytes: `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html) |
| [compressed](compressed.md) | `val compressed: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |

### Functions

| [toString](to-string.md) | `fun toString(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Companion Object Functions

| [fromBase58](from-base58.md) | `fun fromBase58(encoded: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`DumpedPrivateKey`](./index.md) |
| [toBase58](to-base58.md) | `fun toBase58(private: `[`ECKeyPair`](../-e-c-key-pair/index.md)`): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Extension Functions

| [ecKey](../ec-key.md) | `fun `[`DumpedPrivateKey`](./index.md)`.ecKey(): `[`ECKeyPair`](../-e-c-key-pair/index.md) |

