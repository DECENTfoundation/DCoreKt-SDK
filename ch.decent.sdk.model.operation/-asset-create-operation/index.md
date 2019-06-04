[library](../../index.md) / [ch.decent.sdk.model.operation](../index.md) / [AssetCreateOperation](./index.md)

# AssetCreateOperation

`class AssetCreateOperation : `[`BaseOperation`](../-base-operation/index.md)

Create Asset operation constructor.

### Parameters

`issuer` - account id issuing the asset

`symbol` - the string symbol, 3-16 uppercase chars

`precision` - base unit precision, decimal places used in string representation

`description` - optional description

`options` - asset options

`monitoredOptions` - options for monitored asset

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough

### Constructors

| [&lt;init&gt;](-init-.md) | `AssetCreateOperation(issuer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, symbol: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, precision: `[`Byte`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte/index.html)`, description: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, options: `[`AssetOptions`](../../ch.decent.sdk.model/-asset-options/index.md)`, monitoredOptions: `[`MonitoredAssetOptions`](../../ch.decent.sdk.model/-monitored-asset-options/index.md)`? = null, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee())`<br>Create Asset operation constructor. |

### Properties

| [description](description.md) | `val description: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>optional description |
| [issuer](issuer.md) | `val issuer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)<br>account id issuing the asset |
| [monitoredOptions](monitored-options.md) | `val monitoredOptions: `[`MonitoredAssetOptions`](../../ch.decent.sdk.model/-monitored-asset-options/index.md)`?`<br>options for monitored asset |
| [options](options.md) | `val options: `[`AssetOptions`](../../ch.decent.sdk.model/-asset-options/index.md)<br>asset options |
| [precision](precision.md) | `val precision: `[`Byte`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte/index.html)<br>base unit precision, decimal places used in string representation |
| [symbol](symbol.md) | `val symbol: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>the string symbol, 3-16 uppercase chars |

### Inherited Properties

| [fee](../-base-operation/fee.md) | `var fee: `[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)<br>Fee for the operation, must be set to valid value when broadcasting to network |
| [fetchFee](../-base-operation/fetch-fee.md) | `var fetchFee: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Defines whether to fetch required fee for the operation when using the API methods to create transaction or broadcast operation |
| [type](../-base-operation/type.md) | `var type: `[`OperationType`](../-operation-type/index.md) |

### Functions

| [toString](to-string.md) | `fun toString(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Inherited Functions

| [equals](../-base-operation/equals.md) | `open fun equals(other: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`?): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hashCode](../-base-operation/hash-code.md) | `open fun hashCode(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

