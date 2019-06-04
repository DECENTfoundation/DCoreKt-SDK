[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [ValidationApi](index.md) / [getFeesForType](./get-fees-for-type.md)

# getFeesForType

`@JvmOverloads fun getFeesForType(types: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`OperationType`](../../ch.decent.sdk.model.operation/-operation-type/index.md)`>, assetId: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)` = DCoreConstants.DCT.id): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`AssetAmount`](../../ch.decent.sdk.model/-asset-amount/index.md)`>>`

Returns fees for operation type, not valid for operation per size fees:
[OperationType.ASSET_CREATE_OPERATION](../../ch.decent.sdk.model.operation/-operation-type/-a-s-s-e-t_-c-r-e-a-t-e_-o-p-e-r-a-t-i-o-n.md),
[OperationType.ASSET_ISSUE_OPERATION](../../ch.decent.sdk.model.operation/-operation-type/-a-s-s-e-t_-i-s-s-u-e_-o-p-e-r-a-t-i-o-n.md),
[OperationType.PROPOSAL_CREATE_OPERATION](../../ch.decent.sdk.model.operation/-operation-type/-p-r-o-p-o-s-a-l_-c-r-e-a-t-e_-o-p-e-r-a-t-i-o-n.md),
[OperationType.PROPOSAL_UPDATE_OPERATION](../../ch.decent.sdk.model.operation/-operation-type/-p-r-o-p-o-s-a-l_-u-p-d-a-t-e_-o-p-e-r-a-t-i-o-n.md),
[OperationType.WITHDRAW_PERMISSION_CLAIM_OPERATION](../../ch.decent.sdk.model.operation/-operation-type/-w-i-t-h-d-r-a-w_-p-e-r-m-i-s-s-i-o-n_-c-l-a-i-m_-o-p-e-r-a-t-i-o-n.md),
[OperationType.CUSTOM_OPERATION](../../ch.decent.sdk.model.operation/-operation-type/-c-u-s-t-o-m_-o-p-e-r-a-t-i-o-n.md),
[OperationType.ASSERT_OPERATION](../../ch.decent.sdk.model.operation/-operation-type/-a-s-s-e-r-t_-o-p-e-r-a-t-i-o-n.md),
[OperationType.CONTENT_SUBMIT_OPERATION](../../ch.decent.sdk.model.operation/-operation-type/-c-o-n-t-e-n-t_-s-u-b-m-i-t_-o-p-e-r-a-t-i-o-n.md)

### Parameters

`types` - operation types

**Return**
a fee asset amount

