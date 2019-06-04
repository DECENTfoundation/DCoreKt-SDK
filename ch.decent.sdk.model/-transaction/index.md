[library](../../index.md) / [ch.decent.sdk.model](../index.md) / [Transaction](./index.md)

# Transaction

`data class Transaction`

### Constructors

| [&lt;init&gt;](-init-.md) | `Transaction(operations: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`BaseOperation`](../../ch.decent.sdk.model.operation/-base-operation/index.md)`>, expiration: LocalDateTime, refBlockNum: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, refBlockPrefix: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, chainId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?, signatures: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>? = null, extensions: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> = emptyList())` |

### Properties

| [chainId](chain-id.md) | `val chainId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?` |
| [expiration](expiration.md) | `var expiration: LocalDateTime` |
| [extensions](extensions.md) | `val extensions: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>` |
| [id](id.md) | `val id: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [operations](operations.md) | `val operations: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`BaseOperation`](../../ch.decent.sdk.model.operation/-base-operation/index.md)`>` |
| [refBlockNum](ref-block-num.md) | `val refBlockNum: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [refBlockPrefix](ref-block-prefix.md) | `val refBlockPrefix: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [signatures](signatures.md) | `val signatures: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>?` |

### Functions

| [signature](signature.md) | `fun signature(key: `[`ECKeyPair`](../../ch.decent.sdk.crypto/-e-c-key-pair/index.md)`, chainId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = this.chainId ?: ""): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Generate signature on transaction data. May return empty if the signature is not valid for DCore. |
| [withSignature](with-signature.md) | `fun withSignature(key: `[`ECKeyPair`](../../ch.decent.sdk.crypto/-e-c-key-pair/index.md)`, chainId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = this.chainId ?: ""): `[`Transaction`](./index.md)<br>Set a single signature to transaction and return it. May change expiration time to meet valid signature checks for DCore. |

### Companion Object Functions

| [create](create.md) | `fun create(ops: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`BaseOperation`](../../ch.decent.sdk.model.operation/-base-operation/index.md)`>, chainId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, props: `[`DynamicGlobalProps`](../-dynamic-global-props/index.md)`, expiration: Duration): `[`Transaction`](./index.md) |

