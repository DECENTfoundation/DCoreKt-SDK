[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [ContentApi](index.md) / [add](./add.md)

# add

`@JvmOverloads fun add(credentials: `[`Credentials`](../../ch.decent.sdk.crypto/-credentials/index.md)`, coAuthors: `[`CoAuthors`](../../ch.decent.sdk.model/-co-authors/index.md)`, uri: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, price: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`RegionalPrice`](../../ch.decent.sdk.model/-regional-price/index.md)`>, expiration: LocalDateTime, synopsis: `[`Synopsis`](../../ch.decent.sdk.model/-synopsis/index.md)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee()): Single<`[`TransactionConfirmation`](../../ch.decent.sdk.model/-transaction-confirmation/index.md)`>`

Add content.

### Parameters

`credentials` - author credentials. If co-authors is not filled, this account will receive full payout

`coAuthors` - if map is not empty, payout will be split - the parameter maps co-authors
to basis points split, e.g. author1:9000 (bp), author2:1000 (bp),
if author is omitted from this map, it is assigned 10000 (bp_total) minus sum of splits

`uri` - URI where the content can be found

`price` - list of regional prices

`expiration` - content expiration time

`synopsis` - JSON formatted structure containing content information

`fee` - {@link AssetAmount} fee for the operation or asset id, if left undefined the fee will be computed in DCT asset.
When set, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough