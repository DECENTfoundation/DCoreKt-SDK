package ch.decent.sdk.model

import ch.decent.sdk.DCoreConstants

open class ObjectId(
    val space: Int,
    val type: Int,
    val instance: Long
) {

  init {
    check(this.instance <= DCoreConstants.MAX_INSTANCE_ID)
  }

  internal constructor(type: ObjectType, instance: Long) : this(type.space, type.type, instance)

  @Suppress("MagicNumber")
  internal val fullInstance: Long
    get() = (objectType.space.toLong().shl(56) or objectType.type.toLong().shl(48) or instance)

  val objectType: ObjectType
    get() = ObjectType.values().find { it.space == space && it.type == type } ?: ObjectType.UNKNOWN_OBJECT


  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as ObjectId

    if (space != other.space) return false
    if (type != other.type) return false
    if (instance != other.instance) return false

    return true
  }

  override fun hashCode(): Int {
    var result = space
    result = 31 * result + type
    result = 31 * result + instance.hashCode()
    return result
  }

  override fun toString(): String {
    return "$space.$type.$instance"
  }

  companion object {
    private val klasses = mapOf(
        ObjectType.BASE_OBJECT to BaseObjectId::class,
        ObjectType.ACCOUNT_OBJECT to AccountObjectId::class,
        ObjectType.ASSET_OBJECT to AssetObjectId::class,
        ObjectType.MINER_OBJECT to MinerObjectId::class,
        ObjectType.CUSTOM_OBJECT to CustomObjectId::class,
        ObjectType.PROPOSAL_OBJECT to ProposalObjectId::class,
        ObjectType.OPERATION_HISTORY_OBJECT to OperationHistoryObjectId::class,
        ObjectType.WITHDRAW_PERMISSION_OBJECT to WithdrawPermissionObjectId::class,
        ObjectType.VESTING_BALANCE_OBJECT to VestingBalanceObjectId::class,
        ObjectType.NFT_OBJECT to NftObjectId::class,
        ObjectType.NFT_DATA_OBJECT to NftDataObjectId::class,
        ObjectType.GLOBAL_PROPERTY_OBJECT to GlobalPropertyObjectId::class,
        ObjectType.DYNAMIC_GLOBAL_PROPERTY_OBJECT to DynamicGlobalPropertyObjectId::class,
        ObjectType.RESERVED_OBJECT to ReservedObjectId::class,
        ObjectType.ASSET_DYNAMIC_DATA to AssetDataObjectId::class,
        ObjectType.ACCOUNT_BALANCE_OBJECT to AccountBalanceObjectId::class,
        ObjectType.ACCOUNT_STATISTICS_OBJECT to AccountStatsObjectId::class,
        ObjectType.TRANSACTION_OBJECT to TransactionObjectId::class,
        ObjectType.BLOCK_SUMMARY_OBJECT to BlockSummaryObjectId::class,
        ObjectType.ACCOUNT_TRANSACTION_HISTORY_OBJECT to AccountTransactionObjectId::class,
        ObjectType.CHAIN_PROPERTY_OBJECT to ChainPropertyObjectId::class,
        ObjectType.MINER_SCHEDULE_OBJECT to MinerScheduleObjectId::class,
        ObjectType.BUDGET_RECORD_OBJECT to BudgetRecordObjectId::class,
        ObjectType.PURCHASE_OBJECT to PurchaseObjectId::class,
        ObjectType.CONTENT_OBJECT to ContentObjectId::class,
        ObjectType.PUBLISHER_OBJECT to PublisherObjectId::class,
        ObjectType.SUBSCRIPTION_OBJECT to SubscriptionObjectId::class,
        ObjectType.SEEDING_STATISTICS_OBJECT to SeedingStatsObjectId::class,
        ObjectType.TRANSACTION_DETAIL_OBJECT to TransactionDetailObjectId::class,
        ObjectType.MESSAGING_OBJECT to MessagingObjectId::class
    )
    private val regex = Regex("""(\d+)\.(\d+)\.(\d+)""")

    @JvmStatic fun isValid(id: String): Boolean = regex.matches(id.trim())

    @JvmStatic fun parse(id: String): ObjectId =
        if (isValid(id)) regex.matchEntire(id)!!.groupValues.iterator().let {
          it.next()
          ObjectId(it.next().toInt(), it.next().toInt(), it.next().toLong())
        }
        else throw IllegalArgumentException("cannot parse id $id")

    @JvmStatic fun parseToType(id: String): ObjectId = parse(id).let {
      when {
        it.objectType == ObjectType.NULL_OBJECT -> NullObjectId
        klasses.containsKey(it.objectType) -> klasses.getValue(it.objectType).constructors.single().call(it.instance)
        else -> it
      }
    }
  }
}

