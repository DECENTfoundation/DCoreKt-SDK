[library](../../index.md) / [ch.decent.sdk.model](../index.md) / [ExchangeRate](./index.md)

# ExchangeRate

`data class ExchangeRate`

### Constructors

| [&lt;init&gt;](-init-.md) | `ExchangeRate(base: `[`AssetAmount`](../-asset-amount/index.md)`, quote: `[`AssetAmount`](../-asset-amount/index.md)`)` |

### Properties

| [base](base.md) | `val base: `[`AssetAmount`](../-asset-amount/index.md) |
| [quote](quote.md) | `val quote: `[`AssetAmount`](../-asset-amount/index.md) |

### Companion Object Properties

| [EMPTY](-e-m-p-t-y.md) | `val EMPTY: `[`ExchangeRate`](./index.md) |

### Companion Object Functions

| [forCreateOp](for-create-op.md) | `fun forCreateOp(base: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, quote: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`ExchangeRate`](./index.md)<br>quote &amp; base asset ids cannot be the same, for quote any id can be used since it is modified to created asset id upon creation |

