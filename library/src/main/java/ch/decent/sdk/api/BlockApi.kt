package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.exception.ObjectNotFoundException
import ch.decent.sdk.model.BlockHeader
import ch.decent.sdk.net.model.request.GetBlock
import ch.decent.sdk.net.model.request.GetBlockHeader
import ch.decent.sdk.net.model.request.HeadBlockTime
import io.reactivex.Single
import org.threeten.bp.LocalDateTime

class BlockApi internal constructor(api: DCoreApi) : BaseApi(api) {

  /**
   * Retrieve a full, signed block.
   *
   * @param blockNum height of the block to be returned
   *
   * @return the referenced block, or [ObjectNotFoundException] if no matching block was found
   */
  fun get(blockNum: Long) = GetBlock(blockNum).toRequest()

  /**
   * Retrieve a block header.
   *
   * @param blockNum height of the block whose header should be returned
   *
   * @return header of the referenced block, or [ObjectNotFoundException] if no matching block was found
   */
  fun getHeader(blockNum: Long): Single<BlockHeader> = GetBlockHeader(blockNum).toRequest()

  /**
   * Query the last local block.
   *
   * @return the block time
   */
  fun getHeadTime(): Single<LocalDateTime> = HeadBlockTime.toRequest()
}