[library](../../index.md) / [ch.decent.sdk.model](../index.md) / [Transaction](index.md) / [withSignature](./with-signature.md)

# withSignature

`@JvmOverloads fun withSignature(key: `[`ECKeyPair`](../../ch.decent.sdk.crypto/-e-c-key-pair/index.md)`, chainId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = this.chainId ?: ""): `[`Transaction`](index.md)

Set a single signature to transaction and return it. May change expiration time to meet valid signature checks for DCore.

### Parameters

`key` - private key

`chainId` - id of the DCore chain, different for live/testnet/custom net...