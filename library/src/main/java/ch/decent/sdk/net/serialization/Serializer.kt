@file:Suppress("TooManyFunctions", "MatchingDeclarationName", "MagicNumber")

/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License") you may not use this file except in compliance with
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
import ch.decent.sdk.model.AccountOptions
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.AssetOptions
import ch.decent.sdk.model.AuthMap
import ch.decent.sdk.model.Authority
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.CustodyData
import ch.decent.sdk.model.ExchangeRate
import ch.decent.sdk.model.KeyPart
import ch.decent.sdk.model.Memo
import ch.decent.sdk.model.MonitoredAssetOptions
import ch.decent.sdk.model.PubKey
import ch.decent.sdk.model.Publishing
import ch.decent.sdk.model.RegionalPrice
import ch.decent.sdk.model.Transaction
import ch.decent.sdk.model.VoteId
import ch.decent.sdk.model.operation.AccountCreateOperation
import ch.decent.sdk.model.operation.AccountUpdateOperation
import ch.decent.sdk.model.operation.AddOrUpdateContentOperation
import ch.decent.sdk.model.operation.AssetClaimFeesOperation
import ch.decent.sdk.model.operation.AssetCreateOperation
import ch.decent.sdk.model.operation.AssetFundPoolsOperation
import ch.decent.sdk.model.operation.AssetIssueOperation
import ch.decent.sdk.model.operation.AssetReserveOperation
import ch.decent.sdk.model.operation.AssetUpdateAdvancedOperation
import ch.decent.sdk.model.operation.AssetUpdateOperation
import ch.decent.sdk.model.operation.CustomOperation
import ch.decent.sdk.model.operation.LeaveRatingAndCommentOperation
import ch.decent.sdk.model.operation.PurchaseContentOperation
import ch.decent.sdk.model.operation.RemoveContentOperation
import ch.decent.sdk.model.operation.SendMessageOperation
import ch.decent.sdk.model.operation.TransferOperation
import ch.decent.sdk.utils.SIZE_OF_POINT_ON_CURVE_COMPRESSED
import ch.decent.sdk.utils.unhex
import okio.Buffer
import okio.BufferedSink
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import java.io.ByteArrayOutputStream
import java.io.DataOutput
import java.io.DataOutputStream
import java.io.IOException
import java.math.BigInteger
import kotlin.reflect.KClass

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
internal object Varint {

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
internal fun BigInteger.bytes(numBytes: Int): ByteArray {
  require(signum() >= 0) { "b must be positive or zero" }
  require(numBytes > 0) { "numBytes must be positive" }
  val src = toByteArray()
  val dest = ByteArray(numBytes)
  val isFirstByteOnlyForSign = src[0].toInt() == 0
  val length = if (isFirstByteOnlyForSign) src.size - 1 else src.size
  check(length <= numBytes) { "The given number does not fit in $numBytes" }
  val srcPos = if (isFirstByteOnlyForSign) 1 else 0
  val destPos = numBytes - length
  System.arraycopy(src, srcPos, dest, destPos, length)
  return dest
}

typealias Adapter<T> = (BufferedSink, obj: T) -> Unit

object Serializer {
  private val chainObjectAdapter: Adapter<ChainObject> = { buffer, obj ->
    buffer.write(Varint.writeUnsignedVarLong(obj.instance.toLong()))
  }

  private val byteArrayAdapter: Adapter<ByteArray> = { buffer, obj ->
    buffer.write(Varint.writeUnsignedVarInt(obj.size))
    buffer.write(obj)
  }

  private val stringAdapter: Adapter<String> = { buffer, obj ->
    append(buffer, obj.toByteArray())
  }

  private val addressAdapter: Adapter<Address> = { buffer, obj ->
    buffer.write(obj.publicKey.getEncoded(true))
  }

  private val authorityAdapter: Adapter<Authority> = { buffer, obj ->
    buffer.writeIntLe(obj.weightThreshold.toInt())
    append(buffer, obj.accountAuths)
    append(buffer, obj.keyAuths)
  }

  private val authorityMapAdapter: Adapter<AuthMap> = { buffer, obj ->
    append(buffer, obj.value)
    buffer.writeShortLe(obj.weight.toInt())
  }

  private val assetAmountAdapter: Adapter<AssetAmount> = { buffer, obj ->
    buffer.writeLongLe(obj.amount)
    append(buffer, obj.assetId)
  }

