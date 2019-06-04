[library](../../index.md) / [ch.decent.sdk.model](../index.md) / [Asset](index.md) / [convertToDCT](./convert-to-d-c-t.md)

# convertToDCT

`fun convertToDCT(amount: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, roundingMode: `[`RoundingMode`](http://docs.oracle.com/javase/6/docs/api/java/math/RoundingMode.html)`): `[`AssetAmount`](../-asset-amount/index.md)

Converts asset [amount](convert-to-d-c-t.md#ch.decent.sdk.model.Asset$convertToDCT(kotlin.Long, java.math.RoundingMode)/amount) to DCT according conversion rate.
Throws an [IllegalArgumentException](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-illegal-argument-exception/index.html) if the quote or base [amount](convert-to-d-c-t.md#ch.decent.sdk.model.Asset$convertToDCT(kotlin.Long, java.math.RoundingMode)/amount) is not greater then zero.

