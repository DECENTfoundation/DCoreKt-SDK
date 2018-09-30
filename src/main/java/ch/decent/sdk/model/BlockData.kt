package ch.decent.sdk.model

import ch.decent.sdk.net.serialization.ByteSerializable
import com.google.common.primitives.Bytes
import org.threeten.bp.ZoneOffset

/**
 * This class encapsulates all block-related information needed in order to build a valid transaction.
 * Block data constructor
 * @param refBlockNum: Least significant 16 bits from the reference block number.
 * If "relative_expiration" is zero, this field must be zero as well.
 * @param refBlockPrefix: The first non-block-number 32-bits of the reference block ID.
 * Recall that block IDs have 32 bits of block number followed by the
 * actual block hash, so this field should be set using the second 32 bits
 * in the block_id_type
 * @param expiration: Expiration time specified as a POSIX or
 * [Unix time](https://en.wikipedia.org/wiki/Unix_time)
 */
data class BlockData(
    val refBlockNum: Int,
    val refBlockPrefix: Long,
    val expiration: Long
) : ByteSerializable {

  /**
   * Block data constructor that takes in raw blockchain information.
   * @param headBlockNumber: The last block number.
   * @param headBlockId: The last block apiId.
   * @param relativeExpiration: The expiration time.
   */
  constructor(headBlockNumber: Long, headBlockId: String, relativeExpiration: Long) : this(
      headBlockNumber.toInt() and 0xFFFF,
      headBlockId.substring(8, 16).chunked(2).reversed().joinToString("").toLong(16),
      relativeExpiration
  )

  constructor(props: DynamicGlobalProps, expiration: Int) : this(props.headBlockNumber, props.headBlockId, props.time.toEpochSecond(ZoneOffset.UTC) + expiration)

  override val bytes: ByteArray
    get() =
    // Allocating a fixed length byte array, since we will always need
    // 2 bytes for the ref_block_num value
    // 4 bytes for the ref_block_prefix value
    // 4 bytes for the relative_expiration
      Bytes.concat(
          ByteArray(2) { idx -> (refBlockNum shr 8 * idx).toByte() },
          ByteArray(4) { idx -> (refBlockPrefix shr 8 * idx).toByte() },
          ByteArray(4) { idx -> (expiration shr 8 * idx).toByte() })

  fun increment() = this.copy(expiration = expiration + 1)
}