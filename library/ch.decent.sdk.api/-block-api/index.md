[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [BlockApi](./index.md)

# BlockApi

`class BlockApi : `[`BaseApi`](../-base-api/index.md)

### Inherited Properties

| [api](../-base-api/api.md) | `val api: `[`DCoreApi`](../../ch.decent.sdk/-d-core-api/index.md) |

### Functions

| [get](get.md) | `fun get(blockNum: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): Single<`[`SignedBlock`](../../ch.decent.sdk.model/-signed-block/index.md)`>`<br>Retrieve a full, signed block. |
| [getHeadTime](get-head-time.md) | `fun getHeadTime(): Single<LocalDateTime>`<br>Query the last local block. |
| [getHeader](get-header.md) | `fun getHeader(blockNum: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): Single<`[`BlockHeader`](../../ch.decent.sdk.model/-block-header/index.md)`>`<br>Retrieve a block header. |

