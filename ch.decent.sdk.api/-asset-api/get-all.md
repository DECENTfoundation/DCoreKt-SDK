[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [AssetApi](index.md) / [getAll](./get-all.md)

# getAll

`fun getAll(assetIds: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`>): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Asset`](../../ch.decent.sdk.model/-asset/index.md)`>>`

Get assets by id.

### Parameters

`assetIds` - asset id eg. DCT id is 1.3.0

**Return**
list of assets or [ObjectNotFoundException](../../ch.decent.sdk.exception/-object-not-found-exception/index.md)

