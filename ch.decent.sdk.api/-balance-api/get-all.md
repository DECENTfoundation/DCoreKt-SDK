[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [BalanceApi](index.md) / [getAll](./get-all.md)

# getAll

`@JvmOverloads fun getAll(accountId: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, assets: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`> = emptyList()): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)`>>`

Get account balance by id.

### Parameters

`accountId` - object id of the account, 1.2.*

`assets` - object ids of the assets, 1.3.*

**Return**
list of amounts for different assets

`@JvmOverloads fun getAll(name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, assets: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`> = emptyList()): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)`>>`

Get account balance by name.

### Parameters

`name` - account name

`assets` - object ids of the assets, 1.3.*

**Return**
list of amounts for different assets

