[library](../../index.md) / [ch.decent.sdk.api](../index.md) / [ValidationApi](index.md) / [getPotentialSignatures](./get-potential-signatures.md)

# getPotentialSignatures

`fun getPotentialSignatures(transaction: `[`Transaction`](../../ch.decent.sdk.model/-transaction/index.md)`): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Address`](../../ch.decent.sdk.crypto/-address/index.md)`>>`

This method will return the set of all public keys that could possibly sign for a given transaction.
This call can be used by wallets to filter their set of public keys to just the relevant subset prior to calling get_required_signatures() to get the minimum subset.

### Parameters

`transaction` - unsigned transaction

**Return**
public keys that can sign transaction

