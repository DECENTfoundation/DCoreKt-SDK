package ch.decent.sdk.net

import ch.decent.sdk.DCoreClient
import ch.decent.sdk.Helpers
import ch.decent.sdk.TimeOutTest
import ch.decent.sdk.crypto.Address
import ch.decent.sdk.crypto.ECKeyPair
import ch.decent.sdk.crypto.address
import ch.decent.sdk.model.Account
import ch.decent.sdk.model.AccountObjectId
import ch.decent.sdk.model.AssetAmount
import ch.decent.sdk.model.AssetOptions
import ch.decent.sdk.model.ExchangeRate
import ch.decent.sdk.model.Fee
import ch.decent.sdk.model.Memo
import ch.decent.sdk.model.MessagePayload
import ch.decent.sdk.model.MessagePayloadReceiver
import ch.decent.sdk.model.MinerObjectId
import ch.decent.sdk.model.MonitoredAssetOptions
import ch.decent.sdk.model.NftApple
import ch.decent.sdk.model.NftModel
import ch.decent.sdk.model.NftOptions
import ch.decent.sdk.model.ProposalObjectId
import ch.decent.sdk.model.PubKey
import ch.decent.sdk.model.RegionalPrice
import ch.decent.sdk.model.Regions
import ch.decent.sdk.model.Synopsis
import ch.decent.sdk.model.Transaction
import ch.decent.sdk.model.WithdrawPermissionObjectId
import ch.decent.sdk.model.operation.AccountCreateOperation
import ch.decent.sdk.model.operation.AccountUpdateOperation
import ch.decent.sdk.model.operation.AddOrUpdateContentOperation
import ch.decent.sdk.model.operation.AssetClaimFeesOperation
import ch.decent.sdk.model.operation.AssetCreateOperation
import ch.decent.sdk.model.operation.AssetFundPoolsOperation
import ch.decent.sdk.model.operation.AssetIssueOperation
import ch.decent.sdk.model.operation.AssetReserveOperation
import ch.decent.sdk.model.operation.BaseOperation
import ch.decent.sdk.model.operation.LeaveRatingAndCommentOperation
import ch.decent.sdk.model.operation.MinerCreateOperation
import ch.decent.sdk.model.operation.MinerUpdateOperation
import ch.decent.sdk.model.operation.NftCreateOperation
import ch.decent.sdk.model.operation.NftIssueOperation
import ch.decent.sdk.model.operation.NftTransferOperation
import ch.decent.sdk.model.operation.NftUpdateDataOperation
import ch.decent.sdk.model.operation.NftUpdateOperation
import ch.decent.sdk.model.operation.OperationType
import ch.decent.sdk.model.operation.ProposalCreateOperation
import ch.decent.sdk.model.operation.ProposalDeleteOperation
import ch.decent.sdk.model.operation.ProposalUpdateOperation
import ch.decent.sdk.model.operation.PurchaseContentOperation
import ch.decent.sdk.model.operation.RemoveContentOperation
import ch.decent.sdk.model.operation.SendMessageOperation
import ch.decent.sdk.model.operation.TransferOperation
import ch.decent.sdk.model.operation.WithdrawalClaimOperation
import ch.decent.sdk.model.operation.WithdrawalCreateOperation
import ch.decent.sdk.model.operation.WithdrawalDeleteOperation
import ch.decent.sdk.model.operation.WithdrawalUpdateOperation
import ch.decent.sdk.model.toObjectId
import ch.decent.sdk.net.serialization.Serializer
import ch.decent.sdk.print
import ch.decent.sdk.utils.hex
import org.amshove.kluent.`should be equal to`
import org.junit.Test
import org.slf4j.LoggerFactory
import org.threeten.bp.LocalDateTime
import java.math.BigInteger
import java.security.SecureRandom

class SerializerTest : TimeOutTest() {

