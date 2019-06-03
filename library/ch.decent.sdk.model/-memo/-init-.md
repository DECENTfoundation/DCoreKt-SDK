[library](../../index.md) / [ch.decent.sdk.model](../index.md) / [Memo](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`Memo(message: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`)`

Create Memo object with unencrypted message

### Parameters

`message` - a message to send`Memo(message: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, credentials: `[`Credentials`](../../ch.decent.sdk.crypto/-credentials/index.md)`, recipient: `[`Account`](../-account/index.md)`)`

Create Memo object with encrypted message

### Parameters

`message` - a message to send

`credentials` - sender credentials

`recipient` - receiver account`Memo(message: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, keyPair: `[`ECKeyPair`](../../ch.decent.sdk.crypto/-e-c-key-pair/index.md)`, recipient: `[`Address`](../../ch.decent.sdk.crypto/-address/index.md)`, nonce: `[`BigInteger`](http://docs.oracle.com/javase/6/docs/api/java/math/BigInteger.html)` = generateNonce())`

Create Memo object with encrypted message

### Parameters

`message` - a message to send

`keyPair` - sender keys, use [ch.decent.sdk.crypto.Credentials.keyPair](../../ch.decent.sdk.crypto/-credentials/key-pair.md)

`recipient` - receiver public key, use address from [Account.active](../-account/active.md) keys

`nonce` - unique positive number