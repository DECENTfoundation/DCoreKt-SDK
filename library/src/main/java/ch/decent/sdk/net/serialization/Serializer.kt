@file:Suppress("TooManyFunctions", "MatchingDeclarationName")

package ch.decent.sdk.net.serialization

import ch.decent.sdk.crypto.Address
import ch.decent.sdk.model.AccountAuth
import ch.decent.sdk.model.AccountOptions
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.AssetOptions
import ch.decent.sdk.model.Authority
import ch.decent.sdk.model.CddVestingPolicy
import ch.decent.sdk.model.CddVestingPolicyCreate
import ch.decent.sdk.model.CoAuthors
import ch.decent.sdk.model.CustodyData
import ch.decent.sdk.model.ExchangeRate
import ch.decent.sdk.model.KeyAuth
import ch.decent.sdk.model.KeyPart
import ch.decent.sdk.model.LinearVestingPolicy
import ch.decent.sdk.model.LinearVestingPolicyCreate
import ch.decent.sdk.model.Memo
import ch.decent.sdk.model.MonitoredAssetOptions
import ch.decent.sdk.model.NftDataType
import ch.decent.sdk.model.NftOptions
import ch.decent.sdk.model.ObjectId
import ch.decent.sdk.model.PubKey
import ch.decent.sdk.model.Publishing
import ch.decent.sdk.model.RegionalPrice
import ch.decent.sdk.model.StaticVariantParametrized
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
import ch.decent.sdk.model.operation.BaseOperation
import ch.decent.sdk.model.operation.CustomOperation
import ch.decent.sdk.model.operation.LeaveRatingAndCommentOperation
import ch.decent.sdk.model.operation.MinerCreateOperation
import ch.decent.sdk.model.operation.MinerUpdateOperation
import ch.decent.sdk.model.operation.NftCreateOperation
import ch.decent.sdk.model.operation.NftIssueOperation
import ch.decent.sdk.model.operation.NftTransferOperation
import ch.decent.sdk.model.operation.NftUpdateDataOperation
import ch.decent.sdk.model.operation.NftUpdateOperation
import ch.decent.sdk.model.operation.ProposalCreateOperation
import ch.decent.sdk.model.operation.ProposalDeleteOperation
import ch.decent.sdk.model.operation.ProposalUpdateOperation
import ch.decent.sdk.model.operation.PurchaseContentOperation
import ch.decent.sdk.model.operation.RemoveContentOperation
import ch.decent.sdk.model.operation.SendMessageOperation
import ch.decent.sdk.model.operation.TransferOperation
import ch.decent.sdk.model.operation.VestingBalanceCreateOperation
import ch.decent.sdk.model.operation.VestingBalanceWithdrawOperation
import ch.decent.sdk.model.operation.WithdrawalClaimOperation
import ch.decent.sdk.model.operation.WithdrawalCreateOperation
import ch.decent.sdk.model.operation.WithdrawalDeleteOperation
import ch.decent.sdk.model.operation.WithdrawalUpdateOperation
import ch.decent.sdk.utils.SIZE_OF_POINT_ON_CURVE_COMPRESSED
import ch.decent.sdk.utils.unhex
import okio.Buffer
import okio.BufferedSink
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import java.math.BigInteger
import kotlin.reflect.KClass
import kotlin.reflect.full.superclasses


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
  private val objectIdAdapter: Adapter<ObjectId> = { buffer, obj ->
    buffer.write(Varint.writeUnsignedVarLong(obj.instance))
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

  private val keyAuthAdapter: Adapter<KeyAuth> = { buffer, obj ->
    append(buffer, obj.value)
    buffer.writeShortLe(obj.weight.toInt())
  }

  private val accountAuthAdapter: Adapter<AccountAuth> = { buffer, obj ->
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
    buffer.writeType(obj)
    append(buffer, obj.fee)
    append(buffer, obj.registrar)
    append(buffer, obj.name)
    append(buffer, obj.owner)
    append(buffer, obj.active)
    append(buffer, obj.options)
    buffer.writeByte(0)
  }

  private val accountUpdateOperationAdapter: Adapter<AccountUpdateOperation> = { buffer, obj ->
    buffer.writeType(obj)
    append(buffer, obj.fee)
    append(buffer, obj.accountId)
    append(buffer, obj.owner, true)
    append(buffer, obj.active, true)
    append(buffer, obj.options, true)
    buffer.writeByte(0)
  }

  private val purchaseContentOperationAdapter: Adapter<PurchaseContentOperation> = { buffer, obj ->
    buffer.writeType(obj)
    append(buffer, obj.fee)
    append(buffer, obj.uri)
    append(buffer, obj.consumer)
    append(buffer, obj.price)
    buffer.writeIntLe(obj.regionCode.toInt())
    append(buffer, obj.publicElGamal)
  }

  private val transferOperationAdapter: Adapter<TransferOperation> = { buffer, obj ->
    buffer.writeType(obj)
    append(buffer, obj.fee)
    append(buffer, obj.from)
    buffer.writeLongLe(obj.to.fullInstance)
    append(buffer, obj.amount)
    append(buffer, obj.memo, true)
    buffer.writeByte(0)
  }

  private val coAuthorsAdapter: Adapter<CoAuthors> = { buffer, obj ->
    buffer.write(Varint.writeUnsignedVarInt(obj.authors.size))
    obj.authors.forEach { (id, weight) ->
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
    buffer.writeType(obj)
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
    buffer.writeType(obj)
    append(buffer, obj.fee)
    append(buffer, obj.author)
    append(buffer, obj.uri)
  }

  private val customOperationAdapter: Adapter<CustomOperation> = { buffer, obj ->
    buffer.writeType(obj)
    append(buffer, obj.fee)
    append(buffer, obj.payer)
    append(buffer, obj.requiredAuths)
    buffer.writeShortLe(obj.id)
    append(buffer, obj.data.unhex())
  }

  private val rateAndCommentOperationAdapter: Adapter<LeaveRatingAndCommentOperation> = { buffer, obj ->
    buffer.writeType(obj)
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
      buffer.writeByte(it.index)
      append(buffer, it.isFixedMaxSupply)
    }
  }

  private val monitoredAssetOptionsAdapter: Adapter<MonitoredAssetOptions> = { buffer, obj ->
    append(buffer, obj.feeds)
    append(buffer, obj.currentFeed.coreExchangeRate)
    append(buffer, obj.currentFeedPublicationTime)
    buffer.writeIntLe(obj.feedLifetimeSec.toInt())
    buffer.writeByte(obj.minimumFeeds.toInt())
  }

  private val assetCreateAdapter: Adapter<AssetCreateOperation> = { buffer, obj ->
    buffer.writeType(obj)
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
    buffer.writeType(obj)
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
    buffer.writeType(obj)
    append(buffer, obj.fee)
    append(buffer, obj.issuer)
    append(buffer, obj.assetToUpdate)
    buffer.writeByte(obj.precision.toInt())
    append(buffer, obj.fixedMaxSupply)
    buffer.writeByte(0)
  }

  private val assetIssueAdapter: Adapter<AssetIssueOperation> = { buffer, obj ->
    buffer.writeType(obj)
    append(buffer, obj.fee)
    append(buffer, obj.issuer)
    append(buffer, obj.assetToIssue)
    append(buffer, obj.issueToAccount)
    append(buffer, obj.memo, true)
    buffer.writeByte(0)
  }

  private val assetFundAdapter: Adapter<AssetFundPoolsOperation> = { buffer, obj ->
    buffer.writeType(obj)
    append(buffer, obj.fee)
    append(buffer, obj.from)
    append(buffer, obj.uia)
    append(buffer, obj.dct)
    buffer.writeByte(0)
  }

  private val assetReserveAdapter: Adapter<AssetReserveOperation> = { buffer, obj ->
    buffer.writeType(obj)
    append(buffer, obj.fee)
    append(buffer, obj.payer)
    append(buffer, obj.amount)
    buffer.writeByte(0)
  }

  private val assetClaimAdapter: Adapter<AssetClaimFeesOperation> = { buffer, obj ->
    buffer.writeType(obj)
    append(buffer, obj.fee)
    append(buffer, obj.issuer)
    append(buffer, obj.uia)
    append(buffer, obj.dct)
    buffer.writeByte(0)
  }

  private val nftOptionsAdapter: Adapter<NftOptions> = { buffer, obj ->
    append(buffer, obj.issuer)
    buffer.writeIntLe(obj.maxSupply.toInt())
    append(buffer, obj.fixedMaxSupply)
    append(buffer, obj.description)
  }

  private val nftDataAdapter: Adapter<NftDataType> = { buffer, obj ->
    append(buffer, obj.unique)
    buffer.writeLongLe(obj.modifiable.ordinal.toLong())
    buffer.writeLongLe(obj.type.ordinal.toLong())
    append(buffer, obj.name, true)
  }

  private val nftCreateAdapter: Adapter<NftCreateOperation> = { buffer, obj ->
    buffer.writeType(obj)
    append(buffer, obj.fee)
    append(buffer, obj.symbol)
    append(buffer, obj.options)
    append(buffer, obj.definitions)
    append(buffer, obj.transferable)
    buffer.writeByte(0)
  }

  private val nftUpdateAdapter: Adapter<NftUpdateOperation> = { buffer, obj ->
    buffer.writeType(obj)
    append(buffer, obj.fee)
    append(buffer, obj.issuer)
    append(buffer, obj.id)
    append(buffer, obj.options)
    buffer.writeByte(0)
  }

  private val variantAdapter: Adapter<Variant> = { buffer, obj ->
    when (obj) {
      is Number -> {
        val type = if (obj.toLong() < 0) VariantTypeId.INT64_TYPE else VariantTypeId.UINT64_TYPE
        buffer.writeByte(type.ordinal)
        buffer.writeLongLe(obj.toLong())
      }
      is Boolean -> {
        buffer.writeByte(VariantTypeId.BOOL_TYPE.ordinal)
        append(buffer, obj)
      }
      is String -> {
        buffer.writeByte(VariantTypeId.STRING_TYPE.ordinal)
        append(buffer, obj)
      }
    }
  }

  private val nftIssueAdapter: Adapter<NftIssueOperation> = { buffer, obj ->
    buffer.writeType(obj)
    append(buffer, obj.fee)
    append(buffer, obj.issuer)
    append(buffer, obj.to)
    append(buffer, obj.id)
    buffer.write(Varint.writeUnsignedVarLong(obj.data.size.toLong()))
    obj.data.forEach { variantAdapter(buffer, it) }
    append(buffer, obj.memo, true)
    buffer.writeByte(0)
  }

  private val nftTransferAdapter: Adapter<NftTransferOperation> = { buffer, obj ->
    buffer.writeType(obj)
    append(buffer, obj.fee)
    append(buffer, obj.from)
    append(buffer, obj.to)
    append(buffer, obj.id)
    append(buffer, obj.memo, true)
    buffer.writeByte(0)
  }

  private val nftUpdateDataAdapter: Adapter<NftUpdateDataOperation> = { buffer, obj ->
    buffer.writeType(obj)
    append(buffer, obj.fee)
    append(buffer, obj.modifier)
    append(buffer, obj.id)
    buffer.write(Varint.writeUnsignedVarLong(obj.data.size.toLong()))
    obj.data.entries.forEach {
      append(buffer, it.key)
      variantAdapter(buffer, it.value)
    }
    buffer.writeByte(0)
  }

  private val minerCreateAdapter: Adapter<MinerCreateOperation> = { buffer, obj ->
    buffer.writeType(obj)
    append(buffer, obj.fee)
    append(buffer, obj.account)
    append(buffer, obj.url)
    append(buffer, obj.signingKey)
  }

  private val minerUpdateAdapter: Adapter<MinerUpdateOperation> = { buffer, obj ->
    buffer.writeType(obj)
    append(buffer, obj.fee)
    append(buffer, obj.miner)
    append(buffer, obj.account)
    append(buffer, obj.url, true)
    append(buffer, obj.signingKey, true)
  }

  private val proposalCreateAdapter: Adapter<ProposalCreateOperation> = { buffer, obj ->
    buffer.writeType(obj)
    append(buffer, obj.fee)
    append(buffer, obj.payer)
    append(buffer, obj.expirationTime)
    append(buffer, obj.operations.map { it.op })
    append(buffer, obj.reviewPeriodSeconds, true)
    buffer.writeByte(0)
  }

  private val proposalUpdateAdapter: Adapter<ProposalUpdateOperation> = { buffer, obj ->
    buffer.writeType(obj)
    append(buffer, obj.fee)
    append(buffer, obj.payer)
    append(buffer, obj.proposal)
    append(buffer, obj.activeApprovalsAdd)
    append(buffer, obj.activeApprovalsRemove)
    append(buffer, obj.ownerApprovalsAdd)
    append(buffer, obj.ownerApprovalsRemove)
    append(buffer, obj.keyApprovalsAdd)
    append(buffer, obj.keyApprovalsRemove)
    buffer.writeByte(0)
  }

  private val proposalDeleteAdapter: Adapter<ProposalDeleteOperation> = { buffer, obj ->
    buffer.writeType(obj)
    append(buffer, obj.fee)
    append(buffer, obj.payer)
    append(buffer, obj.usingOwnerAuthority)
    append(buffer, obj.proposal)
    buffer.writeByte(0)
  }

  private val withdrawalCreateAdapter: Adapter<WithdrawalCreateOperation> = { buffer, obj ->
    buffer.writeType(obj)
    append(buffer, obj.fee)
    append(buffer, obj.accountFrom)
    append(buffer, obj.accountTo)
    append(buffer, obj.withdrawalLimit)
    buffer.writeIntLe(obj.withdrawalPeriodSec.toInt())
    buffer.writeIntLe(obj.periodsUntilExpiration.toInt())
    append(buffer, obj.periodStartTime)
  }

  private val withdrawalUpdateAdapter: Adapter<WithdrawalUpdateOperation> = { buffer, obj ->
    buffer.writeType(obj)
    append(buffer, obj.fee)
    append(buffer, obj.accountFrom)
    append(buffer, obj.accountTo)
    append(buffer, obj.withdrawalId)
    append(buffer, obj.withdrawalLimit)
    buffer.writeIntLe(obj.withdrawalPeriodSec.toInt())
    append(buffer, obj.periodStartTime)
    buffer.writeIntLe(obj.periodsUntilExpiration.toInt())
  }

  private val withdrawalClaimAdapter: Adapter<WithdrawalClaimOperation> = { buffer, obj ->
    buffer.writeType(obj)
    append(buffer, obj.fee)
    append(buffer, obj.withdrawalId)
    append(buffer, obj.accountFrom)
    append(buffer, obj.accountTo)
    append(buffer, obj.amount)
    append(buffer, obj.memo, true)
  }

  private val withdrawalDeleteAdapter: Adapter<WithdrawalDeleteOperation> = { buffer, obj ->
    buffer.writeType(obj)
    append(buffer, obj.fee)
    append(buffer, obj.accountFrom)
    append(buffer, obj.accountTo)
    append(buffer, obj.withdrawalId)
  }

  private val linearVestingPolicyAdapter: Adapter<LinearVestingPolicyCreate> = { buffer, obj ->
    append(buffer, obj.start)
    buffer.writeIntLe(obj.cliffSeconds.toInt())
    buffer.writeIntLe(obj.durationSeconds.toInt())
  }

  private val cddVestingPolicyAdapter: Adapter<CddVestingPolicyCreate> = { buffer, obj ->
    append(buffer, obj.start)
    buffer.writeIntLe(obj.durationSeconds.toInt())
  }

  private val vestingBalanceCreateAdapter: Adapter<VestingBalanceCreateOperation> = { buffer, obj ->
    buffer.writeType(obj)
    append(buffer, obj.fee)
    append(buffer, obj.creator)
    append(buffer, obj.owner)
    append(buffer, obj.amount)
    obj.policy.append(buffer)
  }

  private val VestingBalanceWithdrawAdapter: Adapter<VestingBalanceWithdrawOperation> = { buffer, obj ->
    buffer.writeType(obj)
    append(buffer, obj.fee)
    append(buffer, obj.id)
    append(buffer, obj.owner)
    append(buffer, obj.amount)
  }

  private fun BufferedSink.writeType(op: BaseOperation) = writeByte(op.type.ordinal)

  private fun StaticVariantParametrized.append(buffer: BufferedSink) = objects.withIndex().forEach {
    if (it.value != null) {
      buffer.writeByte(it.index)
      append(buffer, it.value)
    }
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
      val adapter = (adapters[obj::class] ?: adapters[obj::class.superclasses.first()]) as Adapter<T>?
      requireNotNull(adapter) { "missing adapter for ${obj::class}" }
      adapter(buffer, obj)
    }
  }

  private val adapters: Map<KClass<*>, Adapter<*>> = mapOf(
      ObjectId::class to objectIdAdapter,
      ByteArray::class to byteArrayAdapter,
      String::class to stringAdapter,
      Address::class to addressAdapter,
      Authority::class to authorityAdapter,
      KeyAuth::class to keyAuthAdapter,
      AccountAuth::class to accountAuthAdapter,
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
      CustomOperation::class to customOperationAdapter,
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
      AssetClaimFeesOperation::class to assetClaimAdapter,
      NftOptions::class to nftOptionsAdapter,
      NftDataType::class to nftDataAdapter,
      NftCreateOperation::class to nftCreateAdapter,
      NftUpdateOperation::class to nftUpdateAdapter,
      NftIssueOperation::class to nftIssueAdapter,
      NftTransferOperation::class to nftTransferAdapter,
      NftUpdateDataOperation::class to nftUpdateDataAdapter,
      MinerCreateOperation::class to minerCreateAdapter,
      MinerUpdateOperation::class to minerUpdateAdapter,
      ProposalCreateOperation::class to proposalCreateAdapter,
      ProposalUpdateOperation::class to proposalUpdateAdapter,
      ProposalDeleteOperation::class to proposalDeleteAdapter,
      WithdrawalCreateOperation::class to withdrawalCreateAdapter,
      WithdrawalUpdateOperation::class to withdrawalUpdateAdapter,
      WithdrawalClaimOperation::class to withdrawalClaimAdapter,
      WithdrawalDeleteOperation::class to withdrawalDeleteAdapter,
      LinearVestingPolicyCreate::class to linearVestingPolicyAdapter,
      CddVestingPolicyCreate::class to cddVestingPolicyAdapter,
      VestingBalanceCreateOperation::class to vestingBalanceCreateAdapter,
      VestingBalanceWithdrawOperation::class to VestingBalanceWithdrawAdapter
  )

  fun serialize(obj: Any): ByteArray = Buffer().apply { append(this, obj) }.readByteArray()
}
