package ch.decent.sdk.net.model

enum class SearchAccountHistoryOrder(val value: String) {
  TYPE_ASC("+type"),
  TO_ASC("+to"),
  FROM_ASC("+from"),
  PRICE_ASC("+price"),
  FEE_ASC("+fee"),
  DESCRIPTION_ASC("+description"),
  TIME_ASC("+time"),
  TYPE_DESC("-type"),
  TO_DESC("-to"),
  FROM_DESC("-from"),
  PRICE_DESC("-price"),
  FEE_DESC("-fee"),
  DESCRIPTION_DESC("-description"),
  TIME_DESC("-time"),
}

enum class SearchPurchasesOrder(val value: String) {
  SIZE_ASC("+size"),
  PRICE_ASC("+price"),
  CREATED_ASC("+created"),
  PURCHASED_ASC("+purchased"),
  SIZE_DESC("-size"),
  PRICE_DESC("-price"),
  CREATED_DESC("-created"),
  PURCHASED_DESC("-purchased"),
}