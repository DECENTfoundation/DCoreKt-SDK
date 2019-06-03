[library](../../index.md) / [ch.decent.sdk.crypto](../index.md) / [ECKeyPair](index.md) / [recoverFromSignature](./recover-from-signature.md)

# recoverFromSignature

`@JvmStatic fun recoverFromSignature(recId: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, sig: `[`ECDSASignature`](-e-c-d-s-a-signature/index.md)`, message: `[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)`): `[`ECKeyPair`](index.md)`?`

Given the components of a signature and a selector value, recover and return the public keyPair
that generated the signature according to the algorithm in SEC1v2 section 4.1.6.

The recId is an index from 0 to 3 which indicates which of the 4 possible keys is the correct one. Because
the keyPair recovery operation yields multiple potential keys, the correct keyPair must either be stored alongside the
signature, or you must be willing to try each recId in turn until you find one that outputs the keyPair you are
expecting.

If this method returns null it means recovery was not possible and recId should be iterated.

Given the above two points, a correct usage of this method is inside a for loop from 0 to 3, and if the
output is null OR a keyPair that is not the one you expect, you try again with the next recId.

### Parameters

`recId` - Which possible keyPair to recover.

`sig` - the R and S components of the signature, wrapped.

`message` - Hash of the data that was signed.

**Return**
An ECKeyPair containing only the public part, or null if recovery wasn't possible.