  private val memoAdapter: Adapter<Memo> = { buffer, obj ->
    if (obj.from != null) append(buffer, obj.from) else buffer.write(ByteArray(SIZE_OF_POINT_ON_CURVE_COMPRESSED) { 0 })
    if (obj.to != null) append(buffer, obj.to) else buffer.write(ByteArray(SIZE_OF_POINT_ON_CURVE_COMPRESSED) { 0 })
    buffer.writeLongLe(obj.nonce.toLong())
    append(buffer, obj.message.unhex())
  }

  private val voteAdapter: Adapter<VoteId> = { buffer, obj -> buffer.writeIntLe(obj.id.shl(8) or obj.type) }

  private val booleanAdapter: Adapter<Boolean> = { buffer, obj -> buffer.writeByte(if (obj) 1 else 0) }

  private val optionsAdapter: Adapter<AccountOptions> = { buffer, obj ->
    append(buffer, obj.memoKey)
    append(buffer, obj.votingAccount)
    buffer.writeShortLe(obj.numMiner)
    append(buffer, obj.votes)
    append(buffer, obj.extensions)
    append(buffer, obj.allowSubscription)
    append(buffer, obj.pricePerSubscribe)
    buffer.writeIntLe(obj.subscriptionPeriod.toInt())
  }

  private val pubKeyAdapter: Adapter<PubKey> = { buffer, obj -> append(buffer, obj.keyString) }

  private val publishingAdapter: Adapter<Publishing> = { buffer, obj ->
    append(buffer, obj.isPublishingManager)
    append(buffer, obj.publishRightsReceived)
    append(buffer, obj.publishRightsForwarded)
  }

  private val localDateTimeAdapter: Adapter<LocalDateTime> = { buffer, obj -> buffer.writeIntLe(obj.toEpochSecond(ZoneOffset.UTC).toInt()) }

  private val transactionAdapter: Adapter<Transaction> = { buffer, obj ->
    buffer.writeShortLe(obj.refBlockNum)
    buffer.writeIntLe(obj.refBlockPrefix.toInt())
    append(buffer, obj.expiration)
    append(buffer, obj.operations)
    buffer.writeByte(0)
  }

  private val accountCreateOperationAdapter: Adapter<AccountCreateOperation> = { buffer, obj ->
    buffer.writeByte(obj.type.ordinal)
    append(buffer, obj.fee)
    append(buffer, obj.registrar)
    append(buffer, obj.name)
    append(buffer, obj.owner)
    append(buffer, obj.active)
    append(buffer, obj.options)
    buffer.writeByte(0)
  }

  private val accountUpdateOperationAdapter: Adapter<AccountUpdateOperation> = { buffer, obj ->
    buffer.writeByte(obj.type.ordinal)
    append(buffer, obj.fee)
    append(buffer, obj.accountId)
    append(buffer, obj.owner, true)
    append(buffer, obj.active, true)
    append(buffer, obj.options, true)
    buffer.writeByte(0)
  }

  private val purchaseContentOperationAdapter: Adapter<PurchaseContentOperation> = { buffer, obj ->
    buffer.writeByte(obj.type.ordinal)
    append(buffer, obj.fee)
    append(buffer, obj.uri)
    append(buffer, obj.consumer)
    append(buffer, obj.price)
    buffer.writeIntLe(obj.regionCode.toInt())
    append(buffer, obj.publicElGamal)
  }

  private val transferOperationAdapter: Adapter<TransferOperation> = { buffer, obj ->
    buffer.writeByte(obj.type.ordinal)
    append(buffer, obj.fee)
    append(buffer, obj.from)
    buffer.writeLongLe(obj.to.fullInstance)
    append(buffer, obj.amount)
    append(buffer, obj.memo, true)
    buffer.writeByte(0)
  }

  private val coAuthorsAdapter: Adapter<Map<ChainObject, Int>> = { buffer, obj ->
    buffer.write(Varint.writeUnsignedVarInt(obj.size))
    obj.forEach { (id, weight) ->
      append(buffer, id)
      buffer.writeIntLe(weight)
    }
  }

  private val regionalPriceAdapter: Adapter<RegionalPrice> = { buffer, obj ->
    buffer.writeIntLe(obj.region.toInt())
    append(buffer, obj.price)
  }

  private val keyPartAdapter: Adapter<KeyPart> = { buffer, obj ->
    append(buffer, obj.keyC1)
    append(buffer, obj.keyD1)
  }

  private val custodyDataAdapter: Adapter<CustodyData> = { buffer, obj ->
    buffer.writeIntLe(obj.n.toInt())
    buffer.write(obj.seed.unhex())
    buffer.write(obj.pubKey.unhex())
  }

