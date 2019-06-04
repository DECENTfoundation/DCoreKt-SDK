[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [AssetApi](index.md) / [getAllByName](./get-all-by-name.md)

# getAllByName

`fun getAllByName(assetSymbols: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Asset`](../../ch.decent.sdk.model/-asset/index.md)`>>`

Lookup assets by symbol.

### Parameters

`assetSymbols` - asset symbols eg. DCT

**Return**
list of assets or [ObjectNotFoundException](../../ch.decent.sdk.exception/-object-not-found-exception/index.md)

