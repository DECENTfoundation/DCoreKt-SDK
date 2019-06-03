[library](../../../index.md) / [ch.decent.sdk.crypto](../../index.md) / [ECKeyPair](../index.md) / [ECDSASignature](index.md) / [toCanonicalised](./to-canonicalised.md)

# toCanonicalised

`fun toCanonicalised(): `[`ECDSASignature`](index.md)

Will automatically adjust the S component to be less than or equal to half the curve order, if necessary.
This is required because for every signature (r,s) the signature (r, -s (mod N)) is a valid signature of
the same message. However, we dislike the ability to modify the bits of a Bitcoin transaction after it's
been signed, as that violates various assumed invariants. Thus in future only one of those forms will be
considered legal and the other will be banned.

