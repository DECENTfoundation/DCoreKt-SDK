[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [AccountApi](index.md) / [getAllByNames](./get-all-by-names.md)

# getAllByNames

`fun getAllByNames(names: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Account`](../../ch.decent.sdk.model/-account/index.md)`>>`

Get a list of accounts by name.

### Parameters

`names` - account names to retrieve

**Return**
list of accounts or [ObjectNotFoundException](../../ch.decent.sdk.exception/-object-not-found-exception/index.md) if none exist

