[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [BalanceApi](index.md) / [get](./get.md)

# get

`fun get(accountId: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, asset: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`): Single<`[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)`>`

Get account balance by id.

### Parameters

`accountId` - object id of the account, 1.2.*

`asset` - object id of the assets, 1.3.*

**Return**
amount for asset

`fun get(name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, asset: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`): Single<`[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)`>`

get account balance by name.

### Parameters

`name` - account name

`asset` - object id of the assets, 1.3.*

**Return**
amount for asset

