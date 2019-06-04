[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [AssetApi](index.md) / [fund](./fund.md)

# fund

`@JvmOverloads fun fund(credentials: `[`Credentials`](../../ch.decent.sdk.crypto/-credentials/index.md)`, assetIdOrSymbol: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, uia: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, dct: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee()): Single<`[`TransactionConfirmation`](../../ch.decent.sdk.model/-transaction-confirmation/index.md)`>`

Fund asset pool. Any account can fund a pool.

### Parameters

`credentials` - account credentials funding the pool

`assetIdOrSymbol` - which asset to fund

`uia` - UIA raw amount

`dct` - DCT raw amount

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough