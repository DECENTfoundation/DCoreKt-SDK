[library](../../index.md) / [ch.decent.sdk.model.operation](../index.md) / [AssetUpdateAdvancedOperation](./index.md)

# AssetUpdateAdvancedOperation

`class AssetUpdateAdvancedOperation : `[`BaseOperation`](../-base-operation/index.md)

Update advanced options for the asset.

### Parameters

`issuer` - account id issuing the asset

`assetToUpdate` - asset to update

`precision` - new precision

`fixedMaxSupply` - whether it should be allowed to change max supply, cannot be reverted once set to true

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough

### Constructors

| [&lt;init&gt;](-init-.md) | `AssetUpdateAdvancedOperation(issuer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, assetToUpdate: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, precision: `[`Byte`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte/index.html)`, fixedMaxSupply: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee())`<br>Update advanced options for the asset. |

### Properties

| [assetToUpdate](asset-to-update.md) | `val assetToUpdate: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)<br>asset to update |
| [fixedMaxSupply](fixed-max-supply.md) | `var fixedMaxSupply: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>whether it should be allowed to change max supply, cannot be reverted once set to true |
| [issuer](issuer.md) | `val issuer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)<br>account id issuing the asset |
| [precision](precision.md) | `var precision: `[`Byte`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte/index.html)<br>new precision |

### Inherited Properties

| [fee](../-base-operation/fee.md) | `var fee: `[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)<br>Fee for the operation, must be set to valid value when broadcasting to network |
| [fetchFee](../-base-operation/fetch-fee.md) | `var fetchFee: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Defines whether to fetch required fee for the operation when using the API methods to create transaction or broadcast operation |
| [type](../-base-operation/type.md) | `var type: `[`OperationType`](../-operation-type/index.md) |

### Functions

| [toString](to-string.md) | `fun toString(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Inherited Functions

| [equals](../-base-operation/equals.md) | `open fun equals(other: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`?): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hashCode](../-base-operation/hash-code.md) | `open fun hashCode(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

