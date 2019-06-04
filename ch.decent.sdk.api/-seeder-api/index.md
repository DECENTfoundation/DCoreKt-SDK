[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [SeederApi](./index.md)

# SeederApi

`class SeederApi : `[`BaseApi`](../-base-api/index.md)

### Inherited Properties

| [api](../-base-api/api.md) | `val api: `[`DCoreApi`](../../ch.decent.sdk/-d-core-api/index.md) |

### Functions

| [get](get.md) | `fun get(accountId: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`): Single<`[`Seeder`](../../ch.decent.sdk.model/-seeder/index.md)`>`<br>Get a seeder by ID. |
| [listByPrice](list-by-price.md) | `fun listByPrice(count: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 100): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Seeder`](../../ch.decent.sdk.model/-seeder/index.md)`>>`<br>Get a list of seeders by price, in increasing order. |
| [listByRating](list-by-rating.md) | `fun listByRating(count: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 100): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Seeder`](../../ch.decent.sdk.model/-seeder/index.md)`>>`<br>Get a list of seeders by price, in decreasing order. |
| [listByRegion](list-by-region.md) | `fun listByRegion(region: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = Regions.ALL.code): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Seeder`](../../ch.decent.sdk.model/-seeder/index.md)`>>`<br>Get a list of seeders ordered by price. |
| [listByUpload](list-by-upload.md) | `fun listByUpload(count: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 100): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Seeder`](../../ch.decent.sdk.model/-seeder/index.md)`>>`<br>Get a list of seeders ordered by total upload, in decreasing order. |

