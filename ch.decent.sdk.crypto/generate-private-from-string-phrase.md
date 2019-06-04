[library](../index.md) / [ch.decent.sdk.crypto](index.md) / [generatePrivateFromStringPhrase](./generate-private-from-string-phrase.md)

# generatePrivateFromStringPhrase

`@JvmOverloads fun generatePrivateFromStringPhrase(phrase: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, sequence: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 0, normalized: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = true): `[`ECKeyPair`](-e-c-key-pair/index.md)

Method generates private key from phrase provided by parameter of type [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html). If parameter [normalize](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.io/java.io.-file/normalize.html) is true, provided pass phrase will be converted
to upper case before private key calculation. In other case word stays as it was provided. Default value for [normalize](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.io/java.io.-file/normalize.html) parameter is true.

### Parameters

`phrase` - phrase string

`sequence` - private key derivation sequence

`normalized` - normalization flag