[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [AccountApi](index.md) / [getByName](./get-by-name.md)

# getByName

`fun getByName(name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): Single<`[`Account`](../../ch.decent.sdk.model/-account/index.md)`>`

Get account object by name.

### Parameters

`name` - the name of the account

**Return**
an account if found, [ObjectNotFoundException](../../ch.decent.sdk.exception/-object-not-found-exception/index.md) otherwise

