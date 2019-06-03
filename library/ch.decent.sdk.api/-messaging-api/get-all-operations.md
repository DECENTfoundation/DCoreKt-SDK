[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [MessagingApi](index.md) / [getAllOperations](./get-all-operations.md)

# getAllOperations

`fun getAllOperations(sender: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`? = null, receiver: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`? = null, maxCount: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 1000): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`MessageResponse`](../../ch.decent.sdk.model/-message-response/index.md)`>>`

Get all message operations

### Parameters

`sender` - filter by sender account id

`receiver` - filter by receiver account id

`maxCount` - max items to return

**Return**
list of message operation responses

