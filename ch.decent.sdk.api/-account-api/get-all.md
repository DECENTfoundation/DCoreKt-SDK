[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [AccountApi](index.md) / [getAll](./get-all.md)

# getAll

`fun getAll(accountIds: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`>): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Account`](../../ch.decent.sdk.model/-account/index.md)`>>`

Get account objects by ids.

### Parameters

`accountIds` - object ids of the account, 1.2.*

**Return**
an account list if found, [ObjectNotFoundException](../../ch.decent.sdk.exception/-object-not-found-exception/index.md) otherwise

