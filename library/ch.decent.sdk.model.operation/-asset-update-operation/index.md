[library](../../index.md) / [ch.decent.sdk.model.operation](../index.md) / [AssetUpdateOperation](./index.md)

# AssetUpdateOperation

`class AssetUpdateOperation : `[`BaseOperation`](../-base-operation/index.md)

Update asset operation constructor.

### Parameters

`issuer` - account id issuing the asset

`assetToUpdate` - asset to update

`coreExchangeRate` - new exchange rate

`newDescription` - new description

`exchangeable` - enable converting the asset to DCT, so it can be used to pay for fees

`maxSupply` - new max supply

`newIssuer` - a new issuer account id

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough

### Constructors

| [&lt;init&gt;](-init-.md) | `AssetUpdateOperation(issuer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, assetToUpdate: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, newDescription: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, newIssuer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`?, maxSupply: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, coreExchangeRate: `[`ExchangeRate`](../../ch.decent.sdk.model/-exchange-rate/index.md)`, exchangeable: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee())`<br>Update asset operation constructor. |

### Properties

| [assetToUpdate](asset-to-update.md) | `val assetToUpdate: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)<br>asset to update |
| [coreExchangeRate](core-exchange-rate.md) | `var coreExchangeRate: `[`ExchangeRate`](../../ch.decent.sdk.model/-exchange-rate/index.md)<br>new exchange rate |
| [exchangeable](exchangeable.md) | `var exchangeable: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>enable converting the asset to DCT, so it can be used to pay for fees |
| [issuer](issuer.md) | `val issuer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)<br>account id issuing the asset |
| [maxSupply](max-supply.md) | `var maxSupply: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)<br>new max supply |
| [newDescription](new-description.md) | `var newDescription: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>new description |
| [newIssuer](new-issuer.md) | `val newIssuer: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`?`<br>a new issuer account id |

### Inherited Properties

| [fee](../-base-operation/fee.md) | `var fee: `[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)<br>Fee for the operation, must be set to valid value when broadcasting to network |
| [fetchFee](../-base-operation/fetch-fee.md) | `var fetchFee: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Defines whether to fetch required fee for the operation when using the API methods to create transaction or broadcast operation |
| [type](../-base-operation/type.md) | `var type: `[`OperationType`](../-operation-type/index.md) |

### Functions

| [toString](to-string.md) | `fun toString(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Inherited Functions

| [equals](../-base-operation/equals.md) | `open fun equals(other: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`?): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hashCode](../-base-operation/hash-code.md) | `open fun hashCode(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

