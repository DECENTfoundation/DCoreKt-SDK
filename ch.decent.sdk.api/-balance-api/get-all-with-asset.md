[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [BalanceApi](index.md) / [getAllWithAsset](./get-all-with-asset.md)

# getAllWithAsset

`fun getAllWithAsset(accountId: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, assetSymbols: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`AmountWithAsset`](../../ch.decent.sdk.model/-amount-with-asset/index.md)`>>`

Get account balance by id with asset.

### Parameters

`accountId` - id of the account

`assetSymbols` - asset symbols, eg. DCT

**Return**
a list of pairs of assets to amounts

`fun getAllWithAsset(name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, assetSymbols: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`AmountWithAsset`](../../ch.decent.sdk.model/-amount-with-asset/index.md)`>>`

Get account balance by name.

### Parameters

`name` - account name

`assetSymbols` - asset symbols, eg. DCT

**Return**
list of assets with amounts