  private val addOrUpdateContentOperationAdapter: Adapter<AddOrUpdateContentOperation> = { buffer, obj ->
    buffer.writeByte(obj.type.ordinal)
    append(buffer, obj.fee)
    buffer.writeLongLe(obj.size.toLong())
    append(buffer, obj.author)
    coAuthorsAdapter(buffer, obj.coAuthors)
    append(buffer, obj.uri)
    buffer.writeIntLe(obj.quorum)
    append(buffer, obj.price)
    buffer.write(obj.hash.unhex())
    append(buffer, obj.seeders)
    append(buffer, obj.keyParts)
    append(buffer, obj.expiration)
    append(buffer, obj.publishingFee)
    append(buffer, obj.synopsis)
    append(buffer, obj.custodyData, true)
  }

  private val removeContentOperationAdapter: Adapter<RemoveContentOperation> = { buffer, obj ->
    buffer.writeByte(obj.type.ordinal)
    append(buffer, obj.fee)
    append(buffer, obj.author)
    append(buffer, obj.uri)
  }

  private val customOperationAdapter: Adapter<CustomOperation> = { buffer, obj ->
    buffer.writeByte(obj.type.ordinal)
    append(buffer, obj.fee)
    append(buffer, obj.payer)
    append(buffer, obj.requiredAuths)
    buffer.writeShortLe(obj.id)
    append(buffer, obj.data.unhex())
  }

  private val rateAndCommentOperationAdapter: Adapter<LeaveRatingAndCommentOperation> = { buffer, obj ->
    buffer.writeByte(obj.type.ordinal)
    append(buffer, obj.fee)
    append(buffer, obj.uri)
    append(buffer, obj.consumer)
    append(buffer, obj.comment)
    buffer.writeLongLe(obj.rating.toLong())
  }

  private val exchangeRateAdapter: Adapter<ExchangeRate> = { buffer, obj ->
    append(buffer, obj.base)
    append(buffer, obj.quote)
  }

  private val assetOptionsAdapter: Adapter<AssetOptions> = { buffer, obj ->
    buffer.writeLongLe(obj.maxSupply)
    append(buffer, obj.exchangeRate)
    append(buffer, obj.exchangeable)
    append(buffer, obj.extensions != null) // size of extensions flat_set
    obj.extensions?.let {
      buffer.writeByte(it.get.first)
      append(buffer, it.isFixedMaxSupply)
    }
  }

  private val monitoredAssetOptionsAdapter: Adapter<MonitoredAssetOptions> = { buffer, obj ->
    append(buffer, obj.feeds)
    append(buffer, obj.currentFeed.coreExchangeRate)
    append(buffer, obj.currentFeedPublicationTime)
    buffer.writeIntLe(obj.feedLifetimeSec.toInt())
    buffer.writeByte(obj.minimumFeeds.toInt())
//    03a086010000000000001b0453444b4d041368656c6c6f20617069206d6f6e69746f7265640000000000000000000000000000000000000000000000000000010101000100000000000000000000000000000000000000480fb65c80510100010100
  }

  private val assetCreateAdapter: Adapter<AssetCreateOperation> = { buffer, obj ->
    buffer.writeByte(obj.type.ordinal)
    append(buffer, obj.fee)
    append(buffer, obj.issuer)
    append(buffer, obj.symbol)
    buffer.writeByte(obj.precision.toInt())
    append(buffer, obj.description)
    append(buffer, obj.options)
    append(buffer, obj.monitoredOptions, true)
    append(buffer, true)
    buffer.writeByte(0)
  }

  private val assetUpdateAdapter: Adapter<AssetUpdateOperation> = { buffer, obj ->
    buffer.writeByte(obj.type.ordinal)
    append(buffer, obj.fee)
    append(buffer, obj.issuer)
    append(buffer, obj.assetToUpdate)
    append(buffer, obj.newDescription)
    append(buffer, obj.newIssuer, true)
    buffer.writeLongLe(obj.maxSupply)
    append(buffer, obj.coreExchangeRate)
    append(buffer, obj.exchangeable)
    buffer.writeByte(0)
  }

  private val assetUpdateAdvAdapter: Adapter<AssetUpdateAdvancedOperation> = { buffer, obj ->
    buffer.writeByte(obj.type.ordinal)
    append(buffer, obj.fee)
    append(buffer, obj.issuer)
    append(buffer, obj.assetToUpdate)
    buffer.writeByte(obj.precision.toInt())
    append(buffer, obj.fixedMaxSupply)
    buffer.writeByte(0)
  }

