[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [ValidationApi](index.md) / [getFees](./get-fees.md)

# getFees

`@JvmOverloads fun getFees(op: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`BaseOperation`](../../ch.decent.sdk.model.operation/-base-operation/index.md)`>, assetId: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)` = DCoreConstants.DCT.id): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)`>>`

Returns fees for operation.

### Parameters

`op` - list of operations

`assetId` - asset id eg. DCT id is 1.3.0

**Return**
a list of fee asset amounts

