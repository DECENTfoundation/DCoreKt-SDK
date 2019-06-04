[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [AccountApi](index.md) / [createAccountOperation](./create-account-operation.md)

# createAccountOperation

`@JvmOverloads fun createAccountOperation(registrar: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, address: `[`Address`](../../ch.decent.sdk.crypto/-address/index.md)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee()): Single<`[`AccountCreateOperation`](../../ch.decent.sdk.model.operation/-account-create-operation/index.md)`>`

Create a register new account operation.

### Parameters

`registrar` - account id used to register the new account

`name` - new account name

`address` - new account public key address

`fee` - {@link AssetAmount} fee for the operation or asset id, if left undefined the fee will be computed in DCT asset.
When set, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough

**Return**
a transaction confirmation

