package ch.decent.sdk.api

import ch.decent.sdk.model.ChainObject
import ch.decent.sdk.model.ObjectType
import ch.decent.sdk.model.Purchase
import ch.decent.sdk.model.SearchPurchasesOrder
import io.reactivex.Single

interface PurchaseApi {

  /**
   * search consumer open and history purchases
   *
   * @param consumer object id of the account, 1.2.*
   * @param term search term
   * @param order [SearchPurchasesOrder]
   * @param from object id of the history object to start from, use [ObjectType.NULL_OBJECT.genericId] to ignore
   * @param limit number of entries, max 100
   */
  fun searchPurchases(
      consumer: ChainObject,
      term: String = "",
      order: SearchPurchasesOrder = SearchPurchasesOrder.PURCHASED_DESC,
      from: ChainObject = ObjectType.NULL_OBJECT.genericId,
      limit: Int = 100
  ): Single<List<Purchase>>

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
  ): Single<Purchase>

}