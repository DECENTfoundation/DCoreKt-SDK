package ch.decent.sdk.java;

import ch.decent.sdk.DCoreClient;
import ch.decent.sdk.DCoreConstants;
import ch.decent.sdk.Helpers;
import ch.decent.sdk.api.rx.DCoreApi;
import ch.decent.sdk.crypto.Address;
import ch.decent.sdk.crypto.Credentials;
import ch.decent.sdk.crypto.ECKeyPair;
import ch.decent.sdk.model.*;
import ch.decent.sdk.model.operation.BaseOperation;
import ch.decent.sdk.model.operation.EmptyOperation;
import ch.decent.sdk.model.operation.OperationType;
import ch.decent.sdk.model.operation.TransferOperation;
import ch.decent.sdk.utils.Utils;
import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.Duration;
import org.threeten.bp.LocalDateTime;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * this test checks calling Kotlin from Java
 */
@SuppressWarnings({"RxReturnValueIgnored", "UnusedVariable"})
public class ApiTest {

    private Logger logger = LoggerFactory.getLogger("API");
    private DCoreApi api = DCoreClient.create(Helpers.client(logger), Helpers.getWsUrl(), Helpers.getHttpUrl(), logger);

    @Test
    public void init() {
        DCoreApi apiHttp = DCoreClient.createForHttp(Helpers.client(logger), Helpers.getHttpUrl());
        apiHttp = DCoreClient.createForHttp(Helpers.client(logger), Helpers.getHttpUrl(), logger);
        DCoreApi apiWs = DCoreClient.createForWebSocket(Helpers.client(logger), Helpers.getWsUrl());
        apiWs = DCoreClient.createForWebSocket(Helpers.client(logger), Helpers.getWsUrl(), logger);
        Gson gson = DCoreClient.getGsonBuilder().create();

        apiHttp.setTransactionExpiration(Duration.ofSeconds(100L));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void accountApiTest() {
        api.getAccountApi().get(new AccountObjectId());
        api.getAccountApi().getAll(Collections.singletonList(new AccountObjectId()));
        api.getAccountApi().getByName("reference");
        api.getAccountApi().getAllByNames(Collections.singletonList("a name"));
        api.getAccountApi().countAll();
        api.getAccountApi().createCredentials("u961279ec8b7ae7bd62f304f7c1c3d345", "5Jd7zdvxXYNdUfnEXt5XokrE3zwJSs734yQ36a1YaqioRTGGLtn");
        api.getAccountApi().findAll("term");
        api.getAccountApi().findAll("term", SearchAccountsOrder.ID_ASC);
        api.getAccountApi().findAll("term", SearchAccountsOrder.ID_ASC, null);
        api.getAccountApi().findAll("term", SearchAccountsOrder.ID_ASC, null, 10);
        api.getAccountApi().findAllReferencesByAccount(new AccountObjectId());
        api.getAccountApi().findAllReferencesByKeys(Collections.singletonList(Address.decode("DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz")));
        api.getAccountApi().getFullAccounts(Collections.singletonList("nameorid"));
        api.getAccountApi().getFullAccounts(Collections.singletonList("nameorid"), true);
        api.getAccountApi().listAllRelative("abc");
        api.getAccountApi().listAllRelative("abc", 10);
        api.getAccountApi().searchAccountHistory(new AccountObjectId());
        api.getAccountApi().searchAccountHistory(new AccountObjectId(), null);
        api.getAccountApi().searchAccountHistory(new AccountObjectId(), null, SearchAccountHistoryOrder.FROM_ASC);
        api.getAccountApi().searchAccountHistory(new AccountObjectId(), null, SearchAccountHistoryOrder.FROM_ASC, 10);
    }

    @Test
    public void AssetApiTest() {
        api.getAssetApi().convertToDCT(new AssetObjectId(33), 10);
        api.getAssetApi().convertFromDCT(new AssetObjectId(33), 10);
        api.getAssetApi().get(new AssetObjectId());
        api.getAssetApi().getAll(Arrays.asList(new AssetObjectId(), new AssetObjectId()));
        api.getAssetApi().getAllByName(Arrays.asList("dct", "dct"));
        api.getAssetApi().getByName("dct");
        api.getAssetApi().getRealSupply();
        api.getAssetApi().listAllRelative("abc");
        api.getAssetApi().listAllRelative("abc", 10);
    }

    @Test
    public void BalanceApiTest() {
        api.getBalanceApi().get(new AccountObjectId(), new AssetObjectId());
        api.getBalanceApi().get("account.name", new AssetObjectId());
        api.getBalanceApi().getAll("account.name");
        api.getBalanceApi().getAll("account.name", Arrays.asList(new AssetObjectId(), new AssetObjectId()));
        api.getBalanceApi().getAll(new AccountObjectId());
        api.getBalanceApi().getAll(new AccountObjectId(), Arrays.asList(new AssetObjectId(), new AssetObjectId()));
        api.getBalanceApi().getAllVesting(new AccountObjectId());
        api.getBalanceApi().getWithAsset(new AccountObjectId(), "DCT");
        api.getBalanceApi().getWithAsset("account.name", "DCT");
        api.getBalanceApi().getAllWithAsset(new AccountObjectId(), Arrays.asList("DCT", "DCT"));
        api.getBalanceApi().getAllWithAsset("account.name", Arrays.asList("DCT", "DCT"));
    }

    @Test
    public void BlockApiTest() {
    }

    @Test
    public void BroadcastApiTest() {
        ECKeyPair keyPair = ECKeyPair.fromBase58(Helpers.getPrivate());
        TransferOperation operation = new TransferOperation(new AccountObjectId(), new AccountObjectId(), new AssetAmount(1));
        Transaction trx = new Transaction(Collections.singletonList(operation), LocalDateTime.now(), 0, 0, "fff4")
                .withSignature(keyPair);
        api.getBroadcastApi().broadcast(trx);
        api.getBroadcastApi().broadcast(Helpers.getPrivate(), operation);
        api.getBroadcastApi().broadcast(Helpers.getPrivate(), operation, Duration.ofSeconds(100));
        api.getBroadcastApi().broadcast(Helpers.getPrivate(), Collections.singletonList(operation));
        api.getBroadcastApi().broadcast(Helpers.getPrivate(), Collections.singletonList(operation), Duration.ofSeconds(100));
        api.getBroadcastApi().broadcast(keyPair, operation);
        api.getBroadcastApi().broadcast(keyPair, operation, Duration.ofSeconds(100));
        api.getBroadcastApi().broadcast(keyPair, Collections.singletonList(operation));
        api.getBroadcastApi().broadcast(keyPair, Collections.singletonList(operation), Duration.ofSeconds(100));
        api.getBroadcastApi().broadcastWithCallback(trx);
        api.getBroadcastApi().broadcastWithCallback(Helpers.getPrivate(), operation);
        api.getBroadcastApi().broadcastWithCallback(Helpers.getPrivate(), operation, Duration.ofSeconds(100));
        api.getBroadcastApi().broadcastWithCallback(Helpers.getPrivate(), Collections.singletonList(operation));
        api.getBroadcastApi().broadcastWithCallback(Helpers.getPrivate(), Collections.singletonList(operation), Duration.ofSeconds(100));
        api.getBroadcastApi().broadcastWithCallback(keyPair, operation);
        api.getBroadcastApi().broadcastWithCallback(keyPair, operation, Duration.ofSeconds(100));
        api.getBroadcastApi().broadcastWithCallback(keyPair, Collections.singletonList(operation));
        api.getBroadcastApi().broadcastWithCallback(keyPair, Collections.singletonList(operation), Duration.ofSeconds(100));
    }

    @Test
    public void ContentApiTest() {
        api.getContentApi().get("uri");
        api.getContentApi().get(new ContentObjectId());
    }

    @Test
    public void GeneralApiTest() {
    }

    @Test
    public void HistoryApiTest() {
        OperationHistoryObjectId historyObj = new OperationHistoryObjectId();
        api.getHistoryApi().listOperations(new AccountObjectId());
        api.getHistoryApi().listOperations(new AccountObjectId(), historyObj);
        api.getHistoryApi().listOperations(new AccountObjectId(), historyObj, historyObj);
        api.getHistoryApi().listOperations(new AccountObjectId(), historyObj, historyObj, 100);
    }

    @Test
    public void MiningApiTest() {
        api.getMiningApi().getMiners();
        api.getMiningApi().getMiners(Arrays.asList(new MinerObjectId(), new MinerObjectId()));
        api.getMiningApi().listMinersRelative();
        api.getMiningApi().listMinersRelative("lookup");
        api.getMiningApi().listMinersRelative("lookup", 1);
    }

/*
    @Test
    public void OperationsHelperTest() {
        Credentials credentials = new Credentials(new AccountObjectId(, Helpers.getPrivate());
        AssetAmount fee = new AssetAmount(BigInteger.valueOf(50), new AssetObjectId());
        api.getOperationsHelper().createBuyContent(credentials, new ContentObjectId();
        api.getOperationsHelper().createBuyContent(credentials, "uri");
        api.getOperationsHelper().createTransfer(credentials, "reference", new AssetAmount(10));
        api.getOperationsHelper().createTransfer(credentials, "reference", new AssetAmount(10), fee);
        api.getOperationsHelper().createTransfer(credentials, "reference", new AssetAmount(10), "memo");
        api.getOperationsHelper().createTransfer(credentials, "reference", new AssetAmount(10), "public memo", false);
        api.getOperationsHelper().createTransfer(credentials, "reference", new AssetAmount(10), "public memo", false, fee);
        api.getOperationsHelper().createVote(new AccountObjectId(, Arrays.asList(new MinerObjectId(, new MinerObjectId());
        api.getOperationsHelper().transfer(credentials, "reference", new AssetAmount(10));
        api.getOperationsHelper().transfer(credentials, "reference", new AssetAmount(10), fee);
        api.getOperationsHelper().transfer(credentials, "reference", new AssetAmount(10), "memo");
        api.getOperationsHelper().transfer(credentials, "reference", new AssetAmount(10), "public memo", false);
        api.getOperationsHelper().transfer(credentials, "reference", new AssetAmount(10), "public memo", false, fee);
    }
*/

    @Test
    public void PurchaseApiTest() {
        api.getPurchaseApi().get(new AccountObjectId(), "uri");
        api.getPurchaseApi().findAll(new AccountObjectId());
        api.getPurchaseApi().findAll(new AccountObjectId(), "term");
        api.getPurchaseApi().findAll(new AccountObjectId(), "term", new PurchaseObjectId());
        api.getPurchaseApi().findAll(new AccountObjectId(), "term", new PurchaseObjectId(), SearchPurchasesOrder.PURCHASED_DESC);
        api.getPurchaseApi().findAll(new AccountObjectId(), "term", new PurchaseObjectId(), SearchPurchasesOrder.PURCHASED_DESC, 100);
    }

    @Test
    public void SeedersApiTest() {
    }

    @Test
    public void SubscriptionApiTest() {
    }

    @Test
    public void TransactionApiTest() {
        byte[] id = new byte[20];
        Arrays.fill(id, (byte) 0);
        BaseOperation operation = new EmptyOperation(OperationType.TRANSFER2_OPERATION);
        api.getTransactionApi().getRecent(Utils.hex(id));
        api.getTransactionApi().get(1, 1);
        api.getTransactionApi().get(new TransactionConfirmation("", 1, 1,
                new ProcessedTransaction(Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), LocalDateTime.now(), 1, 1, Collections.emptyList())));
        api.getTransactionApi().createTransaction(operation);
        api.getTransactionApi().createTransaction(operation, Duration.ofSeconds(100));
        api.getTransactionApi().createTransaction(Arrays.asList(operation, operation));
        api.getTransactionApi().createTransaction(Arrays.asList(operation, operation), Duration.ofSeconds(100));
    }

