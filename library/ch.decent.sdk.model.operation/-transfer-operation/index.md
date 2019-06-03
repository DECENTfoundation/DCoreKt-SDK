[library](../../index.md) / [ch.decent.sdk.model.operation](../index.md) / [TransferOperation](./index.md)

# TransferOperation

`class TransferOperation : `[`BaseOperation`](../-base-operation/index.md)

Transfer operation constructor

### Parameters

`from` - account object id of the sender

`to` - account object id or content object id of the receiver

`amount` - an [AssetAmount](../../ch.decent.sdk.model/-asset-amount/index.md) to transfer

`memo` - optional string note

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough

### Constructors

| [&lt;init&gt;](-init-.md) | `TransferOperation(from: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, to: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, amount: `[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)`, memo: `[`Memo`](../../ch.decent.sdk.model/-memo/index.md)`? = null, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee())`<br>Transfer operation constructor |

### Properties

| [amount](amount.md) | `val amount: `[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)<br>an [AssetAmount](../../ch.decent.sdk.model/-asset-amount/index.md) to transfer |
| [from](from.md) | `val from: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)<br>account object id of the sender |
| [memo](memo.md) | `val memo: `[`Memo`](../../ch.decent.sdk.model/-memo/index.md)`?`<br>optional string note |
| [to](to.md) | `val to: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)<br>account object id or content object id of the receiver |

### Inherited Properties

| [fee](../-base-operation/fee.md) | `var fee: `[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)<br>Fee for the operation, must be set to valid value when broadcasting to network |
| [fetchFee](../-base-operation/fetch-fee.md) | `var fetchFee: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Defines whether to fetch required fee for the operation when using the API methods to create transaction or broadcast operation |
| [type](../-base-operation/type.md) | `var type: `[`OperationType`](../-operation-type/index.md) |

### Functions

| [toString](to-string.md) | `fun toString(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Inherited Functions

| [equals](../-base-operation/equals.md) | `open fun equals(other: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`?): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hashCode](../-base-operation/hash-code.md) | `open fun hashCode(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