  @Test fun `serialize transfer`() {
    val bytes = "278813000000000000001e1f000000000002018096980000000000000102c03f8e840c1699fd7808c2bb858e249c688c5be8acf0a0c1c484ab0cfb27f0a802e0ced80260630f641f61f6d6959f32b5c43b1a38be55666b98abfe8bafcc556b5521e507000000001086d54a9e1f8fc6e5319dbae0b087b6cc00"
    val priv = ECKeyPair.fromBase58("5Jd7zdvxXYNdUfnEXt5XokrE3zwJSs734yQ36a1YaqioRTGGLtn")
    val address = Address.decode("DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP")

    val op = TransferOperation(
        "1.2.30".toObjectId(),
        "1.2.31".toObjectId(),
        AssetAmount(10000000),
        Memo("hello memo", priv, address, BigInteger("132456789")),
        Fee(amount = 5000)
    )

    Serializer.serialize(op).hex() `should be equal to` bytes
  }

  @Test fun `serialize buy content`() {
    val bytes = "1500000000000000000033697066733a516d61625537574e485a77636f6a674a724352774b68734a7666696e4c54397866666e4b4d4673726343474746501e809698000000000000020000009b01393130383430393539353932363431303631383538343930393638383830363132333831353335303037303838393138373132303036303039303236323639383330353937313939383532363530313030393830343535343035383735383238393637363235373630393334303934393631353931343538333133383834313435363939373639383133333939313030343939313437333637302e"

    val op = PurchaseContentOperation(
        "ipfs:QmabU7WNHZwcojgJrCRwKhsJvfinLT9xffnKMFsrcCGGFP",
        "1.2.30".toObjectId(),
        AssetAmount(10000000),
        PubKey("9108409595926410618584909688806123815350070889187120060090262698305971998526501009804554058758289676257609340949615914583138841456997698133991004991473670")
    )

    Serializer.serialize(op).hex() `should be equal to` bytes
  }

  @Test fun `serialize buy content encoded uri`() {
    val bytes = "150000000000000000006c687474703a2f2f616c61782e696f2f3f736368656d653d616c6c6178253341253246253246636f6d2e6578616d706c652e68656c6c6f776f726c64253341332676657273696f6e3d37663438623234652d386162392d343831302d386239612d3933363134366164366164321e00e1f5050000000000020000009b01353138323534353438383331383039353030303439383138303536383533393732383231343534353437323437303937343935383333383934323432363735393531303132313835313730383533303632353932313433363737373535353531373238383133393738373936353235333534373538383334303830333534323736323236383732313635363133383837363030323032383433372e"

    val op = PurchaseContentOperation(
        "http://alax.io/?scheme=allax%3A%2F%2Fcom.example.helloworld%3A3&version=7f48b24e-8ab9-4810-8b9a-936146ad6ad2",
        "1.2.30".toObjectId(),
        AssetAmount(100000000),
        PubKey("5182545488318095000498180568539728214545472470974958338942426759510121851708530625921436777555517288139787965253547588340803542762268721656138876002028437")
    )

    Serializer.serialize(op).hex() `should be equal to` bytes
  }

  @Test fun `serialize vote`() {
    val bytes = "0220a1070000000000002200000102c03f8e840c1699fd7808c2bb858e249c688c5be8acf0a0c1c484ab0cfb27f0a803000002000500000008000000000000000000000000000000000000"

    val json = """
      {
  "id": "1.2.34",
  "registrar": "1.2.15",
  "name": "u961279ec8b7ae7bd62f304f7c1c3d345",
  "owner": {
    "weight_threshold": 1,
    "account_auths": [],
    "key_auths": [
      [
        "DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz",
        1
      ]
    ]
  },
  "active": {
    "weight_threshold": 1,
    "account_auths": [],
    "key_auths": [
      [
        "DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz",
        1
      ]
    ]
  },
  "options": {
    "memo_key": "DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz",
    "voting_account": "1.2.3",
    "num_miner": 0,
    "votes": [
      "0:5",
      "0:8"
    ],
    "extensions": [],
    "allow_subscription": false,
    "price_per_subscribe": {
      "amount": 0,
      "asset_id": "1.3.0"
    },
    "subscription_period": 0
  },
  "rights_to_publish": {
    "is_publishing_manager": false,
    "publishing_rights_received": [],
    "publishing_rights_forwarded": []
  },
  "statistics": "2.5.34",
  "top_n_control_flags": 0
}
      """
    val account = DCoreClient.gsonBuilder.create().fromJson(json, Account::class.java)

    val op = AccountUpdateOperation(
        account.id,
        null,
        null,
        account.options,
        Fee(amount = 500000)
    )

    Serializer.serialize(op).hex() `should be equal to` bytes
  }

