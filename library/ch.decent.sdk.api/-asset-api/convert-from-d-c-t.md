[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [AssetApi](index.md) / [convertFromDCT](./convert-from-d-c-t.md)

# convertFromDCT

`@JvmOverloads fun convertFromDCT(assetId: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, amount: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, roundingMode: `[`RoundingMode`](http://docs.oracle.com/javase/6/docs/api/java/math/RoundingMode.html)` = RoundingMode.CEILING): Single<`[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)`>`

Get asset by id and convert amount in DCT to this asset

### Parameters

`assetId` - asset id to get

`amount` - amount to convert

`roundingMode` - rounding mode to use when rounding to target asset precision