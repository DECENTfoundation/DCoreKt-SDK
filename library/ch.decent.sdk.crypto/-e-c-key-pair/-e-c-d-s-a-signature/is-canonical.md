[library](../../../index.md) / [ch.decent.sdk.crypto](../../index.md) / [ECKeyPair](../index.md) / [ECDSASignature](index.md) / [isCanonical](./is-canonical.md)

# isCanonical

`val isCanonical: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)

Returns true if the S component is "low", that means it is below [ECKeyPair.HALF_CURVE_ORDER](#).
See [BIP62](https://github.com/bitcoin/bips/blob/master/bip-0062.mediawiki#Low_S_values_in_signatures).

