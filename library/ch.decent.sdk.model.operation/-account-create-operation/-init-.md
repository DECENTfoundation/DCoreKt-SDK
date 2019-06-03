[library](../../index.md) / [ch.decent.sdk.model.operation](../index.md) / [AccountCreateOperation](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`AccountCreateOperation(registrar: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, public: `[`Address`](../../ch.decent.sdk.crypto/-address/index.md)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee())``AccountCreateOperation(registrar: `[`ChainObject`](../../ch.decent.sdk.model/-chain-object/index.md)`, name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, owner: `[`Authority`](../../ch.decent.sdk.model/-authority/index.md)`, active: `[`Authority`](../../ch.decent.sdk.model/-authority/index.md)`, options: `[`AccountOptions`](../../ch.decent.sdk.model/-account-options/index.md)`, fee: `[`Fee`](../../ch.decent.sdk.model/-fee/index.md)` = Fee())`

Request to create account operation constructor

### Parameters

`registrar` - registrar

`name` - account name

`owner` - owner authority

`active` - active authority

`options` - account options

`fee` - [Fee](../../ch.decent.sdk.model/-fee/index.md) fee for the operation, by default the fee will be computed in DCT asset.
When set to other then DCT, the request might fail if the asset is not convertible to DCT or conversion pool is not large enough