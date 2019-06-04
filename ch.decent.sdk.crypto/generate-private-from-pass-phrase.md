[library](../index.md) / [ch.decent.sdk.crypto](index.md) / [generatePrivateFromPassPhrase](./generate-private-from-pass-phrase.md)

# generatePrivateFromPassPhrase

`@JvmOverloads fun generatePrivateFromPassPhrase(phrase: `[`Passphrase`](-passphrase/index.md)`, sequence: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 0, normalized: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = true): `[`ECKeyPair`](-e-c-key-pair/index.md)

Method generates private key from pass phrase provided by parameter of type [Passphrase](-passphrase/index.md). If parameter [normalize](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.io/java.io.-file/normalize.html) is true, provided pass phrase will be
converted to upper case before private key calculation. In other case word stays as it was provided. Default value for [normalize](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.io/java.io.-file/normalize.html) parameter is true.

### Parameters

`phrase` - pass phrase

`sequence` - private key derivation sequence

`normalized` - normalization flag