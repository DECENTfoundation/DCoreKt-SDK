package ch.decent.sdk.api.blocking

class DCoreApi(
  api: ch.decent.sdk.DCoreApi
) {
  val broadcastApi: BroadcastApi = BroadcastApi(api.broadcastApi)

  val blockApi: BlockApi = BlockApi(api.blockApi)

  val miningApi: MiningApi = MiningApi(api.miningApi)

  val purchaseApi: PurchaseApi = PurchaseApi(api.purchaseApi)

  val contentApi: ContentApi = ContentApi(api.contentApi)

  val transactionApi: TransactionApi = TransactionApi(api.transactionApi)

  val accountApi: AccountApi = AccountApi(api.accountApi)

  val seederApi: SeederApi = SeederApi(api.seederApi)

  val balanceApi: BalanceApi = BalanceApi(api.balanceApi)

  val nftApi: NftApi = NftApi(api.nftApi)

  val generalApi: GeneralApi = GeneralApi(api.generalApi)

  val historyApi: HistoryApi = HistoryApi(api.historyApi)

  val assetApi: AssetApi = AssetApi(api.assetApi)

  val subscriptionApi: SubscriptionApi = SubscriptionApi(api.subscriptionApi)

  val messagingApi: MessagingApi = MessagingApi(api.messagingApi)

  val validationApi: ValidationApi = ValidationApi(api.validationApi)
}
