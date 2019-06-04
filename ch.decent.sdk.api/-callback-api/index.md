[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [CallbackApi](./index.md)

# CallbackApi

`class CallbackApi : `[`BaseApi`](../-base-api/index.md)

### Inherited Properties

| [api](../-base-api/api.md) | `val api: `[`DCoreApi`](../../ch.decent.sdk/-d-core-api/index.md) |

### Functions

| [cancelAll](cancel-all.md) | `fun cancelAll(): Single<`[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`>`<br>Stop receiving any notifications. This unsubscribes from all subscribed objects ([onGlobal](on-global.md) and [AccountApi.getFullAccounts](../-account-api/get-full-accounts.md)). |
| [onBlockApplied](on-block-applied.md) | `fun onBlockApplied(): Flowable<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>`<br>Receive new block notifications. Cannot be cancelled. |
| [onContentUpdate](on-content-update.md) | `fun onContentUpdate(uri: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): Flowable<`[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`>`<br>Receive notifications on content update. Cannot be cancelled. |
| [onGlobal](on-global.md) | `fun onGlobal(clearFilter: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`): Flowable<`[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`>`<br>Subscribe to callbacks. Can be cancelled. with [cancelAll](cancel-all.md). |
| [onPendingTransaction](on-pending-transaction.md) | `fun onPendingTransaction(): Flowable<`[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`>`<br>Receive notifications on pending transactions. Cannot be cancelled. |

