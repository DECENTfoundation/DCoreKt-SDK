[library](../../index.md) / [ch.decent.sdk.model](../index.md) / [MessagePayload](./index.md)

# MessagePayload

`data class MessagePayload`

### Constructors

| [&lt;init&gt;](-init-.md) | `MessagePayload(from: `[`ChainObject`](../-chain-object/index.md)`, messages: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Pair`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)`<`[`ChainObject`](../-chain-object/index.md)`, `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>>)`<br>unencrypted message payload constructor`MessagePayload(from: `[`ChainObject`](../-chain-object/index.md)`, receiversData: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`MessagePayloadReceiver`](../-message-payload-receiver/index.md)`>, fromAddress: `[`Address`](../../ch.decent.sdk.crypto/-address/index.md)`? = null)` |

### Properties

| [from](from.md) | `val from: `[`ChainObject`](../-chain-object/index.md) |
| [fromAddress](from-address.md) | `val fromAddress: `[`Address`](../../ch.decent.sdk.crypto/-address/index.md)`?` |
| [receiversData](receivers-data.md) | `val receiversData: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`MessagePayloadReceiver`](../-message-payload-receiver/index.md)`>` |

