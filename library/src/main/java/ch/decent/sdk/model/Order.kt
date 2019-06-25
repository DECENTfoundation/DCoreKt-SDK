package ch.decent.sdk.model

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

enum class SearchContentOrder(val value: String) {
  AUTHOR_ASC("+author"),
  RATING_ASC("+rating"),
  SIZE_ASC("+size"),
  PRICE_ASC("+price"),
  CREATED_ASC("+created"),
  EXPIRATION_ASC("+expiration"),
  AUTHOR_DESC("-author"),
  RATING_DESC("-rating"),
  SIZE_DESC("-size"),
  PRICE_DESC("-price"),
  CREATED_DESC("-created"),
  EXPIRATION_DESC("-expiration"),
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

enum class SearchAccountsOrder(val value: String) {
  ID_ASC("+id"),
  NAME_ASC("+name"),
  ID_DESC("-id"),
  NAME_DESC("-name"),
}

enum class SearchMinerVotingOrder(val value: String) {
  NAME_ASC("+name"),
  NAME_DESC("-name"),
}
