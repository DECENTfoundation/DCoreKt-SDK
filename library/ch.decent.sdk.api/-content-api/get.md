[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [ContentApi](index.md) / [get](./get.md)

# get

`fun get(contentId: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`): Single<`[`Content`](../../ch.decent.sdk.model/-content/index.md)`>`

Get content by id.

### Parameters

`contentId` - object id of the content, 2.13.*

**Return**
a content if found, [ch.decent.sdk.exception.ObjectNotFoundException](../../ch.decent.sdk.exception/-object-not-found-exception/index.md) otherwise

`fun get(uri: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): Single<`[`Content`](../../ch.decent.sdk.model/-content/index.md)`>`

Get content by uri.

### Parameters

`uri` - Uri of the content

**Return**
a content if found, [ch.decent.sdk.exception.ObjectNotFoundException](../../ch.decent.sdk.exception/-object-not-found-exception/index.md) otherwise

