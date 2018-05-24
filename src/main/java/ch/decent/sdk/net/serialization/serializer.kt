/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.decent.sdk.net.serialization


import ch.decent.sdk.crypto.Address
import ch.decent.sdk.utils.parseVoteId
import com.google.common.primitives.Bytes
import java.io.ByteArrayOutputStream
import java.io.DataOutput
import java.io.DataOutputStream
import java.io.IOException
import java.math.BigInteger
import java.nio.ByteBuffer


/**
 * <p>Encodes signed and unsigned values using a common variable-length
 * scheme, found for example in
 * <a href="http://code.google.com/apis/protocolbuffers/docs/encoding.html">
 * Google's Protocol Buffers</a>. It uses fewer bytes to encode smaller values,
 * but will use slightly more bytes to encode large values.</p>
 * <p/>
 * <p>Signed values are further encoded using so-called zig-zag encoding
 * in order to make them "compatible" with variable-length encoding.</p>
 */
object Varint {

  /**
   * Encodes a value using the variable-length encoding from
   * <a href="http://code.google.com/apis/protocolbuffers/docs/encoding.html">
   * Google Protocol Buffers</a>. It uses zig-zag encoding to efficiently
   * encode signed values. If values are known to be nonnegative,
   * {@link #writeUnsignedVarLong(long, DataOutput)} should be used.
   *
   * @param value value to encode
   * @param out   to write bytes to
   * @throws IOException if {@link DataOutput} throws {@link IOException}
   */
  @Throws(IOException::class)
  fun writeSignedVarLong(value: Long, out: DataOutput) {
    // Great trick from http://code.google.com/apis/protocolbuffers/docs/encoding.html#types
    writeUnsignedVarLong(value shl 1 xor (value shr 63), out)
  }

  /**
   * Encodes a value using the variable-length encoding from
   * <a href="http://code.google.com/apis/protocolbuffers/docs/encoding.html">
   * Google Protocol Buffers</a>. Zig-zag is not used, so input must not be negative.
   * If values can be negative, use {@link #writeSignedVarLong(long, DataOutput)}
   * instead. This method treats negative input as like a large unsigned value.
   *
   * @param value value to encode
   * @param out   to write bytes to
   * @throws IOException if {@link DataOutput} throws {@link IOException}
   */
  @Throws(IOException::class)
  fun writeUnsignedVarLong(value: Long, out: DataOutput) {
    var v = value
    while (v and -0x80L != 0L) {
      out.writeByte(v.toInt() and 0x7F or 0x80)
      v = v ushr 7
    }
    out.writeByte(v.toInt() and 0x7F)
  }

  fun writeUnsignedVarLong(value: Long): ByteArray =
      ByteArrayOutputStream().use { baos ->
        DataOutputStream(baos).use { dos ->
          writeUnsignedVarLong(value, dos)
          baos.toByteArray()
        }
      }

  /**
   * @see .writeSignedVarLong
   */
  @Throws(IOException::class)
  fun writeSignedVarInt(value: Int, out: DataOutput) {
    // Great trick from http://code.google.com/apis/protocolbuffers/docs/encoding.html#types
    writeUnsignedVarInt(value shl 1 xor (value shr 31), out)
  }

  /**
   * @see .writeUnsignedVarLong
   */
  @Throws(IOException::class)
  fun writeUnsignedVarInt(value: Int, out: DataOutput) {
    var v = value
    while ((v and -0x80).toLong() != 0L) {
      out.writeByte(v and 0x7F or 0x80)
      v = v ushr 7
    }
    out.writeByte(v and 0x7F)
  }

  fun writeSignedVarInt(value: Int): ByteArray {
    // Great trick from http://code.google.com/apis/protocolbuffers/docs/encoding.html#types
    return writeUnsignedVarInt(value shl 1 xor (value shr 31))
  }

