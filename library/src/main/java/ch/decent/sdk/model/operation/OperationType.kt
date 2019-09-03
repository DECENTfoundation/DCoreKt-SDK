package ch.decent.sdk.model.operation

/**
 * The order of operation types is important
 */
enum class OperationType(val clazz: Class<*>? = null) {
  TRANSFER_OPERATION(TransferOperation::class.java),
  ACCOUNT_CREATE_OPERATION(AccountCreateOperation::class.java),
  ACCOUNT_UPDATE_OPERATION(AccountUpdateOperation::class.java),
  ASSET_CREATE_OPERATION(AssetCreateOperation::class.java),
  ASSET_ISSUE_OPERATION(AssetIssueOperation::class.java),
  ASSET_PUBLISH_FEED_OPERATION(AssetPublishFeedOperation::class.java),
  MINER_CREATE_OPERATION(MinerCreateOperation::class.java),
  MINER_UPDATE_OPERATION(MinerUpdateOperation::class.java),
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
  CUSTOM_OPERATION(CustomOperation::class.java),
  ASSERT_OPERATION,
  CONTENT_SUBMIT_OPERATION(AddOrUpdateContentOperation::class.java),       //20
  REQUEST_TO_BUY_OPERATION(PurchaseContentOperation::class.java),
  LEAVE_RATING_AND_COMMENT_OPERATION(LeaveRatingAndCommentOperation::class.java),
  READY_TO_PUBLISH_OPERATION,
  PROOF_OF_CUSTODY_OPERATION,
  DELIVER_KEYS_OPERATION,                 //25
  SUBSCRIBE_OPERATION,
  SUBSCRIBE_BY_AUTHOR_OPERATION,
  AUTOMATIC_RENEWAL_OF_SUBSCRIPTION_OPERATION,
  REPORT_STATS_OPERATION,
  SET_PUBLISHING_MANAGER_OPERATION, //30
  SET_PUBLISHING_RIGHT_OPERATION,
  CONTENT_CANCELLATION_OPERATION(RemoveContentOperation::class.java),
  ASSET_FUND_POOLS_OPERATION(AssetFundPoolsOperation::class.java),
  ASSET_RESERVE_OPERATION(AssetReserveOperation::class.java),
  ASSET_CLAIM_FEES_OPERATION(AssetClaimFeesOperation::class.java),     //35
  UPDATE_USER_ISSUED_ASSET_OPERATION(AssetUpdateOperation::class.java),
  UPDATE_MONITORED_ASSET_OPERATION(AssetUpdateMonitoredOperation::class.java),
  READY_TO_PUBLISH2_OPERATION,
  TRANSFER2_OPERATION(TransferOperation::class.java),
  UPDATE_USER_ISSUED_ASSET_ADVANCED(AssetUpdateAdvancedOperation::class.java), //40
  NFT_CREATE_DEFINITION(NftCreateOperation::class.java),
  NFT_UPDATE_DEFINITION(NftUpdateOperation::class.java),
  NFT_ISSUE(NftIssueOperation::class.java),
  NFT_TRANSFER(NftTransferOperation::class.java),
  NFT_UPDATE_DATA(NftUpdateDataOperation::class.java), // 45
  DISALLOW_AUTOMATIC_RENEWAL_OF_SUBSCRIPTION_OPERATION,  // VIRTUAL
  RETURN_ESCROW_SUBMISSION_OPERATION,                    // VIRTUAL
  RETURN_ESCROW_BUYING_OPERATION,                        // VIRTUAL
  PAY_SEEDER_OPERATION,                                  // VIRTUAL
  FINISH_BUYING_OPERATION,                               // VIRTUAL 50
  RENEWAL_OF_SUBSCRIPTION_OPERATION,                     // VIRTUAL
  UNKNOWN_OPERATION
}