  @Test fun `serialize create account`() {
    val bytes = "010000000000000000001b086d696b656565656501000000000102a01c045821676cfc191832ad22cc5c9ade0ea1760131c87ff2dd3fed2f13dd33010001000000000102a01c045821676cfc191832ad22cc5c9ade0ea1760131c87ff2dd3fed2f13dd33010002a01c045821676cfc191832ad22cc5c9ade0ea1760131c87ff2dd3fed2f13dd330300000000000000000000000000000000000000"

    val op = AccountCreateOperation("1.2.27".toObjectId(), "mikeeeee", "DCT6718kUCCksnkeYD1YySWkXb1VLpzjkFfHHMirCRPexp5gDPJLU".address())

    Serializer.serialize(op).hex() `should be equal to` bytes
  }

  @Test fun `should serialize add new content or update operation`() {
    val expected = "140000000000000000000100000000000000220016687474703a2f2f68656c6c6f2e696f2f776f726c6432000000000101000000e80300000000000000222222222222222222222222222222222222222200007238ed5c0000000000000000004c7b227469746c65223a2247616d65205469746c65222c226465736372697074696f6e223a224465736372697074696f6e222c22636f6e74656e745f747970655f6964223a22312e352e35227d00"

    val op = AddOrUpdateContentOperation(
        author = "1.2.34".toObjectId(),
        uri = "http://hello.io/world2",
        price = listOf(RegionalPrice(AssetAmount(1000), Regions.NONE.id)),
        expiration = LocalDateTime.parse("2019-05-28T13:32:34"),
        synopsis = Synopsis("Game Title", "Description", "1.5.5").json,
        hash = "2222222222222222222222222222222222222222"
    )

    Serializer.serialize(op).hex() `should be equal to` expected
  }

  @Test fun `should serialize remove existing content operation`() {
    val expected = "200000000000000000002216687474703a2f2f68656c6c6f2e696f2f776f726c6432"

    val op = RemoveContentOperation(
        "1.2.34".toObjectId(),
        "http://hello.io/world2"
    )

    Serializer.serialize(op).hex() `should be equal to` expected
  }

  @Test fun `should serialize transfer op transaction`() {
    val expected = "3e322ef4e4170c88615b012720a10700000000000022230000000000020160e3160000000000000102c03f8e840c1699fd7808c2bb858e249c688c5be8acf0a0c1c484ab0cfb27f0a802e0ced80260630f641f61f6d6959f32b5c43b1a38be55666b98abfe8bafcc556b002ea2558d64350a204bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a70000"
    val gson = DCoreClient.gsonBuilder.create()
    val rawOp = """{"from":"1.2.34","to":"1.2.35","amount":{"amount":1500000,"asset_id":"1.3.0"},"memo":{"from":"DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz","to":"DCT6bVmimtYSvWQtwdrkVVQGHkVsTJZVKtBiUqf4YmJnrJPnk89QP","message":"4bc2a1ee670302ceddb897c2d351fa0496ff089c934e35e030f8ae4f3f9397a7","nonce":735604672334802432},"fee":{"amount":500000,"asset_id":"1.3.0"}}"""
    val op = gson.fromJson(rawOp, TransferOperation::class.java)
    op.type = OperationType.TRANSFER2_OPERATION

    val trx = Transaction(
        listOf(op),
        LocalDateTime.parse("2018-08-01T10:14:36"),
        12862,
        400880686,
        "17401602b201b3c45a3ad98afc6fb458f91f519bd30d1058adf6f2bed66376bc")

    Serializer.serialize(trx).hex() `should be equal to` expected
  }


