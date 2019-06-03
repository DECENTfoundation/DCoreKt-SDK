[library](../../index.md) / [ch.decent.sdk.model.operation](../index.md) / [AccountCreateOperation](./index.md)

# AccountCreateOperation

`class AccountCreateOperation : `[`BaseOperation`](../-base-operation/index.md)

Request to create account operation constructor

### Parameters

`registrar` - registrar

`name` - account name

`owner` - owner authority

`active` - active authority

`options` - account options

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough

### Constructors

| [&lt;init&gt;](-init-.md) | `AccountCreateOperation(registrar: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, public: `[`Address`](../../ch.decent.sdk.crypto/-address/index.md)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee())``AccountCreateOperation(registrar: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, owner: `[`Authority`](../../ch.decent.sdk.model/-authority/index.md)`, active: `[`Authority`](../../ch.decent.sdk.model/-authority/index.md)`, options: `[`AccountOptions`](../../ch.decent.sdk.model/-account-options/index.md)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee())`<br>Request to create account operation constructor |

### Properties

| [active](active.md) | `val active: `[`Authority`](../../ch.decent.sdk.model/-authority/index.md)<br>active authority |
| [name](name.md) | `val name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>account name |
| [options](options.md) | `val options: `[`AccountOptions`](../../ch.decent.sdk.model/-account-options/index.md)<br>account options |
| [owner](owner.md) | `val owner: `[`Authority`](../../ch.decent.sdk.model/-authority/index.md)<br>owner authority |
| [registrar](registrar.md) | `val registrar: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)<br>registrar |

### Inherited Properties

| [fee](../-base-operation/fee.md) | `var fee: `[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)<br>Fee for the operation, must be set to valid value when broadcasting to network |
| [fetchFee](../-base-operation/fetch-fee.md) | `var fetchFee: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Defines whether to fetch required fee for the operation when using the API methods to create transaction or broadcast operation |
| [type](../-base-operation/type.md) | `var type: `[`OperationType`](../-operation-type/index.md) |

### Functions

| [toString](to-string.md) | `fun toString(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Inherited Functions

| [equals](../-base-operation/equals.md) | `open fun equals(other: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`?): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hashCode](../-base-operation/hash-code.md) | `open fun hashCode(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

