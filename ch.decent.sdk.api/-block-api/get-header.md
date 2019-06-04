[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [BlockApi](index.md) / [getHeader](./get-header.md)

# getHeader

`fun getHeader(blockNum: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): Single<`[`BlockHeader`](../../ch.decent.sdk.model/-block-header/index.md)`>`

Retrieve a block header.

### Parameters

`blockNum` - height of the block whose header should be returned

**Return**
header of the referenced block, or [ObjectNotFoundException](../../ch.decent.sdk.exception/-object-not-found-exception/index.md) if no matching block was found

