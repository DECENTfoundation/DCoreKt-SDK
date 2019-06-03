[library](../../index.md) / [ch.decent.sdk.model.operation](../index.md) / [AssetFundPoolsOperation](./index.md)

# AssetFundPoolsOperation

`class AssetFundPoolsOperation : `[`BaseOperation`](../-base-operation/index.md)

Fund asset pool operation constructor. Any account can fund a pool.

### Parameters

`from` - account id funding the pool

`uia` - the uia asset value to fund

`dct` - the dct asset value to fund

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough

### Constructors

| [&lt;init&gt;](-init-.md) | `AssetFundPoolsOperation(from: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, uia: `[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)`, dct: `[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee())`<br>Fund asset pool operation constructor. Any account can fund a pool. |

### Properties

| [dct](dct.md) | `val dct: `[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)<br>the dct asset value to fund |
| [from](from.md) | `val from: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)<br>account id funding the pool |
| [uia](uia.md) | `val uia: `[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)<br>the uia asset value to fund |

### Inherited Properties

| [fee](../-base-operation/fee.md) | `var fee: `[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)<br>Fee for the operation, must be set to valid value when broadcasting to network |
| [fetchFee](../-base-operation/fetch-fee.md) | `var fetchFee: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Defines whether to fetch required fee for the operation when using the API methods to create transaction or broadcast operation |
| [type](../-base-operation/type.md) | `var type: `[`OperationType`](../-operation-type/index.md) |

### Functions

| [toString](to-string.md) | `fun toString(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Inherited Functions

| [equals](../-base-operation/equals.md) | `open fun equals(other: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`?): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hashCode](../-base-operation/hash-code.md) | `open fun hashCode(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

