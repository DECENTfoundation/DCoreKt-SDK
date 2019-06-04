[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [AccountApi](index.md) / [findAllReferencesByAccount](./find-all-references-by-account.md)

# findAllReferencesByAccount

`fun findAllReferencesByAccount(accountId: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`>>`

Get all accounts that refer to the account id in their owner or active authorities.

### Parameters

`accountId` - account object id

**Return**
a list of account object ids

