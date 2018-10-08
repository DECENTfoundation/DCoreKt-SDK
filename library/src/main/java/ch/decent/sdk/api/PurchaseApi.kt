package ch.decent.sdk.api

import ch.decent.sdk.DCoreApi
import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.model.Purchase
import ch.decent.sdk.model.SearchPurchasesOrder
import ch.decent.sdk.net.model.request.GetBuyingByUri
import ch.decent.sdk.net.model.request.SearchBuyings
import io.reactivex.Single

class PurchaseApi internal constructor(api: DCoreApi) : BaseApi(api) {

  /**
   * search consumer open and history purchases
   *
   * @param consumer object id of the account, 1.2.*
   * @param term search term
   * @param from object id of the history object to start from, use [ObjectType.NULL_OBJECT.genericId] to ignore
   * @param order [SearchPurchasesOrder]
   * @param limit number of entries, max 100
   *
   * @return list of purchases
   */
  @JvmOverloads
  fun searchPurchases(
      consumer: ChainObject,
      term: String = "",
      from: ChainObject = ObjectType.NULL_OBJECT.genericId,
      order: SearchPurchasesOrder = SearchPurchasesOrder.PURCHASED_DESC,
      limit: Int = 100
  ): Single<List<Purchase>> = SearchBuyings(consumer, order, from, term, limit).toRequest()

  /**
   * get consumer buying by content uri
   *
   * @param consumer object id of the account, 1.2.*
   * @param uri a uri of the content
   *
   * @return an account if found, [ch.decent.sdk.exception.ObjectNotFoundException] otherwise
   */
  fun getPurchase(
      consumer: ChainObject,
      uri: String
  ): Single<Purchase> = GetBuyingByUri(consumer, uri).toRequest()

}