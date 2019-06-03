[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [PurchaseApi](index.md) / [findAll](./find-all.md)

# findAll

`@JvmOverloads fun findAll(consumer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, term: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = "", from: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)` = ObjectType.NULL_OBJECT.genericId, order: `[`SearchPurchasesOrder`](../../ch.decent.sdk.model/-search-purchases-order/index.md)` = SearchPurchasesOrder.PURCHASED_DESC, limit: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 100): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Purchase`](../../ch.decent.sdk.model/-purchase/index.md)`>>`

Search consumer open and history purchases.

### Parameters

`consumer` - object id of the account, 1.2.*

`term` - search term

`from` - object id of the history object to start from, use [ObjectType.NULL_OBJECT.genericId](../../ch.decent.sdk.model/-object-type/generic-id.md) to ignore

`order` - [SearchPurchasesOrder](../../ch.decent.sdk.model/-search-purchases-order/index.md)

`limit` - number of entries, max 100

**Return**
list of purchases