  @Test fun `serialize send message op`() {
    val bytes = "1222a1070000000000001b011b01009e027b2266726f6d223a22312e322e3237222c227265636569766572735f64617461223a5b7b22746f223a22312e322e3238222c2264617461223a2263646132383562326165653737303435343233333661633236626339613361666333393735363562616531353561316535663766353131393338326434303633222c227075625f746f223a224443543550776353696967665450547775626164743835656e784d464331385474566f746933676e54624737544e39663952334670222c226e6f6e6365223a343736343232313338393335393932363237327d5d2c227075625f66726f6d223a2244435436546a4c6872387545537667747872625775584e414e337663717a424d7735657945757033504d694432676e567865755462227d"

    val private = "5Hxwqx6JJUBYWjQNt8DomTNJ6r6YK8wDJym4CMAH1zGctFyQtzt"
    val public = "DCT6TjLhr8uESvgtxrbWuXNAN3vcqzBMw5eyEup3PMiD2gnVxeuTb".address()
    val public2 = "DCT5PwcSiigfTPTwubadt85enxMFC18TtVoti3gnTbG7TN9f9R3Fp".address()

    val keyPair = ECKeyPair.fromBase58(private)
    val memo = Memo("hello messaging api", keyPair, public2, 4764221389359926272.toBigInteger())
    val payloadReceiver = MessagePayloadReceiver("1.2.28".toObjectId(), memo.message, public2, memo.nonce)
    val payload = MessagePayload("1.2.27".toObjectId(), listOf(payloadReceiver), public)
    val gson = DCoreClient.gsonBuilder.create()
    val op = SendMessageOperation(gson, payload, "1.2.27".toObjectId(), Fee(amount = 500002))

    Serializer.serialize(op).hex() `should be equal to` bytes
  }

  @Test fun `should serialize rate and comment operation`() {
    val expected = "1600000000000000000033697066733a516d57426f52425975787a48356138643367737352624d53357363733666714c4b676170426671564e554655745a1b07636f6d6d656e740100000000000000"

    val op = LeaveRatingAndCommentOperation(
        "ipfs:QmWBoRBYuxzH5a8d3gssRbMS5scs6fqLKgapBfqVNUFUtZ", "1.2.27".toObjectId(), 1, "comment"
    )

    Serializer.serialize(op).hex() `should be equal to` expected
  }

  @Test fun `should serialize create asset operation`() {
    val expected = "0320a1070000000000001b0353444b010968656c6c6f20617069fad456864c011a000100000000000000000100000000000000e70701010100000100"

    val ex = ExchangeRate(AssetAmount(1), AssetAmount(1, "1.3.999".toObjectId()))
    val op = AssetCreateOperation("1.2.27".toObjectId(), "SDK", 1, "hello api", AssetOptions(ex))
    op.fee = AssetAmount(500000)

    Serializer.serialize(op).hex() `should be equal to` expected
  }

  @Test fun `should serialize create monitored asset operation`() {
    val expected = "03a086010000000000001b0453444b4d041368656c6c6f20617069206d6f6e69746f7265640000000000000000000000000000000000000000000000000000010101000100000000000000000000000000000000000000480fb65c80510100010100"

    val opt = MonitoredAssetOptions(currentFeedPublicationTime = LocalDateTime.parse("2019-04-16T17:22:16"))
    val op = AssetCreateOperation("1.2.27".toObjectId(), "SDKM", 4, "hello api monitored", AssetOptions(ExchangeRate.EMPTY, 0), opt)
    op.fee = AssetAmount(100000)
    Serializer.serialize(op).hex() `should be equal to` expected
  }

  @Test fun `should serialize issue asset operation`() {
    val expected = "040a00000000000000001b0a00000000000000241b0000"

    val op = AssetIssueOperation("1.2.27".toObjectId(), AssetAmount(10, "1.3.36".toObjectId()), "1.2.27".toObjectId())
    op.fee = AssetAmount(10)
    Serializer.serialize(op).hex() `should be equal to` expected
  }

  @Test fun `should serialize reserve asset operation`() {
    val expected = "220a00000000000000001b0a000000000000002400"

    val op = AssetReserveOperation("1.2.27".toObjectId(), AssetAmount(10, "1.3.36".toObjectId()))
    op.fee = AssetAmount(10)
    Serializer.serialize(op).hex() `should be equal to` expected
  }

