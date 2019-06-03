[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [AccountApi](index.md) / [get](./get.md)

# get

`fun get(id: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`): Single<`[`Account`](../../ch.decent.sdk.model/-account/index.md)`>`

Get account by id.

### Parameters

`id` - account id

**Return**
an account if exist, [ObjectNotFoundException](../../ch.decent.sdk.exception/-object-not-found-exception/index.md) if not found

`fun get(nameOrId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): Single<`[`Account`](../../ch.decent.sdk.model/-account/index.md)`>`

Get account by name or id.

### Parameters

`nameOrId` - account id or name

**Return**
an account if exist, [ObjectNotFoundException](../../ch.decent.sdk.exception/-object-not-found-exception/index.md) if not found, or [IllegalStateException](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-illegal-state-exception/index.html) if the account name or id is not valid