  private val assetIssueAdapter: Adapter<AssetIssueOperation> = { buffer, obj ->
    buffer.writeByte(obj.type.ordinal)
    append(buffer, obj.fee)
    append(buffer, obj.issuer)
    append(buffer, obj.assetToIssue)
    append(buffer, obj.issueToAccount)
    append(buffer, obj.memo, true)
    buffer.writeByte(0)
  }

  private val assetFundAdapter: Adapter<AssetFundPoolsOperation> = { buffer, obj ->
    buffer.writeByte(obj.type.ordinal)
    append(buffer, obj.fee)
    append(buffer, obj.from)
    append(buffer, obj.uia)
    append(buffer, obj.dct)
    buffer.writeByte(0)
  }

  private val assetReserveAdapter: Adapter<AssetReserveOperation> = { buffer, obj ->
    buffer.writeByte(obj.type.ordinal)
    append(buffer, obj.fee)
    append(buffer, obj.payer)
    append(buffer, obj.amount)
    buffer.writeByte(0)
  }

  private val assetClaimAdapter: Adapter<AssetClaimFeesOperation> = { buffer, obj ->
    buffer.writeByte(obj.type.ordinal)
    append(buffer, obj.fee)
    append(buffer, obj.issuer)
    append(buffer, obj.uia)
    append(buffer, obj.dct)
    buffer.writeByte(0)
  }


  @Suppress("UNCHECKED_CAST")
  private fun <T : Any> append(buffer: BufferedSink, obj: T?, optional: Boolean = false) {
    if (optional && obj == null) {
      buffer.writeByte(0)
      return
    } else if (optional) {
      buffer.writeByte(1)
    }
    requireNotNull(obj)

    if (obj is Collection<*>) {
      buffer.write(if (obj.isEmpty()) byteArrayOf(0) else Varint.writeUnsignedVarLong(obj.size.toLong()))
      obj.forEach { append(buffer, it) }
    } else {
      val adapter = adapters[obj::class] as Adapter<T>?
      requireNotNull(adapter) { "missing adapter for ${obj::class}" }
      adapter(buffer, obj)
    }
  }

  private val adapters: Map<KClass<*>, Adapter<*>> = mapOf(
      ChainObject::class to chainObjectAdapter,
      ByteArray::class to byteArrayAdapter,
      String::class to stringAdapter,
      Address::class to addressAdapter,
      Authority::class to authorityAdapter,
      AuthMap::class to authorityMapAdapter,
      AssetAmount::class to assetAmountAdapter,
      Memo::class to memoAdapter,
      VoteId::class to voteAdapter,
      Boolean::class to booleanAdapter,
      AccountOptions::class to optionsAdapter,
      PubKey::class to pubKeyAdapter,
      Publishing::class to publishingAdapter,
      LocalDateTime::class to localDateTimeAdapter,
      Transaction::class to transactionAdapter,
      AccountCreateOperation::class to accountCreateOperationAdapter,
      AccountUpdateOperation::class to accountUpdateOperationAdapter,
      PurchaseContentOperation::class to purchaseContentOperationAdapter,
      TransferOperation::class to transferOperationAdapter,
      RegionalPrice::class to regionalPriceAdapter,
      KeyPart::class to keyPartAdapter,
      CustodyData::class to custodyDataAdapter,
      AddOrUpdateContentOperation::class to addOrUpdateContentOperationAdapter,
      RemoveContentOperation::class to removeContentOperationAdapter,
      SendMessageOperation::class to customOperationAdapter,
      LeaveRatingAndCommentOperation::class to rateAndCommentOperationAdapter,
      ExchangeRate::class to exchangeRateAdapter,
      AssetOptions::class to assetOptionsAdapter,
      MonitoredAssetOptions::class to monitoredAssetOptionsAdapter,
      AssetCreateOperation::class to assetCreateAdapter,
      AssetUpdateOperation::class to assetUpdateAdapter,
      AssetUpdateAdvancedOperation::class to assetUpdateAdvAdapter,
      AssetIssueOperation::class to assetIssueAdapter,
      AssetFundPoolsOperation::class to assetFundAdapter,
      AssetReserveOperation::class to assetReserveAdapter,
      AssetClaimFeesOperation::class to assetClaimAdapter
  )

  fun serialize(obj: Any): ByteArray = Buffer().apply { append(this, obj) }.readByteArray()
}