  @Test fun `should serialize fund asset pool operation`() {
    val expected = "210a00000000000000001b0a00000000000000240a000000000000000000"

    val op = AssetFundPoolsOperation("1.2.27".toObjectId(), AssetAmount(10, "1.3.36".toObjectId()), AssetAmount(10))
    op.fee = AssetAmount(10)
    Serializer.serialize(op).hex() `should be equal to` expected
  }

  @Test fun `should serialize claim asset pool operation`() {
    val expected = "230a00000000000000001b0a00000000000000240a000000000000000000"

    val op = AssetClaimFeesOperation("1.2.27".toObjectId(), AssetAmount(10, "1.3.36".toObjectId()), AssetAmount(10))
    op.fee = AssetAmount(10)
    Serializer.serialize(op).hex() `should be equal to` expected
  }

  @Test fun `should serialize nft create operation`() {
    val expected = "2920a1070000000000000953444b2e4150504c451b640000000008616e206170706c65030000000000000000000100000000000000010473697a6501000000000000000000000000000000000105636f6c6f7200030000000000000002000000000000000105656174656e0100"

    val op = NftCreateOperation(
        "SDK.APPLE",
        NftOptions("1.2.27".toObjectId(), 100, false, "an apple"),
        NftModel.createDefinitions(NftApple::class),
        true
    )
    op.fee = AssetAmount(500000)
    Serializer.serialize(op).hex() `should be equal to` expected
  }

  @Test fun `should serialize nft update operation`() {
    val expected = "2a20a1070000000000001b011b6400000001046465736300"
    val op = NftUpdateOperation(
        "1.2.27".toObjectId(),
        "1.10.1".toObjectId(),
        NftOptions("1.2.27".toObjectId(), 100, true, "desc"),
        Fee(amount = 500000)
    )

    Serializer.serialize(op).hex() `should be equal to` expected
  }

  @Test fun `should serialize nft issue operation`() {
    val expected = "2b20a1070000000000001b1c0103020700000000000000050372656404000000"
    val op = NftIssueOperation(
        "1.2.27".toObjectId(),
        "1.10.1".toObjectId(),
        "1.2.28".toObjectId(),
        NftApple(7, "red", false).values(),
        fee = Fee(amount = 500000)
    )

    Serializer.serialize(op).hex() `should be equal to` expected
  }

  @Test fun `should serialize nft transfer operation`() {
    val expected = "2c20a1070000000000001b1c010000"
    val op = NftTransferOperation(
        "1.2.27".toObjectId(),
        "1.2.28".toObjectId(),
        "1.11.1".toObjectId(),
        fee = Fee(amount = 500000)
    )

    Serializer.serialize(op).hex() `should be equal to` expected
  }

  @Test fun `should serialize nft update data operation`() {
    val expected = "2d20a1070000000000001b01030473697a6502010000000000000005636f6c6f72050372656405656174656e040000"
    val op = NftUpdateDataOperation(
        "1.2.27".toObjectId(),
        "1.11.1".toObjectId(),
        mutableMapOf("size" to 1, "color" to "red", "eaten" to false),
        fee = Fee(amount = 500000)
    )

    Serializer.serialize(op).hex() `should be equal to` expected
  }

  @Test fun `should serialize miner create operation`() {
    val expected = "0620a1070000000000001b11687474703a2f2f676f6f676c652e636f6d0242e0431837a5843252a0ecfab9565bdb20bdb0fc4c88398455f64589fdc7b93d"
    val op = MinerCreateOperation(
        AccountObjectId(27),
        "http://google.com",
        "DCT5PwcSiigfTPTwubadt85enxMFC18TtVoti3gnTbG7TN9f9R3Fp".address(),
        fee = Fee(amount = 500000)
    )

    Serializer.serialize(op).hex() `should be equal to` expected
  }

  @Test fun `should serialize miner update operation`() {
    val expected = "0720a1070000000000001b1b0111687474703a2f2f676f6f676c652e636f6d010242e0431837a5843252a0ecfab9565bdb20bdb0fc4c88398455f64589fdc7b93d"
    val op = MinerUpdateOperation(
        MinerObjectId(27),
        AccountObjectId(27),
        "http://google.com",
        "DCT5PwcSiigfTPTwubadt85enxMFC18TtVoti3gnTbG7TN9f9R3Fp".address(),
        fee = Fee(amount = 500000)
    )

    Serializer.serialize(op).hex() `should be equal to` expected
  }

