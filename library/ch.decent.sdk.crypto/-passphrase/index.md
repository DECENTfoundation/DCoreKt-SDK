[library](../../index.md) / [ch.decent.sdk.crypto](../index.md) / [Passphrase](./index.md)

# Passphrase

`class Passphrase`

### Constructors

| [&lt;init&gt;](-init-.md) | `Passphrase(words: `[`Array`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>)` |

### Properties

| [size](size.md) | `val size: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [words](words.md) | `val words: `[`Array`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |

### Functions

| [toString](to-string.md) | `fun toString(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Companion Object Properties

| [WORD_COUNT](-w-o-r-d_-c-o-u-n-t.md) | `const val WORD_COUNT: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

### Companion Object Functions

| [generate](generate.md) | `fun generate(seed: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`> = SeedDictionary.DEFAULT, count: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = WORD_COUNT, normalize: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = true): `[`Passphrase`](./index.md)<br>If parameter [normalize](generate.md#ch.decent.sdk.crypto.Passphrase.Companion$generate(kotlin.collections.List((kotlin.String)), kotlin.Int, kotlin.Boolean)/normalize) is true, all provided word will be converted to upper case before entropy creation. In other case word stays as it was provided. |

