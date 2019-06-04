[library](../../index.md) / [ch.decent.sdk.model](../index.md) / [Asset](index.md) / [convertFromDCT](./convert-from-d-c-t.md)

# convertFromDCT

`fun convertFromDCT(amount: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, roundingMode: `[`RoundingMode`](http://docs.oracle.com/javase/6/docs/api/java/math/RoundingMode.html)`): `[`AssetAmount`](../-asset-amount/index.md)

Converts DCT [amount](convert-from-d-c-t.md#ch.decent.sdk.model.Asset$convertFromDCT(kotlin.Long, java.math.RoundingMode)/amount) according conversion rate.
Throws an [IllegalArgumentException](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-illegal-argument-exception/index.html) if the quote or base [amount](convert-from-d-c-t.md#ch.decent.sdk.model.Asset$convertFromDCT(kotlin.Long, java.math.RoundingMode)/amount) is not greater then zero.

