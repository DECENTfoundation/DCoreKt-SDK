[library](../../index.md) / [ch.decent.sdk.model](../index.md) / [Memo](./index.md)

# Memo

`class Memo`

### Constructors

| [&lt;init&gt;](-init-.md) | `Memo(message: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`)`<br>Create Memo object with unencrypted message`Memo(message: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, credentials: `[`Credentials`](../../ch.decent.sdk.crypto/-credentials/index.md)`, recipient: `[`Account`](../-account/index.md)`)`<br>`Memo(message: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, keyPair: `[`ECKeyPair`](../../ch.decent.sdk.crypto/-e-c-key-pair/index.md)`, recipient: `[`Address`](../../ch.decent.sdk.crypto/-address/index.md)`, nonce: `[`BigInteger`](http://docs.oracle.com/javase/6/docs/api/java/math/BigInteger.html)` = generateNonce())`<br>Create Memo object with encrypted message |

### Properties

| [from](from.md) | `val from: `[`Address`](../../ch.decent.sdk.crypto/-address/index.md)`?` |
| [message](message.md) | `val message: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [nonce](nonce.md) | `val nonce: `[`BigInteger`](http://docs.oracle.com/javase/6/docs/api/java/math/BigInteger.html) |
| [to](to.md) | `val to: `[`Address`](../../ch.decent.sdk.crypto/-address/index.md)`?` |

### Functions

| [decrypt](decrypt.md) | `fun decrypt(keyPair: `[`ECKeyPair`](../../ch.decent.sdk.crypto/-e-c-key-pair/index.md)`): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [toString](to-string.md) | `fun toString(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

