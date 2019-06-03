[library](../../index.md) / [ch.decent.sdk.crypto](../index.md) / [Passphrase](index.md) / [generate](./generate.md)

# generate

`@JvmOverloads fun generate(seed: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`> = SeedDictionary.DEFAULT, count: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = WORD_COUNT, normalize: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = true): `[`Passphrase`](index.md)

If parameter [normalize](generate.md#ch.decent.sdk.crypto.Passphrase.Companion$generate(kotlin.collections.List((kotlin.String)), kotlin.Int, kotlin.Boolean)/normalize) is true, all provided word will be converted to upper case before entropy creation. In other case word stays
as it was provided.

### Parameters

`seed` - words list

`count` - word count, default 16

`normalize` - normalization flag, default true