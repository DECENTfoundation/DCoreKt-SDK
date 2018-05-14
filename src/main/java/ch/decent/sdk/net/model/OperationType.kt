package ch.decent.sdk.net.model

import ch.decent.sdk.model.AccountUpdateOperation
import ch.decent.sdk.model.BuyContentOperation
import ch.decent.sdk.model.TransferOperation

/**
  The order of operation types is important
 */
enum class OperationType(val clazz: Class<*>? = null) {
  TRANSFER_OPERATION(TransferOperation::class.java),
  ACCOUNT_CREATE_OPERATION,
  ACCOUNT_UPDATE_OPERATION(AccountUpdateOperation::class.java),
  ASSET_CREATE_OPERATION,
  ASSET_ISSUE_OPERATION,
  ASSET_PUBLISH_FEED_OPERATION,
  MINER_CREATE_OPERATION,
  MINER_UPDATE_OPERATION,
  MINER_UPDATE_GLOBAL_PARAMETERS_OPERATION,
  PROPOSAL_CREATE_OPERATION,
  PROPOSAL_UPDATE_OPERATION,      //10
  PROPOSAL_DELETE_OPERATION,
  WITHDRAW_PERMISSION_CREATE_OPERATION,
  WITHDRAW_PERMISSION_UPDATE_OPERATION,
  WITHDRAW_PERMISSION_CLAIM_OPERATION,
  WITHDRAW_PERMISSION_DELETE_OPERATION,   //15
  VESTING_BALANCE_CREATE_OPERATION,
  VESTING_BALANCE_WITHDRAW_OPERATION,
  CUSTOM_OPERATION,
  ASSERT_OPERATION,
  CONTENT_SUBMIT_OPERATION,       //20
  REQUEST_TO_BUY_OPERATION(BuyContentOperation::class.java),
  LEAVE_RATING_AND_COMMENT_OPERATION,
  READY_TO_PUBLISH_OPERATION,
  PROOF_OF_CUSTODY_OPERATION,
  DELIVER_KEYS_OPERATION,                 //25
  SUBSCRIBE_OPERATION,
  SUBSCRIBE_BY_AUTHOR_OPERATION,
  AUTOMATIC_RENEWAL_OF_SUBSCRIPTION_OPERATION,
  REPORT_STATS_OPERATION,
  SET_PUBLISHING_MANAGER_OPERATION, //30
  SET_PUBLISHING_RIGHT_OPERATION,
  CONTENT_CANCELLATION_OPERATION,
  ASSET_FUND_POOLS_OPERATION,
  ASSET_RESERVE_OPERATION,
  ASSET_CLAIM_FEES_OPERATION,     //35
  UPDATE_USER_ISSUED_ASSET_OPERATION,
  UPDATE_MONITORED_ASSET_OPERATION,
  READY_TO_PUBLISH2_OPERATION,
  DISALLOW_AUTOMATIC_RENEWAL_OF_SUBSCRIPTION_OPERATION,  // VIRTUAL
  RETURN_ESCROW_SUBMISSION_OPERATION,                    // VIRTUAL 40
  RETURN_ESCROW_BUYING_OPERATION,                        // VIRTUAL
  PAY_SEEDER_OPERATION,                                  // VIRTUAL
  FINISH_BUYING_OPERATION,                               // VIRTUAL
  RENEWAL_OF_SUBSCRIPTION_OPERATION                      // VIRTUAL
}