    @Test
    public void ValidationApiTest() {
        api.getValidationApi().getFee(new EmptyOperation(OperationType.TRANSFER2_OPERATION));
        api.getValidationApi().getFee(new EmptyOperation(OperationType.TRANSFER2_OPERATION), DCoreConstants.DCT_ASSET_ID);
        api.getValidationApi().getFees(Arrays.asList(new EmptyOperation(OperationType.TRANSFER2_OPERATION), new EmptyOperation(OperationType.TRANSFER2_OPERATION)));
        api.getValidationApi().getFees(Arrays.asList(new EmptyOperation(OperationType.TRANSFER2_OPERATION), new EmptyOperation(OperationType.TRANSFER2_OPERATION)), DCoreConstants.DCT_ASSET_ID);
        api.getValidationApi().getFeeForType(OperationType.TRANSFER2_OPERATION);
        api.getValidationApi().getFeeForType(OperationType.TRANSFER2_OPERATION, DCoreConstants.DCT_ASSET_ID);
        api.getValidationApi().getFeesForType(Collections.singletonList(OperationType.TRANSFER2_OPERATION));
        api.getValidationApi().getFeesForType(Collections.singletonList(OperationType.TRANSFER2_OPERATION), DCoreConstants.DCT_ASSET_ID);
    }

    @Test
    public void NftApiTest() {
        Credentials credentials = new Credentials(new AccountObjectId(), Helpers.getPrivate());
        api.getNftApi().countAll();
        api.getNftApi().countAllData();
        api.getNftApi().create(credentials, "NFT", 10, true, "description", NftJava.class, true);

    }

    class NftJava implements NftModel {

        public String string;
        public boolean aBoolean;
        public short aShort;
        public byte aByte;
        public int anInt;
        public long aLong;

        @NotNull
        @Override
        public List<Object> values() {
            return null;
        }

        @NotNull
        @Override
        public Map<String, Object> createUpdate() {
            return null;
        }
    }
}
