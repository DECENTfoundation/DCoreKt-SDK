[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [ContentApi](index.md) / [findAll](./find-all.md)

# findAll

`fun findAll(term: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, order: `[`SearchContentOrder`](../../ch.decent.sdk.model/-search-content-order/index.md)` = SearchContentOrder.CREATED_DESC, user: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = "", regionCode: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = Regions.ALL.code, type: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = contentType(ApplicationType.DECENT_CORE, CategoryType.NONE), startId: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)` = ObjectType.NULL_OBJECT.genericId, limit: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 100): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Content`](../../ch.decent.sdk.model/-content/index.md)`>>`

Search for term in contents (author, title and description).

### Parameters

`term` - search term

`order` - ordering field. Available options are defined in [SearchContentOrder](../../ch.decent.sdk.model/-search-content-order/index.md)

`user` - content owner account name

`regionCode` - two letter region code, defined in [Regions](../../ch.decent.sdk.model/-regions/index.md)

`type` - the application and content type to be filtered, use [contentType](../../ch.decent.sdk.model/content-type.md) method

`startId` - the id of content object to start searching from

`limit` - maximum number of contents to fetch (must not exceed 100)

**Return**
the contents found

