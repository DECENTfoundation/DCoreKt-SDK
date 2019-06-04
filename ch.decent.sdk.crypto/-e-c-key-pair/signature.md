[library](../../index.md) / [ch.decent.sdk.crypto](../index.md) / [ECKeyPair](index.md) / [signature](./signature.md)

# signature

`fun signature(data: `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)`): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

should be called in a loop until a 'canonical' signature is returned, slightly changing input data on every call

