[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [BlockApi](index.md) / [get](./get.md)

# get

`fun get(blockNum: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): Single<`[`SignedBlock`](../../ch.decent.sdk.model/-signed-block/index.md)`>`

Retrieve a full, signed block.

### Parameters

`blockNum` - height of the block to be returned

**Return**
the referenced block, or [ObjectNotFoundException](../../ch.decent.sdk.exception/-object-not-found-exception/index.md) if no matching block was found

