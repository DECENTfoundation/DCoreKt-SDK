[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [AssetApi](index.md) / [getAssetsData](./get-assets-data.md)

# getAssetsData

`fun getAssetsData(assetId: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`>): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`AssetData`](../../ch.decent.sdk.model/-asset-data/index.md)`>>`

Get asset dynamic data by id.

### Parameters

`assetId` - asset data id eg. DCT id is 2.3.0

**Return**
asset dynamic data or [ObjectNotFoundException](../../ch.decent.sdk.exception/-object-not-found-exception/index.md)

