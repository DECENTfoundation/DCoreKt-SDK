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

package ch.decent.sdk.utils


import java.io.ByteArrayOutputStream
import java.io.DataOutput
import java.io.DataOutputStream
import java.io.IOException


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