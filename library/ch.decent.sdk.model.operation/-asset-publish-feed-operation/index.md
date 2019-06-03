[library](../../index.md) / [ch.decent.sdk.model.operation](../index.md) / [AssetPublishFeedOperation](./index.md)

# AssetPublishFeedOperation

`class AssetPublishFeedOperation : `[`BaseOperation`](../-base-operation/index.md)

skip, cannot create monitored asset, also only miner account can publish feeds
asset_create_op has account_id_type fee_payer()const { return monitored_asset_opts.valid() ? account_id_type() : issuer; }
therefore throws Missing Active Authority 1.2.0

### Constructors

| [&lt;init&gt;](-init-.md) | `AssetPublishFeedOperation(publisher: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, asset: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, feed: `[`PriceFeed`](../../ch.decent.sdk.model/-price-feed/index.md)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee())`<br>skip, cannot create monitored asset, also only miner account can publish feeds asset_create_op has account_id_type fee_payer()const { return monitored_asset_opts.valid() ? account_id_type() : issuer; } therefore throws Missing Active Authority 1.2.0 |

### Properties

| [asset](asset.md) | `val asset: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md) |
| [feed](feed.md) | `val feed: `[`PriceFeed`](../../ch.decent.sdk.model/-price-feed/index.md) |
| [publisher](publisher.md) | `val publisher: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md) |

### Inherited Properties

| [fee](../-base-operation/fee.md) | `var fee: `[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)<br>Fee for the operation, must be set to valid value when broadcasting to network |
| [fetchFee](../-base-operation/fetch-fee.md) | `var fetchFee: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Defines whether to fetch required fee for the operation when using the API methods to create transaction or broadcast operation |
| [type](../-base-operation/type.md) | `var type: `[`OperationType`](../-operation-type/index.md) |

### Functions

| [toString](to-string.md) | `fun toString(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Inherited Functions

| [equals](../-base-operation/equals.md) | `open fun equals(other: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`?): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hashCode](../-base-operation/hash-code.md) | `open fun hashCode(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

