package ch.decent.sdk.api

import ch.decent.sdk.model.toChainObject
import ch.decent.sdk.print
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class SeedersApiTest(channel: Channel) : BaseApiTest(channel) {
//  override val useMock: Boolean = false

  @Test fun `should list seeders by price`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"list_seeders_by_price",[100]],"id":1}""",
            """{"id":1,"result":[]}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":[]}"""
    )

    val test = api.seedersApi.listSeedersByPrice()
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should list seeders by upload`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"list_seeders_by_upload",[100]],"id":1}""",
            """{"id":1,"result":[{"id":"2.14.0","seeder":"1.2.16","free_space":1022,"price":{"amount":1,"asset_id":"1.3.0"},"expiration":"2018-09-14T02:10:05","pubKey":{"s":"108509137992084552766842257584642929445130808368600055288928130756106214148863141200188299504000640159872636359336882924163527129138321742300857400054934."},"ipfs_ID":"Qmd1WE8qhNDhwbZwRn4J6UJwAoBCgxX7iLHM856pkvDFdJ","stats":"2.16.0","rating":0,"region_code":""},{"id":"2.14.1","seeder":"1.2.90","free_space":1018,"price":{"amount":1,"asset_id":"1.3.0"},"expiration":"2018-07-31T04:16:25","pubKey":{"s":"8490319717792401336022711903211688203776011372958543603431635388400597658916629399144065413122027733188246528373794568719741969521921155180202961044843254."},"ipfs_ID":"QmVotciYd21wM5fbQzCA4S9nG5hqumkcbfjvHJtLnsAWoF","stats":"2.16.1","rating":0,"region_code":""},{"id":"2.14.2","seeder":"1.2.91","free_space":1022,"price":{"amount":1,"asset_id":"1.3.0"},"expiration":"2018-07-23T11:18:05","pubKey":{"s":"2303777410538172886271756794595982408449215299184180786707946414344825115577210309329094182704710373375176575047946743652593825273038473592524248046488588."},"ipfs_ID":"QmcTKsArf7aMicW1zNvMRXEJHbpEdfFF2wZi6HK7UdEYvs","stats":"2.16.2","rating":0,"region_code":""},{"id":"2.14.3","seeder":"1.2.85","free_space":99,"price":{"amount":70000000,"asset_id":"1.3.0"},"expiration":"2018-07-07T11:26:40","pubKey":{"s":"3743207398576779808066945562729802450508896326744651113099676580067412967050373989300299175261482010131674378978235327498648053632565557861526621363621025."},"ipfs_ID":"QmU4zusEFksdUoEnqk57LND5fFFdDjzDTCBGJsYahN56Tt","stats":"2.16.3","rating":0,"region_code":""}]}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":[{"id":"2.14.0","seeder":"1.2.16","free_space":1022,"price":{"amount":1,"asset_id":"1.3.0"},"expiration":"2018-09-14T02:10:05","pubKey":{"s":"108509137992084552766842257584642929445130808368600055288928130756106214148863141200188299504000640159872636359336882924163527129138321742300857400054934."},"ipfs_ID":"Qmd1WE8qhNDhwbZwRn4J6UJwAoBCgxX7iLHM856pkvDFdJ","stats":"2.16.0","rating":0,"region_code":""},{"id":"2.14.1","seeder":"1.2.90","free_space":1018,"price":{"amount":1,"asset_id":"1.3.0"},"expiration":"2018-07-31T04:16:25","pubKey":{"s":"8490319717792401336022711903211688203776011372958543603431635388400597658916629399144065413122027733188246528373794568719741969521921155180202961044843254."},"ipfs_ID":"QmVotciYd21wM5fbQzCA4S9nG5hqumkcbfjvHJtLnsAWoF","stats":"2.16.1","rating":0,"region_code":""},{"id":"2.14.2","seeder":"1.2.91","free_space":1022,"price":{"amount":1,"asset_id":"1.3.0"},"expiration":"2018-07-23T11:18:05","pubKey":{"s":"2303777410538172886271756794595982408449215299184180786707946414344825115577210309329094182704710373375176575047946743652593825273038473592524248046488588."},"ipfs_ID":"QmcTKsArf7aMicW1zNvMRXEJHbpEdfFF2wZi6HK7UdEYvs","stats":"2.16.2","rating":0,"region_code":""},{"id":"2.14.3","seeder":"1.2.85","free_space":99,"price":{"amount":70000000,"asset_id":"1.3.0"},"expiration":"2018-07-07T11:26:40","pubKey":{"s":"3743207398576779808066945562729802450508896326744651113099676580067412967050373989300299175261482010131674378978235327498648053632565557861526621363621025."},"ipfs_ID":"QmU4zusEFksdUoEnqk57LND5fFFdDjzDTCBGJsYahN56Tt","stats":"2.16.3","rating":0,"region_code":""}]}"""
    )

    val test = api.seedersApi.listSeedersByUpload()
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should list seeders by region`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"list_seeders_by_region",["default"]],"id":1}""",
            """{"id":1,"result":[]}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":[]}"""
    )

    val test = api.seedersApi.listSeedersByRegion()
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should list seeders by rating`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"list_seeders_by_rating",[10]],"id":1}""",
            """{"id":1,"result":[]}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":[]}"""
    )

    val test = api.seedersApi.listSeedersByRating(10)
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }

  @Test fun `should get seeder by id`() {
    mockWebSocket
        .enqueue(
            """{"method":"call","params":[0,"get_seeder",["1.2.16"]],"id":1}""",
            """{"id":1,"result":{"id":"2.14.0","seeder":"1.2.16","free_space":1022,"price":{"amount":1,"asset_id":"1.3.0"},"expiration":"2018-09-14T02:10:05","pubKey":{"s":"108509137992084552766842257584642929445130808368600055288928130756106214148863141200188299504000640159872636359336882924163527129138321742300857400054934."},"ipfs_ID":"Qmd1WE8qhNDhwbZwRn4J6UJwAoBCgxX7iLHM856pkvDFdJ","stats":"2.16.0","rating":0,"region_code":""}}"""
        )

    mockHttp.enqueue(
        """{"id":1,"result":{"id":"2.14.0","seeder":"1.2.16","free_space":1022,"price":{"amount":1,"asset_id":"1.3.0"},"expiration":"2018-09-14T02:10:05","pubKey":{"s":"108509137992084552766842257584642929445130808368600055288928130756106214148863141200188299504000640159872636359336882924163527129138321742300857400054934."},"ipfs_ID":"Qmd1WE8qhNDhwbZwRn4J6UJwAoBCgxX7iLHM856pkvDFdJ","stats":"2.16.0","rating":0,"region_code":""}}"""
    )

    val test = api.seedersApi.getSeeder("1.2.16".toChainObject())
        .subscribeOn(Schedulers.newThread())
        .test()

    test.awaitTerminalEvent()
    test.assertComplete()
        .assertNoErrors()
  }
}