[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [ContentApi](index.md) / [update](./update.md)

# update

`@JvmOverloads fun update(credentials: `[`Credentials`](../../ch.decent.sdk.crypto/-credentials/index.md)`, content: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, synopsis: `[`Synopsis`](../../ch.decent.sdk.model/-synopsis/index.md)`? = null, price: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`RegionalPrice`](../../ch.decent.sdk.model/-regional-price/index.md)`>? = null, coAuthors: `[`CoAuthors`](../../ch.decent.sdk.model/-co-authors/index.md)`? = null, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee()): Single<`[`TransactionConfirmation`](../../ch.decent.sdk.model/-transaction-confirmation/index.md)`>`

Update content.

### Parameters

`credentials` - author credentials

`content` - content id

`synopsis` - JSON formatted structure containing content information

`price` - list of regional prices

`coAuthors` - if map is not empty, payout will be split - the parameter maps co-authors
to basis points split, e.g. author1:9000 (bp), author2:1000 (bp),
if author is omitted from this map, it is assigned 10000 (bp_total) minus sum of splits

`fee` - {@link AssetAmount} fee for the operation or asset id, if left undefined the fee will be computed in DCT asset.
When set, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough`@JvmOverloads fun update(credentials: `[`Credentials`](../../ch.decent.sdk.crypto/-credentials/index.md)`, content: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, synopsis: `[`Synopsis`](../../ch.decent.sdk.model/-synopsis/index.md)`? = null, price: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`RegionalPrice`](../../ch.decent.sdk.model/-regional-price/index.md)`>? = null, coAuthors: `[`CoAuthors`](../../ch.decent.sdk.model/-co-authors/index.md)`? = null, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee()): Single<`[`TransactionConfirmation`](../../ch.decent.sdk.model/-transaction-confirmation/index.md)`>`

Update content. Update parameters are functions that have current values as arguments.

### Parameters

`credentials` - author credentials

`content` - content uri

`synopsis` - JSON formatted structure containing content information

`price` - list of regional prices

`coAuthors` - if map is not empty, payout will be split - the parameter maps co-authors
to basis points split, e.g. author1:9000 (bp), author2:1000 (bp),
if author is omitted from this map, it is assigned 10000 (bp_total) minus sum of splits

`fee` - {@link AssetAmount} fee for the operation or asset id, if left undefined the fee will be computed in DCT asset.
When set, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough