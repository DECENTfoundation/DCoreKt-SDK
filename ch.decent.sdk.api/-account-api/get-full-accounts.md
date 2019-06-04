[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [AccountApi](index.md) / [getFullAccounts](./get-full-accounts.md)

# getFullAccounts

`@JvmOverloads fun getFullAccounts(namesOrIds: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>, subscribe: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = false): Single<`[`Map`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, `[`FullAccount`](../../ch.decent.sdk.model/-full-account/index.md)`>>`

Fetch all objects relevant to the specified accounts and subscribe to updates.

### Parameters

`namesOrIds` - list of account names or ids

`subscribe` - true to subscribe to updates

**Return**
map of names or ids to account, or empty map if not present

