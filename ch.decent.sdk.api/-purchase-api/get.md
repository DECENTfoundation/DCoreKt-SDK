[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [PurchaseApi](index.md) / [get](./get.md)

# get

`fun get(consumer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, uri: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): Single<`[`Purchase`](../../ch.decent.sdk.model/-purchase/index.md)`>`

Get consumer purchase by content uri.

### Parameters

`consumer` - object id of the account, 1.2.*

`uri` - a uri of the content

**Return**
an account if found, [ch.decent.sdk.exception.ObjectNotFoundException](../../ch.decent.sdk.exception/-object-not-found-exception/index.md) otherwise

