[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [ValidationApi](index.md) / [getRequiredSignatures](./get-required-signatures.md)

# getRequiredSignatures

`fun getRequiredSignatures(transaction: `[`Transaction`](../../ch.decent.sdk.model/-transaction/index.md)`, keys: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Address`](../../ch.decent.sdk.crypto/-address/index.md)`>): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Address`](../../ch.decent.sdk.crypto/-address/index.md)`>>`

This API will take a partially signed transaction and a set of public keys that the owner has the ability to sign for
and return the minimal subset of public keys that should add signatures to the transaction.

### Parameters

`transaction` - partially signed transaction

`keys` - available owner public keys

**Return**
public keys that should add signatures

