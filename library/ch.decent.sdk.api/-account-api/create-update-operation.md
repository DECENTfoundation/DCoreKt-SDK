[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [AccountApi](index.md) / [createUpdateOperation](./create-update-operation.md)

# createUpdateOperation

`@JvmOverloads fun createUpdateOperation(nameOrId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee()): Single<`[`AccountUpdateOperation`](../../ch.decent.sdk.model.operation/-account-update-operation/index.md)`>`

Create update account operation. Fills model with actual account values.

### Parameters

`nameOrId` - account id or name

`fee` - {@link AssetAmount} fee for the operation or asset id, if left undefined the fee will be computed in DCT asset.
When set, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough

**Return**
modifiable account update operation

