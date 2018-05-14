package ch.decent.sdk.model

import kotlin.math.max

/**
 * Enum type used to list all possible object types and obtain their space + type id
 */
enum class ObjectType {
  // dcore/libraries/chain/include/graphene/chain/protocol/types.hpp
  //  enum object_type, space = 1
  NULL_OBJECT, // ordinal = 0, type = 0, space = any
  BASE_OBJECT,
  ACCOUNT_OBJECT,
  ASSET_OBJECT,
  MINER_OBJECT,
  CUSTOM_OBJECT, //5
  PROPOSAL_OBJECT,
  OPERATION_HISTORY_OBJECT,
  WITHDRAW_PERMISSION_OBJECT,
  VESTING_BALANCE_OBJECT,

  //  enum impl_object_type, space = 2
  GLOBAL_PROPERTY_OBJECT, // ordinal = 10, type 0
  DYNAMIC_GLOBAL_PROPERTY_OBJECT,
  RESERVED_OBJECT,
  ASSET_DYNAMIC_DATA,
  ACCOUNT_BALANCE_OBJECT,
  ACCOUNT_STATISTICS_OBJECT, //5
  TRANSACTION_OBJECT,
  BLOCK_SUMMARY_OBJECT,
  ACCOUNT_TRANSACTION_HISTORY_OBJECT,
  CHAIN_PROPERTY_OBJECT,
  MINER_SCHEDULE_OBJECT, //10
  BUDGET_RECORD_OBJECT,
  BUYING_OBJECT,
  CONTENT_OBJECT,
  PUBLISHER_OBJECT,
  SUBSCRIPTION_OBJECT, //15
  SEEDING_STATISTICS_OBJECT,
  TRANSACTION_DETAIL_OBJECT,
  MESSAGING_OBJECT;

  val space
    get() = if(this == NULL_OBJECT) 0 else if (ordinal < 10) 1 else 2

  val type
    get() = ordinal - (max(space - 1, 0)) * 10

  /**
   * This method is used to return the generic object type in the form space.type.0.
   *
   * Not to be confused with [ChainObject.getObjectId], which will return
   * the full object id in the form space.type.id.
   *
   * @return: The generic object type
   */
  val genericId: ChainObject
    get() = ChainObject(this)

  companion object {
    fun fromSpaceType(space: Int, type: Int) = ObjectType.values()[max(space - 1, 0) * 10 + type]
  }
}
