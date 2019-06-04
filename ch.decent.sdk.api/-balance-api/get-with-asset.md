[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [BalanceApi](index.md) / [getWithAsset](./get-with-asset.md)

# getWithAsset

`fun getWithAsset(accountId: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, assetSymbol: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = DCoreConstants.DCT_SYMBOL): Single<`[`AmountWithAsset`](../../ch.decent.sdk.model/-amount-with-asset/index.md)`>`

Get account balance by name.

### Parameters

`accountId` - id of the account

`assetSymbol` - asset symbol, eg. DCT

**Return**
a pair of asset to amount

`fun getWithAsset(name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, assetSymbol: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = DCoreConstants.DCT_SYMBOL): Single<`[`AmountWithAsset`](../../ch.decent.sdk.model/-amount-with-asset/index.md)`>`

Get account balance by name.

### Parameters

`name` - account name

`assetSymbol` - asset symbol, eg. DCT

**Return**
a pair of asset to amount

