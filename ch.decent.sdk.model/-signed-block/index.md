[library](../../index.md) / [ch.decent.sdk.model](../index.md) / [SignedBlock](./index.md)

# SignedBlock

`data class SignedBlock`

### Constructors

| [&lt;init&gt;](-init-.md) | `SignedBlock(previous: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, timestamp: LocalDateTime, miner: `[`ChainObject`](../-chain-object/index.md)`, transactionMerkleRoot: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, minerSignature: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, transactions: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ProcessedTransaction`](../-processed-transaction/index.md)`>, extensions: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>)` |

### Properties

| [extensions](extensions.md) | `val extensions: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` |
| [miner](miner.md) | `val miner: `[`ChainObject`](../-chain-object/index.md) |
| [minerSignature](miner-signature.md) | `val minerSignature: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [previous](previous.md) | `val previous: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [timestamp](timestamp.md) | `val timestamp: LocalDateTime` |
| [transactionMerkleRoot](transaction-merkle-root.md) | `val transactionMerkleRoot: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [transactions](transactions.md) | `val transactions: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ProcessedTransaction`](../-processed-transaction/index.md)`>` |

