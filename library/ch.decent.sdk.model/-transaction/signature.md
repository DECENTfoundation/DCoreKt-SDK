[library](../../index.md) / [ch.decent.sdk.model](../index.md) / [Transaction](index.md) / [signature](./signature.md)

# signature

`@JvmOverloads fun signature(key: `[`ECKeyPair`](../../ch.decent.sdk.crypto/-e-c-key-pair/index.md)`, chainId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = this.chainId ?: ""): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Generate signature on transaction data. May return empty if the signature is not valid for DCore.

### Parameters

`key` - private key

`chainId` - id of the DCore chain, different for live/testnet/custom net...