[library](../../index.md) / [ch.decent.sdk.model.operation](../index.md) / [AddOrUpdateContentOperation](index.md) / [coAuthors](./co-authors.md)

# coAuthors

`@SerializedName("co_authors") var coAuthors: `[`CoAuthors`](../../ch.decent.sdk.model/-co-authors/index.md)

if map is not empty, payout will be split - the parameter maps co-authors
to basis points split, e.g. author1:9000 (bp), author2:1000 (bp),
if author is omitted from this map, it is assigned 10000 (bp_total) minus sum of splits

