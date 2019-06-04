[library](../../index.md) / [ch.decent.sdk.crypto](../index.md) / [Address](./index.md)

# Address

`data class Address`

Class used to encapsulate address-related operations.

### Constructors

| [&lt;init&gt;](-init-.md) | `Address(publicKey: ECPoint, prefix: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = PREFIX)`<br>Class used to encapsulate address-related operations. |

### Properties

| [publicKey](public-key.md) | `val publicKey: ECPoint` |

### Functions

| [encode](encode.md) | `fun encode(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>the public key is always in a compressed format in DCore |
| [toString](to-string.md) | `fun toString(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Companion Object Properties

| [PREFIX](-p-r-e-f-i-x.md) | `const val PREFIX: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Companion Object Functions

| [decode](decode.md) | `fun decode(address: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Address`](./index.md) |
| [decodeCheckNull](decode-check-null.md) | `fun decodeCheckNull(address: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Address`](./index.md)`?` |
| [isValid](is-valid.md) | `fun isValid(address: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |

### Extension Functions

| [bytes](../bytes.md) | `fun `[`Address`](./index.md)`.bytes(): `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html) |

