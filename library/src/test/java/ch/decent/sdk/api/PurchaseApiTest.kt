package ch.decent.sdk.api

import ch.decent.sdk.account
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class PurchaseApiTest(channel: Channel) : BaseApiTest(channel) {
//  override val useMock: Boolean = false

  @Test fun `should get list of open purchases`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"get_open_buyings",[]],"id":1}""",
            """{"id":1,"result":[]}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":[]}"""
    )

    val test = api.purchaseApi.getAllOpen()
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get list of open purchases for account`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"get_open_buyings_by_consumer",["1.2.34"]],"id":1}""",
            """{"id":1,"result":[]}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":[]}"""
    )

    val test = api.purchaseApi.getAllOpenByAccount(account)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get list of open purchases for uri`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"get_open_buyings_by_URI",["http://some.uri"]],"id":1}""",
            """{"id":1,"result":[]}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":[]}"""
    )

    val test = api.purchaseApi.getAllOpenByUri("http://some.uri")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get list of history purchases`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"get_buying_history_objects_by_consumer",["1.2.34"]],"id":1}""",
            """{"id":1,"result":[{"id":"2.12.3","consumer":"1.2.34","URI":"http://alax.io/?scheme=alax%3A%2F%2F1%2F2&version=bbc8a9c3-1bcd-48a6-820d-e5a60c29cf56","synopsis":"{\"content_type_id\":\"1.5.5.0\",\"title\":\"Product 2\",\"description\":\"{\\\"productId\\\":2,\\\"applicationId\\\":1}\"}","price":{"amount":0,"asset_id":"1.3.0"},"paid_price_before_exchange":{"amount":100000000,"asset_id":"1.3.0"},"paid_price_after_exchange":{"amount":100000000,"asset_id":"1.3.0"},"seeders_answered":[],"size":1,"rating":"18446744073709551615","comment":"","expiration_time":"2018-05-16T08:59:10","pubKey":{"s":"5182545488318095000498180568539728214545472470974958338942426759510121851708530625921436777555517288139787965253547588340803542762268721656138876002028437."},"key_particles":[],"expired":false,"delivered":true,"expiration_or_delivery_time":"2018-05-15T08:59:10","rated_or_commented":false,"created":"2018-04-26T15:34:50","region_code_from":1},{"id":"2.12.5","consumer":"1.2.34","URI":"http://alax.io/?scheme=alax%3A%2F%2F1%2F2&version=13f99277-f746-447f-9d07-47e597d7b0e0","synopsis":"{\"content_type_id\":\"1.5.5.0\",\"title\":\"Product 2\",\"description\":\"{\\\"productId\\\":2,\\\"applicationId\\\":1}\"}","price":{"amount":0,"asset_id":"1.3.0"},"paid_price_before_exchange":{"amount":100000000,"asset_id":"1.3.0"},"paid_price_after_exchange":{"amount":100000000,"asset_id":"1.3.0"},"seeders_answered":[],"size":1,"rating":"18446744073709551615","comment":"","expiration_time":"2018-05-29T13:49:00","pubKey":{"s":"5182545488318095000498180568539728214545472470974958338942426759510121851708530625921436777555517288139787965253547588340803542762268721656138876002028437."},"key_particles":[],"expired":false,"delivered":true,"expiration_or_delivery_time":"2018-05-28T13:49:00","rated_or_commented":false,"created":"2018-04-26T15:36:15","region_code_from":1},{"id":"2.12.6","consumer":"1.2.34","URI":"http://alax.io/?scheme=alax%3A%2F%2Ftest%3A3&version=3b0b6b4b-5c8f-412f-ba03-bf7e6679df49","synopsis":"{\"content_type_id\":\"1.5.5.0\",\"title\":\"Hra\",\"description\":\"{\\\"applicationId\\\":3,\\\"vendorId\\\":2}\"}","price":{"amount":0,"asset_id":"1.3.0"},"paid_price_before_exchange":{"amount":100000000,"asset_id":"1.3.0"},"paid_price_after_exchange":{"amount":100000000,"asset_id":"1.3.0"},"seeders_answered":[],"size":1,"rating":"18446744073709551615","comment":"","expiration_time":"2018-05-30T07:38:05","pubKey":{"s":"5182545488318095000498180568539728214545472470974958338942426759510121851708530625921436777555517288139787965253547588340803542762268721656138876002028437."},"key_particles":[],"expired":false,"delivered":true,"expiration_or_delivery_time":"2018-05-29T07:38:05","rated_or_commented":false,"created":"2018-05-29T07:36:20","region_code_from":1},{"id":"2.12.9","consumer":"1.2.34","URI":"http://alax.io/?scheme=alax%3A%2F%2F1%2F2&version=cb525aa4-6d75-4f27-84bc-cdf372ef148b","synopsis":"{\"content_type_id\":\"1.5.5.0\",\"title\":\"Product 2\",\"description\":\"{\\\"productId\\\":2,\\\"applicationId\\\":1}\"}","price":{"amount":0,"asset_id":"1.3.0"},"paid_price_before_exchange":{"amount":100000000,"asset_id":"1.3.0"},"paid_price_after_exchange":{"amount":100000000,"asset_id":"1.3.0"},"seeders_answered":[],"size":1,"rating":"18446744073709551615","comment":"","expiration_time":"2018-05-31T09:16:10","pubKey":{"s":"5182545488318095000498180568539728214545472470974958338942426759510121851708530625921436777555517288139787965253547588340803542762268721656138876002028437."},"key_particles":[],"expired":false,"delivered":true,"expiration_or_delivery_time":"2018-05-30T09:16:10","rated_or_commented":false,"created":"2018-04-26T15:48:50","region_code_from":1},{"id":"2.12.13","consumer":"1.2.34","URI":"http://alax.io/?scheme=alax%3A%2F%2F1%2F2&version=3c7cf98f-fdff-4bfa-b698-b30ee8abfe92","synopsis":"{\"content_type_id\":\"1.5.5.0\",\"title\":\"Product 2\",\"description\":\"{\\\"productId\\\":2,\\\"applicationId\\\":1}\"}","price":{"amount":0,"asset_id":"1.3.0"},"paid_price_before_exchange":{"amount":100000000,"asset_id":"1.3.0"},"paid_price_after_exchange":{"amount":100000000,"asset_id":"1.3.0"},"seeders_answered":[],"size":1,"rating":"18446744073709551615","comment":"","expiration_time":"2018-05-31T09:21:10","pubKey":{"s":"5182545488318095000498180568539728214545472470974958338942426759510121851708530625921436777555517288139787965253547588340803542762268721656138876002028437."},"key_particles":[],"expired":false,"delivered":true,"expiration_or_delivery_time":"2018-05-30T09:21:10","rated_or_commented":false,"created":"2018-04-27T08:54:25","region_code_from":1},{"id":"2.12.14","consumer":"1.2.34","URI":"http://alax.io/?scheme=alax%3A%2F%2F1%2F1&version=b711dc9b-3627-4f37-93f3-6f6f3137bcca","synopsis":"{\"content_type_id\":\"1.5.5.0\",\"title\":\"New product 2\",\"description\":\"{\\\"productId\\\":1,\\\"applicationId\\\":1}\"}","price":{"amount":0,"asset_id":"1.3.0"},"paid_price_before_exchange":{"amount":100000000,"asset_id":"1.3.0"},"paid_price_after_exchange":{"amount":100000000,"asset_id":"1.3.0"},"seeders_answered":[],"size":1,"rating":"18446744073709551615","comment":"","expiration_time":"2018-05-31T09:21:10","pubKey":{"s":"5182545488318095000498180568539728214545472470974958338942426759510121851708530625921436777555517288139787965253547588340803542762268721656138876002028437."},"key_particles":[],"expired":false,"delivered":true,"expiration_or_delivery_time":"2018-05-30T09:21:10","rated_or_commented":false,"created":"2018-05-21T09:29:15","region_code_from":1},{"id":"2.12.15","consumer":"1.2.34","URI":"http://alax.io/?scheme=alax%3A%2F%2F1%2F2&version=65fe7fa5-a81b-45fc-a733-c713dd816024","synopsis":"{\"content_type_id\":\"1.5.5.0\",\"title\":\"Product 2\",\"description\":\"{\\\"productId\\\":2,\\\"applicationId\\\":1}\"}","price":{"amount":0,"asset_id":"1.3.0"},"paid_price_before_exchange":{"amount":100000000,"asset_id":"1.3.0"},"paid_price_after_exchange":{"amount":100000000,"asset_id":"1.3.0"},"seeders_answered":[],"size":1,"rating":"18446744073709551615","comment":"","expiration_time":"2018-05-31T09:21:40","pubKey":{"s":"5182545488318095000498180568539728214545472470974958338942426759510121851708530625921436777555517288139787965253547588340803542762268721656138876002028437."},"key_particles":[],"expired":false,"delivered":true,"expiration_or_delivery_time":"2018-05-30T09:21:40","rated_or_commented":false,"created":"2018-04-26T15:57:05","region_code_from":1},{"id":"2.12.16","consumer":"1.2.34","URI":"http://alax.io/?scheme=alax%3A%2F%2F1%2F2&version=6eb8850f-2edd-4164-9069-3f3f5ea2eca5","synopsis":"{\"content_type_id\":\"1.5.5.0\",\"title\":\"Product 2\",\"description\":\"{\\\"productId\\\":2,\\\"applicationId\\\":1}\"}","price":{"amount":0,"asset_id":"1.3.0"},"paid_price_before_exchange":{"amount":100000000,"asset_id":"1.3.0"},"paid_price_after_exchange":{"amount":100000000,"asset_id":"1.3.0"},"seeders_answered":[],"size":1,"rating":"18446744073709551615","comment":"","expiration_time":"2018-05-31T09:30:30","pubKey":{"s":"5182545488318095000498180568539728214545472470974958338942426759510121851708530625921436777555517288139787965253547588340803542762268721656138876002028437."},"key_particles":[],"expired":false,"delivered":true,"expiration_or_delivery_time":"2018-05-30T09:30:30","rated_or_commented":false,"created":"2018-05-21T09:37:10","region_code_from":1},{"id":"2.12.17","consumer":"1.2.34","URI":"http://alax.io/?scheme=alax%3A%2F%2F1%2F1&version=949da412-18bd-4b8d-acba-e8fd7a594d88","synopsis":"{\"content_type_id\":\"1.5.5.0\",\"title\":\"New product 2\",\"description\":\"{\\\"productId\\\":1,\\\"applicationId\\\":1}\"}","price":{"amount":0,"asset_id":"1.3.0"},"paid_price_before_exchange":{"amount":100000000,"asset_id":"1.3.0"},"paid_price_after_exchange":{"amount":100000000,"asset_id":"1.3.0"},"seeders_answered":[],"size":1,"rating":"18446744073709551615","comment":"","expiration_time":"2018-05-31T11:34:40","pubKey":{"s":"5182545488318095000498180568539728214545472470974958338942426759510121851708530625921436777555517288139787965253547588340803542762268721656138876002028437."},"key_particles":[],"expired":false,"delivered":true,"expiration_or_delivery_time":"2018-05-30T11:34:40","rated_or_commented":false,"created":"2018-05-21T09:37:20","region_code_from":1}]}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":[{"id":"2.12.3","consumer":"1.2.34","URI":"http://alax.io/?scheme=alax%3A%2F%2F1%2F2&version=bbc8a9c3-1bcd-48a6-820d-e5a60c29cf56","synopsis":"{\"content_type_id\":\"1.5.5.0\",\"title\":\"Product 2\",\"description\":\"{\\\"productId\\\":2,\\\"applicationId\\\":1}\"}","price":{"amount":0,"asset_id":"1.3.0"},"paid_price_before_exchange":{"amount":100000000,"asset_id":"1.3.0"},"paid_price_after_exchange":{"amount":100000000,"asset_id":"1.3.0"},"seeders_answered":[],"size":1,"rating":"18446744073709551615","comment":"","expiration_time":"2018-05-16T08:59:10","pubKey":{"s":"5182545488318095000498180568539728214545472470974958338942426759510121851708530625921436777555517288139787965253547588340803542762268721656138876002028437."},"key_particles":[],"expired":false,"delivered":true,"expiration_or_delivery_time":"2018-05-15T08:59:10","rated_or_commented":false,"created":"2018-04-26T15:34:50","region_code_from":1},{"id":"2.12.5","consumer":"1.2.34","URI":"http://alax.io/?scheme=alax%3A%2F%2F1%2F2&version=13f99277-f746-447f-9d07-47e597d7b0e0","synopsis":"{\"content_type_id\":\"1.5.5.0\",\"title\":\"Product 2\",\"description\":\"{\\\"productId\\\":2,\\\"applicationId\\\":1}\"}","price":{"amount":0,"asset_id":"1.3.0"},"paid_price_before_exchange":{"amount":100000000,"asset_id":"1.3.0"},"paid_price_after_exchange":{"amount":100000000,"asset_id":"1.3.0"},"seeders_answered":[],"size":1,"rating":"18446744073709551615","comment":"","expiration_time":"2018-05-29T13:49:00","pubKey":{"s":"5182545488318095000498180568539728214545472470974958338942426759510121851708530625921436777555517288139787965253547588340803542762268721656138876002028437."},"key_particles":[],"expired":false,"delivered":true,"expiration_or_delivery_time":"2018-05-28T13:49:00","rated_or_commented":false,"created":"2018-04-26T15:36:15","region_code_from":1},{"id":"2.12.6","consumer":"1.2.34","URI":"http://alax.io/?scheme=alax%3A%2F%2Ftest%3A3&version=3b0b6b4b-5c8f-412f-ba03-bf7e6679df49","synopsis":"{\"content_type_id\":\"1.5.5.0\",\"title\":\"Hra\",\"description\":\"{\\\"applicationId\\\":3,\\\"vendorId\\\":2}\"}","price":{"amount":0,"asset_id":"1.3.0"},"paid_price_before_exchange":{"amount":100000000,"asset_id":"1.3.0"},"paid_price_after_exchange":{"amount":100000000,"asset_id":"1.3.0"},"seeders_answered":[],"size":1,"rating":"18446744073709551615","comment":"","expiration_time":"2018-05-30T07:38:05","pubKey":{"s":"5182545488318095000498180568539728214545472470974958338942426759510121851708530625921436777555517288139787965253547588340803542762268721656138876002028437."},"key_particles":[],"expired":false,"delivered":true,"expiration_or_delivery_time":"2018-05-29T07:38:05","rated_or_commented":false,"created":"2018-05-29T07:36:20","region_code_from":1},{"id":"2.12.9","consumer":"1.2.34","URI":"http://alax.io/?scheme=alax%3A%2F%2F1%2F2&version=cb525aa4-6d75-4f27-84bc-cdf372ef148b","synopsis":"{\"content_type_id\":\"1.5.5.0\",\"title\":\"Product 2\",\"description\":\"{\\\"productId\\\":2,\\\"applicationId\\\":1}\"}","price":{"amount":0,"asset_id":"1.3.0"},"paid_price_before_exchange":{"amount":100000000,"asset_id":"1.3.0"},"paid_price_after_exchange":{"amount":100000000,"asset_id":"1.3.0"},"seeders_answered":[],"size":1,"rating":"18446744073709551615","comment":"","expiration_time":"2018-05-31T09:16:10","pubKey":{"s":"5182545488318095000498180568539728214545472470974958338942426759510121851708530625921436777555517288139787965253547588340803542762268721656138876002028437."},"key_particles":[],"expired":false,"delivered":true,"expiration_or_delivery_time":"2018-05-30T09:16:10","rated_or_commented":false,"created":"2018-04-26T15:48:50","region_code_from":1},{"id":"2.12.13","consumer":"1.2.34","URI":"http://alax.io/?scheme=alax%3A%2F%2F1%2F2&version=3c7cf98f-fdff-4bfa-b698-b30ee8abfe92","synopsis":"{\"content_type_id\":\"1.5.5.0\",\"title\":\"Product 2\",\"description\":\"{\\\"productId\\\":2,\\\"applicationId\\\":1}\"}","price":{"amount":0,"asset_id":"1.3.0"},"paid_price_before_exchange":{"amount":100000000,"asset_id":"1.3.0"},"paid_price_after_exchange":{"amount":100000000,"asset_id":"1.3.0"},"seeders_answered":[],"size":1,"rating":"18446744073709551615","comment":"","expiration_time":"2018-05-31T09:21:10","pubKey":{"s":"5182545488318095000498180568539728214545472470974958338942426759510121851708530625921436777555517288139787965253547588340803542762268721656138876002028437."},"key_particles":[],"expired":false,"delivered":true,"expiration_or_delivery_time":"2018-05-30T09:21:10","rated_or_commented":false,"created":"2018-04-27T08:54:25","region_code_from":1},{"id":"2.12.14","consumer":"1.2.34","URI":"http://alax.io/?scheme=alax%3A%2F%2F1%2F1&version=b711dc9b-3627-4f37-93f3-6f6f3137bcca","synopsis":"{\"content_type_id\":\"1.5.5.0\",\"title\":\"New product 2\",\"description\":\"{\\\"productId\\\":1,\\\"applicationId\\\":1}\"}","price":{"amount":0,"asset_id":"1.3.0"},"paid_price_before_exchange":{"amount":100000000,"asset_id":"1.3.0"},"paid_price_after_exchange":{"amount":100000000,"asset_id":"1.3.0"},"seeders_answered":[],"size":1,"rating":"18446744073709551615","comment":"","expiration_time":"2018-05-31T09:21:10","pubKey":{"s":"5182545488318095000498180568539728214545472470974958338942426759510121851708530625921436777555517288139787965253547588340803542762268721656138876002028437."},"key_particles":[],"expired":false,"delivered":true,"expiration_or_delivery_time":"2018-05-30T09:21:10","rated_or_commented":false,"created":"2018-05-21T09:29:15","region_code_from":1},{"id":"2.12.15","consumer":"1.2.34","URI":"http://alax.io/?scheme=alax%3A%2F%2F1%2F2&version=65fe7fa5-a81b-45fc-a733-c713dd816024","synopsis":"{\"content_type_id\":\"1.5.5.0\",\"title\":\"Product 2\",\"description\":\"{\\\"productId\\\":2,\\\"applicationId\\\":1}\"}","price":{"amount":0,"asset_id":"1.3.0"},"paid_price_before_exchange":{"amount":100000000,"asset_id":"1.3.0"},"paid_price_after_exchange":{"amount":100000000,"asset_id":"1.3.0"},"seeders_answered":[],"size":1,"rating":"18446744073709551615","comment":"","expiration_time":"2018-05-31T09:21:40","pubKey":{"s":"5182545488318095000498180568539728214545472470974958338942426759510121851708530625921436777555517288139787965253547588340803542762268721656138876002028437."},"key_particles":[],"expired":false,"delivered":true,"expiration_or_delivery_time":"2018-05-30T09:21:40","rated_or_commented":false,"created":"2018-04-26T15:57:05","region_code_from":1},{"id":"2.12.16","consumer":"1.2.34","URI":"http://alax.io/?scheme=alax%3A%2F%2F1%2F2&version=6eb8850f-2edd-4164-9069-3f3f5ea2eca5","synopsis":"{\"content_type_id\":\"1.5.5.0\",\"title\":\"Product 2\",\"description\":\"{\\\"productId\\\":2,\\\"applicationId\\\":1}\"}","price":{"amount":0,"asset_id":"1.3.0"},"paid_price_before_exchange":{"amount":100000000,"asset_id":"1.3.0"},"paid_price_after_exchange":{"amount":100000000,"asset_id":"1.3.0"},"seeders_answered":[],"size":1,"rating":"18446744073709551615","comment":"","expiration_time":"2018-05-31T09:30:30","pubKey":{"s":"5182545488318095000498180568539728214545472470974958338942426759510121851708530625921436777555517288139787965253547588340803542762268721656138876002028437."},"key_particles":[],"expired":false,"delivered":true,"expiration_or_delivery_time":"2018-05-30T09:30:30","rated_or_commented":false,"created":"2018-05-21T09:37:10","region_code_from":1},{"id":"2.12.17","consumer":"1.2.34","URI":"http://alax.io/?scheme=alax%3A%2F%2F1%2F1&version=949da412-18bd-4b8d-acba-e8fd7a594d88","synopsis":"{\"content_type_id\":\"1.5.5.0\",\"title\":\"New product 2\",\"description\":\"{\\\"productId\\\":1,\\\"applicationId\\\":1}\"}","price":{"amount":0,"asset_id":"1.3.0"},"paid_price_before_exchange":{"amount":100000000,"asset_id":"1.3.0"},"paid_price_after_exchange":{"amount":100000000,"asset_id":"1.3.0"},"seeders_answered":[],"size":1,"rating":"18446744073709551615","comment":"","expiration_time":"2018-05-31T11:34:40","pubKey":{"s":"5182545488318095000498180568539728214545472470974958338942426759510121851708530625921436777555517288139787965253547588340803542762268721656138876002028437."},"key_particles":[],"expired":false,"delivered":true,"expiration_or_delivery_time":"2018-05-30T11:34:40","rated_or_commented":false,"created":"2018-05-21T09:37:20","region_code_from":1}]}"""
    )

    val test = api.purchaseApi.getAllHistory(account)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get purchase for account and uri`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"get_buying_by_consumer_URI",["1.2.34","http://alax.io/?scheme=alax%3A%2F%2F1%2F2&version=bbc8a9c3-1bcd-48a6-820d-e5a60c29cf56"]],"id":1}""",
            """{"id":1,"result":{"id":"2.12.3","consumer":"1.2.34","URI":"http://alax.io/?scheme=alax%3A%2F%2F1%2F2&version=bbc8a9c3-1bcd-48a6-820d-e5a60c29cf56","synopsis":"{\"content_type_id\":\"1.5.5.0\",\"title\":\"Product 2\",\"description\":\"{\\\"productId\\\":2,\\\"applicationId\\\":1}\"}","price":{"amount":0,"asset_id":"1.3.0"},"paid_price_before_exchange":{"amount":100000000,"asset_id":"1.3.0"},"paid_price_after_exchange":{"amount":100000000,"asset_id":"1.3.0"},"seeders_answered":[],"size":1,"rating":"18446744073709551615","comment":"","expiration_time":"2018-05-16T08:59:10","pubKey":{"s":"5182545488318095000498180568539728214545472470974958338942426759510121851708530625921436777555517288139787965253547588340803542762268721656138876002028437."},"key_particles":[],"expired":false,"delivered":true,"expiration_or_delivery_time":"2018-05-15T08:59:10","rated_or_commented":false,"created":"2018-04-26T15:34:50","region_code_from":1}}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":{"id":"2.12.3","consumer":"1.2.34","URI":"http://alax.io/?scheme=alax%3A%2F%2F1%2F2&version=bbc8a9c3-1bcd-48a6-820d-e5a60c29cf56","synopsis":"{\"content_type_id\":\"1.5.5.0\",\"title\":\"Product 2\",\"description\":\"{\\\"productId\\\":2,\\\"applicationId\\\":1}\"}","price":{"amount":0,"asset_id":"1.3.0"},"paid_price_before_exchange":{"amount":100000000,"asset_id":"1.3.0"},"paid_price_after_exchange":{"amount":100000000,"asset_id":"1.3.0"},"seeders_answered":[],"size":1,"rating":"18446744073709551615","comment":"","expiration_time":"2018-05-16T08:59:10","pubKey":{"s":"5182545488318095000498180568539728214545472470974958338942426759510121851708530625921436777555517288139787965253547588340803542762268721656138876002028437."},"key_particles":[],"expired":false,"delivered":true,"expiration_or_delivery_time":"2018-05-15T08:59:10","rated_or_commented":false,"created":"2018-04-26T15:34:50","region_code_from":1}}"""
    )

    val test = api.purchaseApi.get(account, "http://alax.io/?scheme=alax%3A%2F%2F1%2F2&version=bbc8a9c3-1bcd-48a6-820d-e5a60c29cf56")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should search purchases`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"get_buying_objects_by_consumer",["1.2.34","-purchased","1.0.0","new",100]],"id":1}""",
            """{"id":1,"result":[{"id":"2.12.17","consumer":"1.2.34","URI":"http://alax.io/?scheme=alax%3A%2F%2F1%2F1&version=949da412-18bd-4b8d-acba-e8fd7a594d88","synopsis":"{\"content_type_id\":\"1.5.5.0\",\"title\":\"New product 2\",\"description\":\"{\\\"productId\\\":1,\\\"applicationId\\\":1}\"}","price":{"amount":0,"asset_id":"1.3.0"},"paid_price_before_exchange":{"amount":100000000,"asset_id":"1.3.0"},"paid_price_after_exchange":{"amount":100000000,"asset_id":"1.3.0"},"seeders_answered":[],"size":1,"rating":"18446744073709551615","comment":"","expiration_time":"2018-05-31T11:34:40","pubKey":{"s":"5182545488318095000498180568539728214545472470974958338942426759510121851708530625921436777555517288139787965253547588340803542762268721656138876002028437."},"key_particles":[],"expired":false,"delivered":true,"expiration_or_delivery_time":"2018-05-30T11:34:40","rated_or_commented":false,"created":"2018-05-21T09:37:20","region_code_from":1},{"id":"2.12.14","consumer":"1.2.34","URI":"http://alax.io/?scheme=alax%3A%2F%2F1%2F1&version=b711dc9b-3627-4f37-93f3-6f6f3137bcca","synopsis":"{\"content_type_id\":\"1.5.5.0\",\"title\":\"New product 2\",\"description\":\"{\\\"productId\\\":1,\\\"applicationId\\\":1}\"}","price":{"amount":0,"asset_id":"1.3.0"},"paid_price_before_exchange":{"amount":100000000,"asset_id":"1.3.0"},"paid_price_after_exchange":{"amount":100000000,"asset_id":"1.3.0"},"seeders_answered":[],"size":1,"rating":"18446744073709551615","comment":"","expiration_time":"2018-05-31T09:21:10","pubKey":{"s":"5182545488318095000498180568539728214545472470974958338942426759510121851708530625921436777555517288139787965253547588340803542762268721656138876002028437."},"key_particles":[],"expired":false,"delivered":true,"expiration_or_delivery_time":"2018-05-30T09:21:10","rated_or_commented":false,"created":"2018-05-21T09:29:15","region_code_from":1}]}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":[{"id":"2.12.17","consumer":"1.2.34","URI":"http://alax.io/?scheme=alax%3A%2F%2F1%2F1&version=949da412-18bd-4b8d-acba-e8fd7a594d88","synopsis":"{\"content_type_id\":\"1.5.5.0\",\"title\":\"New product 2\",\"description\":\"{\\\"productId\\\":1,\\\"applicationId\\\":1}\"}","price":{"amount":0,"asset_id":"1.3.0"},"paid_price_before_exchange":{"amount":100000000,"asset_id":"1.3.0"},"paid_price_after_exchange":{"amount":100000000,"asset_id":"1.3.0"},"seeders_answered":[],"size":1,"rating":"18446744073709551615","comment":"","expiration_time":"2018-05-31T11:34:40","pubKey":{"s":"5182545488318095000498180568539728214545472470974958338942426759510121851708530625921436777555517288139787965253547588340803542762268721656138876002028437."},"key_particles":[],"expired":false,"delivered":true,"expiration_or_delivery_time":"2018-05-30T11:34:40","rated_or_commented":false,"created":"2018-05-21T09:37:20","region_code_from":1},{"id":"2.12.14","consumer":"1.2.34","URI":"http://alax.io/?scheme=alax%3A%2F%2F1%2F1&version=b711dc9b-3627-4f37-93f3-6f6f3137bcca","synopsis":"{\"content_type_id\":\"1.5.5.0\",\"title\":\"New product 2\",\"description\":\"{\\\"productId\\\":1,\\\"applicationId\\\":1}\"}","price":{"amount":0,"asset_id":"1.3.0"},"paid_price_before_exchange":{"amount":100000000,"asset_id":"1.3.0"},"paid_price_after_exchange":{"amount":100000000,"asset_id":"1.3.0"},"seeders_answered":[],"size":1,"rating":"18446744073709551615","comment":"","expiration_time":"2018-05-31T09:21:10","pubKey":{"s":"5182545488318095000498180568539728214545472470974958338942426759510121851708530625921436777555517288139787965253547588340803542762268721656138876002028437."},"key_particles":[],"expired":false,"delivered":true,"expiration_or_delivery_time":"2018-05-30T09:21:10","rated_or_commented":false,"created":"2018-05-21T09:29:15","region_code_from":1}]}"""
    )

    val test = api.purchaseApi.findAll(account, "new")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should search feedback`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"search_feedback",[null,"","1.0.0",100]],"id":1}""",
            """{"id":1,"result":[]}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":[]}"""
    )

    val test = api.purchaseApi.findAllForFeedback("")
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }
}