[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [AccountApi](index.md) / [findAll](./find-all.md)

# findAll

`@JvmOverloads fun findAll(searchTerm: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, order: `[`SearchAccountsOrder`](../../ch.decent.sdk.model/-search-accounts-order/index.md)` = SearchAccountsOrder.NAME_DESC, id: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)` = ObjectType.NULL_OBJECT.genericId, limit: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 1000): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Account`](../../ch.decent.sdk.model/-account/index.md)`>>`

Get names and IDs for registered accounts that match search term.

### Parameters

`searchTerm` - will try to partially match account name or id

`order` - sort data by field

`id` - object id to start searching from

`limit` - number of items to get, max 1000

**Return**
list of found accounts