  @Test fun `should serialize proposal create operation`() {
    val expected = "0920a1070000000000001be39b775d000000"
    val op = ProposalCreateOperation(
        AccountObjectId(27),
        emptyList(),
        LocalDateTime.parse("2019-09-10T12:49:39.220"),
        fee = Fee(amount = 500000)
    )

    Serializer.serialize(op).hex() `should be equal to` expected
  }

  @Test fun `should serialize proposal update operation`() {
    val expected = "0a20a1070000000000001b0101010102010301040102cf2c986e78776c21e5a75d42dd858dfe8ef06cf663ee0e8363db89ad5999d84f010242e0431837a5843252a0ecfab9565bdb20bdb0fc4c88398455f64589fdc7b93d00"
    val op = ProposalUpdateOperation(
        AccountObjectId(27),
        ProposalObjectId(1),
        listOf(AccountObjectId(1)),
        listOf(AccountObjectId(2)),
        listOf(AccountObjectId(3)),
        listOf(AccountObjectId(4)),
        listOf(Helpers.public.address()),
        listOf(Helpers.public2.address()),
        fee = Fee(amount = 500000)
    )

    Serializer.serialize(op).hex() `should be equal to` expected
  }

  @Test fun `should serialize proposal delete operation`() {
    val expected = "0b20a1070000000000001b010100"
    val op = ProposalDeleteOperation(
        AccountObjectId(27),
        ProposalObjectId(1),
        true,
        fee = Fee(amount = 500000)
    )

    Serializer.serialize(op).hex() `should be equal to` expected
  }

  @Test fun `should serialize withdrawal create operation`() {
    val expected = "0c20a1070000000000001b1c64000000000000000064000000050000001b3f7a5d"
    val op = WithdrawalCreateOperation(
        AccountObjectId(27),
        AccountObjectId(28),
        AssetAmount(100),
        100,
        5,
        LocalDateTime.parse("2019-09-12T12:50:35.550"),
        fee = Fee(amount = 500000)
    )

    Serializer.serialize(op).hex() `should be equal to` expected
  }

  @Test fun `should serialize withdrawal update operation`() {
    val expected = "0d20a1070000000000001b1c00640000000000000000640000001b3f7a5d05000000"
    val op = WithdrawalUpdateOperation(
        WithdrawPermissionObjectId(),
        AccountObjectId(27),
        AccountObjectId(28),
        AssetAmount(100),
        100,
        5,
        LocalDateTime.parse("2019-09-12T12:50:35.550"),
        fee = Fee(amount = 500000)
    )

    Serializer.serialize(op).hex() `should be equal to` expected
  }

  @Test fun `should serialize withdrawal claim operation`() {
    val expected = "0e20a107000000000000001b1c64000000000000000000"
    val op = WithdrawalClaimOperation(
        WithdrawPermissionObjectId(),
        AccountObjectId(27),
        AccountObjectId(28),
        AssetAmount(100),
        fee = Fee(amount = 500000)
    )

    Serializer.serialize(op).hex() `should be equal to` expected
  }

  @Test fun `should serialize withdrawal delete operation`() {
    val expected = "0f20a1070000000000001b1c00"
    val op = WithdrawalDeleteOperation(
        WithdrawPermissionObjectId(),
        AccountObjectId(27),
        AccountObjectId(28),
        fee = Fee(amount = 500000)
    )

    Serializer.serialize(op).hex() `should be equal to` expected
  }

  private fun BaseOperation.expected() {
    val logger = LoggerFactory.getLogger("serializer")
    val api = DCoreClient.createForWebSocket(Helpers.client(logger), Helpers.dockerWs, logger)
    api.transactionApi.createTransaction(this)
        .flatMap { api.transactionApi.getHexDump(it) }
        .blockingGet()
        .drop(22).dropLast(4)
        .print()
  }

}
