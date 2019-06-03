[library](../../index.md) / [ch.decent.sdk.model.operation](../index.md) / [AssetUpdateMonitoredOperation](./index.md)

# AssetUpdateMonitoredOperation

`class AssetUpdateMonitoredOperation : `[`BaseOperation`](../-base-operation/index.md)

skip, cannot create
asset_create_op has account_id_type fee_payer()const { return monitored_asset_opts.valid() ? account_id_type() : issuer; }
therefore throws Missing Active Authority 1.2.0

### Constructors

| [&lt;init&gt;](-init-.md) | `AssetUpdateMonitoredOperation(issuer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, assetToUpdate: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, description: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, newFeedLifetime: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, newMinimumFeeds: `[`Short`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-short/index.html)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee())`<br>skip, cannot create asset_create_op has account_id_type fee_payer()const { return monitored_asset_opts.valid() ? account_id_type() : issuer; } therefore throws Missing Active Authority 1.2.0 |

### Properties

| [assetToUpdate](asset-to-update.md) | `val assetToUpdate: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md) |
| [description](description.md) | `val description: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [issuer](issuer.md) | `val issuer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md) |
| [newFeedLifetime](new-feed-lifetime.md) | `val newFeedLifetime: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [newMinimumFeeds](new-minimum-feeds.md) | `val newMinimumFeeds: `[`Short`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-short/index.html) |

### Inherited Properties

| [fee](../-base-operation/fee.md) | `var fee: `[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)<br>Fee for the operation, must be set to valid value when broadcasting to network |
| [fetchFee](../-base-operation/fetch-fee.md) | `var fetchFee: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Defines whether to fetch required fee for the operation when using the API methods to create transaction or broadcast operation |
| [type](../-base-operation/type.md) | `var type: `[`OperationType`](../-operation-type/index.md) |

### Functions

| [toString](to-string.md) | `fun toString(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Inherited Functions

| [equals](../-base-operation/equals.md) | `open fun equals(other: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`?): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hashCode](../-base-operation/hash-code.md) | `open fun hashCode(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