  /**
   * @see #writeUnsignedVarLong(long, DataOutput)
   * <p/>
   * This one does not use streams and is much faster.
   * Makes a single object each time, and that object is a primitive array.
   */
  fun writeUnsignedVarInt(value: Int): ByteArray {
    var v = value
    val byteArrayList = ByteArray(10)
    var i = 0
    while ((v and -0x80).toLong() != 0L) {
      byteArrayList[i++] = (v and 0x7F or 0x80).toByte()
      v = v ushr 7
    }
    byteArrayList[i] = (v and 0x7F).toByte()
    val out = ByteArray(i + 1)
    while (i >= 0) {
      out[i] = byteArrayList[i]
      i--
    }
    return out
  }

}


// Serialization utils methods

/**
 * The regular [java.math.BigInteger.toByteArray] includes the sign bit of the number and
 * might result in an extra byte addition. This method removes this extra byte.
 *
 * Assuming only positive numbers, it's possible to discriminate if an extra byte
 * is added by checking if the first element of the array is 0 (0000_0000).
 * Due to the minimal representation provided by BigInteger, it means that the bit sign
 * is the least significant bit 0000_000**0** .
 * Otherwise the representation is not minimal.
 * For example, if the sign bit is 0000_00**0**0, then the representation is not minimal due to the rightmost zero.
 *
 * @param b the integer to format into a byte array
 * @param numBytes the desired size of the resulting byte array
 * @return numBytes byte long array.
 */
fun BigInteger.bytes(numBytes: Int): ByteArray {
  require(signum() >= 0, { "b must be positive or zero" })
  require(numBytes > 0, { "numBytes must be positive" })
  val src = toByteArray()
  val dest = ByteArray(numBytes)
  val isFirstByteOnlyForSign = src[0].toInt() == 0
  val length = if (isFirstByteOnlyForSign) src.size - 1 else src.size
  check(length <= numBytes, { "The given number does not fit in $numBytes" })
  val srcPos = if (isFirstByteOnlyForSign) 1 else 0
  val destPos = numBytes - length
  System.arraycopy(src, srcPos, dest, destPos, length)
  return dest
}

/**
 * Returns an array of bytes with the underlying data used to represent an integer in the reverse form.
 * This is useful for endianess switches, meaning that if you give this function a big-endian integer
 * it will return it's little-endian bytes.
 * @return The array of bytes that represent this value in the reverse format.
 */
fun Int.bytes(): ByteArray {
  return ByteBuffer.allocate(Integer.SIZE / 8).putInt(Integer.reverseBytes(this)).array()
}

/**
 * Same operation as in the Int.reverse function, but in this case for a short (2 bytes) value.
 * @return The array of bytes that represent this value in the reverse format.
 */
fun Short.bytes(): ByteArray {
  return ByteBuffer.allocate(java.lang.Short.SIZE / 8).putShort(java.lang.Short.reverseBytes(this)).array()
}

/**
 * Same operation as in the Int.reverse function, but in this case for a long (8 bytes) value.
 * @return The array of bytes that represent this value in the reverse format.
 */
fun Long.bytes(): ByteArray = ByteBuffer.allocate(java.lang.Long.SIZE / 8).putLong(java.lang.Long.reverseBytes(this)).array()

fun String.bytes() = toByteArray().bytes()

fun Boolean.bytes() = byteArrayOf(if (this) 1 else 0)

fun List<ByteSerializable>.bytes(): ByteArray = if (size == 0) byteArrayOf(0) else Bytes.concat(Varint.writeUnsignedVarInt(size), *map { it.bytes }.toTypedArray())

typealias VoteId = String

fun Set<VoteId>.bytes(): ByteArray = Bytes.concat(Varint.writeUnsignedVarInt(size), *map { it.parseVoteId() }.toTypedArray())

fun ByteArray.bytes(): ByteArray = Bytes.concat(Varint.writeUnsignedVarInt(size), this)

fun Address?.bytes() = this?.publicKey?.getEncoded(true) ?: ByteArray(33, { 0 })

fun ByteSerializable?.optionalBytes() = this?.let { byteArrayOf(1) + bytes } ?: byteArrayOf(0)