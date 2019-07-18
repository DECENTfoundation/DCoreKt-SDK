package ch.decent.sdk

import ch.decent.sdk.api.AccountSuite
import ch.decent.sdk.api.AssetSuite
import ch.decent.sdk.api.BalanceApiTest
import ch.decent.sdk.api.BlockApiTest
import ch.decent.sdk.api.CallbackApiTest
import ch.decent.sdk.api.ContentSuite
import ch.decent.sdk.api.GeneralApiTest
import ch.decent.sdk.api.HistoryApiTest
import ch.decent.sdk.api.MessagingSuite
import ch.decent.sdk.api.MiningApiTest
import ch.decent.sdk.api.PurchaseApiTest
import ch.decent.sdk.api.SeederApiTest
import ch.decent.sdk.api.SubscriptionApiTest
import ch.decent.sdk.api.TransactionApiTest
import ch.decent.sdk.api.ValidationApiTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@Suite.SuiteClasses(
    AccountSuite::class,
    AssetSuite::class,
    BalanceApiTest::class,
    BlockApiTest::class,
    CallbackApiTest::class,
    ContentSuite::class,
    GeneralApiTest::class,
    MessagingSuite::class,
    MiningApiTest::class,
    PurchaseApiTest::class,
    SeederApiTest::class,
    SubscriptionApiTest::class,
    ValidationApiTest::class,
    HistoryApiTest::class,
    TransactionApiTest::class
)
@RunWith(Suite::class)
class ApiSuite
