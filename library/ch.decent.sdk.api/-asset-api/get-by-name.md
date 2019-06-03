[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [AssetApi](index.md) / [getByName](./get-by-name.md)

# getByName

`fun getByName(assetSymbol: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): Single<`[`Asset`](../../ch.decent.sdk.model/-asset/index.md)`>`

Lookup asset by symbol.

### Parameters

`assetSymbol` - asset symbol eg. DCT

**Return**
asset or [ObjectNotFoundException](../../ch.decent.sdk.exception/-object-not-found-exception/index.md)

