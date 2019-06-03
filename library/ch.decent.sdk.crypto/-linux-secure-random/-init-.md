[library](../../index.md) / [ch.decent.sdk.crypto](../index.md) / [LinuxSecureRandom](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`LinuxSecureRandom()`

Implementation from
[BitcoinJ implementation](https://github.com/bitcoinj/bitcoinj/blob/master/core/src/main/java/org/bitcoinj/crypto/LinuxSecureRandom.java)

A SecureRandom implementation that is able to override the standard JVM provided
implementation, and which simply serves random numbers by reading /dev/urandom. That is, it
delegates to the kernel on UNIX systems and is unusable on other platforms. Attempts to manually
set the seed are ignored. There is no difference between seed bytes and non-seed bytes, they are
all from the same source.

