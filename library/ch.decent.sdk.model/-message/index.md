[library](../../index.md) / [ch.decent.sdk.model](../index.md) / [Message](./index.md)

# Message

`data class Message`

### Constructors

| [&lt;init&gt;](-init-.md) | `Message(operationId: `[`ChainObject`](../-chain-object/index.md)`, timestamp: LocalDateTime, sender: `[`ChainObject`](../-chain-object/index.md)`, senderAddress: `[`Address`](../../ch.decent.sdk.crypto/-address/index.md)`?, receiver: `[`ChainObject`](../-chain-object/index.md)`, receiverAddress: `[`Address`](../../ch.decent.sdk.crypto/-address/index.md)`?, message: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, nonce: `[`BigInteger`](http://docs.oracle.com/javase/6/docs/api/java/math/BigInteger.html)` = BigInteger.ZERO, encrypted: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = senderAddress != null && receiverAddress != null)` |

### Properties

| [encrypted](encrypted.md) | `val encrypted: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [message](message.md) | `val message: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [nonce](nonce.md) | `val nonce: `[`BigInteger`](http://docs.oracle.com/javase/6/docs/api/java/math/BigInteger.html) |
| [operationId](operation-id.md) | `val operationId: `[`ChainObject`](../-chain-object/index.md) |
| [receiver](receiver.md) | `val receiver: `[`ChainObject`](../-chain-object/index.md) |
| [receiverAddress](receiver-address.md) | `val receiverAddress: `[`Address`](../../ch.decent.sdk.crypto/-address/index.md)`?` |
| [sender](sender.md) | `val sender: `[`ChainObject`](../-chain-object/index.md) |
| [senderAddress](sender-address.md) | `val senderAddress: `[`Address`](../../ch.decent.sdk.crypto/-address/index.md)`?` |
| [timestamp](timestamp.md) | `val timestamp: LocalDateTime` |

### Functions

| [decrypt](decrypt.md) | `fun decrypt(credentials: `[`Credentials`](../../ch.decent.sdk.crypto/-credentials/index.md)`): `[`Message`](./index.md)<br>Decrypt the message with given credentials |

### Companion Object Functions

| [create](create.md) | `fun create(response: `[`MessageResponse`](../-message-response/index.md)`): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Message`](./index.md)`>` |

