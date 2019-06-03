[library](../../index.md) / [ch.decent.sdk.model.operation](../index.md) / [AddOrUpdateContentOperation](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`AddOrUpdateContentOperation(size: `[`BigInteger`](http://docs.oracle.com/javase/6/docs/api/java/math/BigInteger.html)` = BigInteger.ONE, author: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, coAuthors: `[`CoAuthors`](../../ch.decent.sdk.model/-co-authors/index.md)` = CoAuthors(emptyMap()), uri: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, quorum: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 0, price: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`RegionalPrice`](../../ch.decent.sdk.model/-regional-price/index.md)`>, hash: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = uri.toByteArray().ripemd160().hex(), seeders: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`> = emptyList(), keyParts: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`KeyPart`](../../ch.decent.sdk.model/-key-part/index.md)`> = emptyList(), expiration: LocalDateTime, publishingFee: `[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)` = AssetAmount(0), synopsis: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, custodyData: `[`CustodyData`](../../ch.decent.sdk.model/-custody-data/index.md)`? = null, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee())`

Request to submit content operation constructor

### Parameters

`size` - size of content, including samples, in megabytes

`author` - author of the content. If co-authors is not filled, this account will receive full payout

`coAuthors` - if map is not empty, payout will be split - the parameter maps co-authors
to basis points split, e.g. author1:9000 (bp), author2:1000 (bp),
if author is omitted from this map, it is assigned 10000 (bp_total) minus sum of splits

`uri` - URI where the content can be found

`quorum` - how many seeders needs to cooperate to recover the key

`price` - list of regional prices

`hash` - hash of the content. Should be 40 chars long, hex encoded

`seeders` - list of selected seeders

`keyParts` - key particles, each assigned to one of the seeders, encrypted with his key

`expiration` - content expiration time

`publishingFee` - fee must be greater than the sum of seeders' publishing prices * number of days. Is paid by author

`synopsis` - JSON formatted structure containing content information

`custodyData` - if cd.n == 0 then no custody is submitted, and simplified verification is done.

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough