[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [AccountApi](index.md) / [searchAccountHistory](./search-account-history.md)

# searchAccountHistory

`@JvmOverloads fun ~~searchAccountHistory~~(accountId: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, from: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)` = ObjectType.NULL_OBJECT.genericId, order: `[`SearchAccountHistoryOrder`](../../ch.decent.sdk.model/-search-account-history-order/index.md)` = SearchAccountHistoryOrder.TIME_DESC, limit: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 100): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`TransactionDetail`](../../ch.decent.sdk.model/-transaction-detail/index.md)`>>`
**Deprecated:** Use history API

Search account history.

### Parameters

`accountId` - object id of the account, 1.2.*

`from` - object id of the history object to start from, use [ObjectType.NULL_OBJECT.genericId](../../ch.decent.sdk.model/-object-type/generic-id.md) to ignore

`order` - order of items

`limit` - number of entries, max 100

**Return**
account history list