inline fun <reified T : ObjectId> String.isValidId(): Boolean = ObjectId.isValid(this) && ObjectId.parseToType(this) is T

inline fun <reified T : ObjectId> String.toObjectId(): T = ObjectId.parseToType(this).let {
  if (it is T) it else throw IllegalArgumentException("invalid ${T::class} for ${it.objectType} in id $this")
}

object NullObjectId : ObjectId(ObjectType.NULL_OBJECT, 0)
class BaseObjectId(instance: Long = 0) : ObjectId(ObjectType.BASE_OBJECT, instance)
class AccountObjectId(instance: Long = 0) : ObjectId(ObjectType.ACCOUNT_OBJECT, instance)
class AssetObjectId(instance: Long = 0) : ObjectId(ObjectType.ASSET_OBJECT, instance)
class MinerObjectId(instance: Long = 0) : ObjectId(ObjectType.MINER_OBJECT, instance)
class CustomObjectId(instance: Long = 0) : ObjectId(ObjectType.CUSTOM_OBJECT, instance)
class ProposalObjectId(instance: Long = 0) : ObjectId(ObjectType.PROPOSAL_OBJECT, instance)
class OperationHistoryObjectId(instance: Long = 0) : ObjectId(ObjectType.OPERATION_HISTORY_OBJECT, instance)
class WithdrawPermissionObjectId(instance: Long = 0) : ObjectId(ObjectType.WITHDRAW_PERMISSION_OBJECT, instance)
class VestingBalanceObjectId(instance: Long = 0) : ObjectId(ObjectType.VESTING_BALANCE_OBJECT, instance)
class NftObjectId(instance: Long = 0) : ObjectId(ObjectType.NFT_OBJECT, instance)
class NftDataObjectId(instance: Long = 0) : ObjectId(ObjectType.NFT_DATA_OBJECT, instance)
class GlobalPropertyObjectId(instance: Long = 0) : ObjectId(ObjectType.GLOBAL_PROPERTY_OBJECT, instance)
class DynamicGlobalPropertyObjectId(instance: Long = 0) : ObjectId(ObjectType.DYNAMIC_GLOBAL_PROPERTY_OBJECT, instance)
class ReservedObjectId(instance: Long = 0) : ObjectId(ObjectType.RESERVED_OBJECT, instance)
class AssetDataObjectId(instance: Long = 0) : ObjectId(ObjectType.ASSET_DYNAMIC_DATA, instance)
class AccountBalanceObjectId(instance: Long = 0) : ObjectId(ObjectType.ACCOUNT_BALANCE_OBJECT, instance)
class AccountStatsObjectId(instance: Long = 0) : ObjectId(ObjectType.ACCOUNT_STATISTICS_OBJECT, instance)
class TransactionObjectId(instance: Long = 0) : ObjectId(ObjectType.TRANSACTION_OBJECT, instance)
class BlockSummaryObjectId(instance: Long = 0) : ObjectId(ObjectType.BLOCK_SUMMARY_OBJECT, instance)
class AccountTransactionObjectId(instance: Long = 0) : ObjectId(ObjectType.ACCOUNT_TRANSACTION_HISTORY_OBJECT, instance)
class ChainPropertyObjectId(instance: Long = 0) : ObjectId(ObjectType.CHAIN_PROPERTY_OBJECT, instance)
class MinerScheduleObjectId(instance: Long = 0) : ObjectId(ObjectType.MINER_SCHEDULE_OBJECT, instance)
class BudgetRecordObjectId(instance: Long = 0) : ObjectId(ObjectType.BUDGET_RECORD_OBJECT, instance)
class PurchaseObjectId(instance: Long = 0) : ObjectId(ObjectType.PURCHASE_OBJECT, instance)
class ContentObjectId(instance: Long = 0) : ObjectId(ObjectType.CONTENT_OBJECT, instance)
class PublisherObjectId(instance: Long = 0) : ObjectId(ObjectType.PUBLISHER_OBJECT, instance)
class SubscriptionObjectId(instance: Long = 0) : ObjectId(ObjectType.SUBSCRIPTION_OBJECT, instance)
class SeedingStatsObjectId(instance: Long = 0) : ObjectId(ObjectType.SEEDING_STATISTICS_OBJECT, instance)
class TransactionDetailObjectId(instance: Long = 0) : ObjectId(ObjectType.TRANSACTION_DETAIL_OBJECT, instance)
class MessagingObjectId(instance: Long = 0) : ObjectId(ObjectType.MESSAGING_OBJECT, instance)
