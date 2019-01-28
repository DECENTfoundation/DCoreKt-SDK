package ch.decent.sdk.api.java;

import ch.decent.sdk.DCoreApi;
import ch.decent.sdk.DCoreSdk;
import ch.decent.sdk.Helpers;
import ch.decent.sdk.crypto.Address;
import ch.decent.sdk.crypto.Credentials;
import ch.decent.sdk.crypto.ECKeyPair;
import ch.decent.sdk.model.*;
import ch.decent.sdk.utils.Utils;
import com.google.gson.Gson;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.LocalDateTime;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

/**
 * this test checks calling Kotlin from Java
 */
public class ApiTest {

    private Logger logger = LoggerFactory.getLogger("API");
    private DCoreApi api = DCoreSdk.create(Helpers.client(logger), Helpers.getUrl(), Helpers.getRestUrl(), logger);

    @Test
    public void init() {
        DCoreApi apiHttp = DCoreSdk.createForHttp(Helpers.client(logger), Helpers.getRestUrl());
        apiHttp = DCoreSdk.createForHttp(Helpers.client(logger), Helpers.getRestUrl(), logger);
        DCoreApi apiWs = DCoreSdk.createForWebSocket(Helpers.client(logger), Helpers.getUrl());
        apiWs = DCoreSdk.createForWebSocket(Helpers.client(logger), Helpers.getUrl(), logger);
        Gson gson = DCoreSdk.getGsonBuilder().create();

        apiHttp.setTransactionExpiration(100);
    }

    @Test
    public void accountApiTest() {
        api.getAccountApi().accountExist("reference");
        api.getAccountApi().getAccount("reference");
        api.getAccountApi().getAccount(ObjectType.ACCOUNT_OBJECT.getGenericId().toString());
        api.getAccountApi().getAccount("DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz");
        api.getAccountApi().getAccounts(Collections.singletonList(ObjectType.ACCOUNT_OBJECT.getGenericId()));
        api.getAccountApi().getAccountIds(Collections.singletonList(Address.decode("DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz")));
        api.getAccountApi().searchAccountHistory(ObjectType.ACCOUNT_OBJECT.getGenericId());
        api.getAccountApi().searchAccountHistory(ObjectType.ACCOUNT_OBJECT.getGenericId(), ObjectType.NULL_OBJECT.getGenericId());
        api.getAccountApi().searchAccountHistory(ObjectType.ACCOUNT_OBJECT.getGenericId(), ObjectType.NULL_OBJECT.getGenericId(), SearchAccountHistoryOrder.FROM_ASC);
        api.getAccountApi().searchAccountHistory(ObjectType.ACCOUNT_OBJECT.getGenericId(), ObjectType.NULL_OBJECT.getGenericId(), SearchAccountHistoryOrder.FROM_ASC, 10);
        api.getAccountApi().createCredentials("u961279ec8b7ae7bd62f304f7c1c3d345", "5Jd7zdvxXYNdUfnEXt5XokrE3zwJSs734yQ36a1YaqioRTGGLtn");
    }

    @Test
    public void AssetApiTest() {
        api.getAssetApi().getAsset(ObjectType.ASSET_OBJECT.getGenericId());
        api.getAssetApi().getAssets(Arrays.asList(ObjectType.ASSET_OBJECT.getGenericId(), ObjectType.ASSET_OBJECT.getGenericId()));
        api.getAssetApi().lookupAsset("dct");
        api.getAssetApi().lookupAssets(Arrays.asList("dct", "dct"));
    }

    @Test
    public void AuthorityApiTest() {
    }

    @Test
    public void BalanceApiTest() {
        api.getBalanceApi().getBalance(ObjectType.ACCOUNT_OBJECT.getGenericId());
        api.getBalanceApi().getBalance(ObjectType.ACCOUNT_OBJECT.getGenericId(), ObjectType.ASSET_OBJECT.getGenericId());
        api.getBalanceApi().getBalance(ObjectType.ACCOUNT_OBJECT.getGenericId(), Arrays.asList(ObjectType.ASSET_OBJECT.getGenericId(), ObjectType.ASSET_OBJECT.getGenericId()));
        api.getBalanceApi().getBalance("reference");
        api.getBalanceApi().getBalance("reference", Arrays.asList(ObjectType.ASSET_OBJECT.getGenericId(), ObjectType.ASSET_OBJECT.getGenericId()));
        api.getBalanceApi().getBalance("reference", ObjectType.ASSET_OBJECT.getGenericId());
        api.getBalanceApi().getBalanceWithAsset(ObjectType.ACCOUNT_OBJECT.getGenericId(), "DCT");
        api.getBalanceApi().getBalanceWithAsset(ObjectType.ACCOUNT_OBJECT.getGenericId(), Arrays.asList("DCT", "DCT"));
        api.getBalanceApi().getBalanceWithAsset("reference", "DCT");
        api.getBalanceApi().getBalanceWithAsset("reference", Arrays.asList("DCT", "DCT"));
    }

    @Test
    public void BlockApiTest() {
    }

    @Test
    public void BroadcastApiTest() {
        ECKeyPair keyPair = ECKeyPair.fromBase58(Helpers.getPrivate());
        TransferOperation operation = new TransferOperation(ObjectType.ACCOUNT_OBJECT.getGenericId(), ObjectType.ACCOUNT_OBJECT.getGenericId(), new AssetAmount(1));
        Transaction trx = new Transaction(new BlockData(0, 0, 0), Collections.singletonList(operation), "")
                .withSignature(keyPair);
        api.getBroadcastApi().broadcast(trx);
        api.getBroadcastApi().broadcast(Helpers.getPrivate(), operation);
        api.getBroadcastApi().broadcast(Helpers.getPrivate(), operation, 100);
        api.getBroadcastApi().broadcast(Helpers.getPrivate(), Collections.singletonList(operation));
        api.getBroadcastApi().broadcast(Helpers.getPrivate(), Collections.singletonList(operation), 100);
        api.getBroadcastApi().broadcast(keyPair, operation);
        api.getBroadcastApi().broadcast(keyPair, operation, 100);
        api.getBroadcastApi().broadcast(keyPair, Collections.singletonList(operation));
        api.getBroadcastApi().broadcast(keyPair, Collections.singletonList(operation), 100);
        api.getBroadcastApi().broadcastWithCallback(trx);
        api.getBroadcastApi().broadcastWithCallback(Helpers.getPrivate(), operation);
        api.getBroadcastApi().broadcastWithCallback(Helpers.getPrivate(), operation, 100);
        api.getBroadcastApi().broadcastWithCallback(Helpers.getPrivate(), Collections.singletonList(operation));
        api.getBroadcastApi().broadcastWithCallback(Helpers.getPrivate(), Collections.singletonList(operation), 100);
        api.getBroadcastApi().broadcastWithCallback(keyPair, operation);
        api.getBroadcastApi().broadcastWithCallback(keyPair, operation, 100);
        api.getBroadcastApi().broadcastWithCallback(keyPair, Collections.singletonList(operation));
        api.getBroadcastApi().broadcastWithCallback(keyPair, Collections.singletonList(operation), 100);
    }

    @Test
    public void ContentApiTest() {
        api.getContentApi().getContent("uri");
        api.getContentApi().getContent(ObjectType.CONTENT_OBJECT.getGenericId());
    }

    @Test
    public void GeneralApiTest() {
    }

    @Test
    public void HistoryApiTest() {
        ChainObject historyObj = ObjectType.OPERATION_HISTORY_OBJECT.getGenericId();
        api.getHistoryApi().getAccountHistory(ObjectType.ACCOUNT_OBJECT.getGenericId());
        api.getHistoryApi().getAccountHistory(ObjectType.ACCOUNT_OBJECT.getGenericId(), historyObj);
        api.getHistoryApi().getAccountHistory(ObjectType.ACCOUNT_OBJECT.getGenericId(), historyObj, historyObj);
        api.getHistoryApi().getAccountHistory(ObjectType.ACCOUNT_OBJECT.getGenericId(), historyObj, historyObj, 100);
    }

    @Test
    public void MiningApiTest() {
        api.getMiningApi().getMiners();
        api.getMiningApi().getMiners(Arrays.asList(ObjectType.MINER_OBJECT.getGenericId(), ObjectType.MINER_OBJECT.getGenericId()));
        api.getMiningApi().lookupMiners();
        api.getMiningApi().lookupMiners("lookup");
        api.getMiningApi().lookupMiners("lookup", 1);
    }

    @Test
    public void OperationsHelperTest() {
        Credentials credentials = new Credentials(ObjectType.ACCOUNT_OBJECT.getGenericId(), Helpers.getPrivate());
        AssetAmount fee = new AssetAmount(BigInteger.valueOf(50), ObjectType.ASSET_OBJECT.getGenericId());
        api.getOperationsHelper().createBuyContent(credentials, ObjectType.CONTENT_OBJECT.getGenericId());
        api.getOperationsHelper().createBuyContent(credentials, "uri");
        api.getOperationsHelper().createTransfer(credentials, "reference", new AssetAmount(10));
        api.getOperationsHelper().createTransfer(credentials, "reference", new AssetAmount(10), fee);
        api.getOperationsHelper().createTransfer(credentials, "reference", new AssetAmount(10), "memo");
        api.getOperationsHelper().createTransfer(credentials, "reference", new AssetAmount(10), "public memo", false);
        api.getOperationsHelper().createTransfer(credentials, "reference", new AssetAmount(10), "public memo", false, fee);
        api.getOperationsHelper().createVote(ObjectType.ACCOUNT_OBJECT.getGenericId(), Arrays.asList(ObjectType.MINER_OBJECT.getGenericId(), ObjectType.MINER_OBJECT.getGenericId()));
        api.getOperationsHelper().transfer(credentials, "reference", new AssetAmount(10));
        api.getOperationsHelper().transfer(credentials, "reference", new AssetAmount(10), fee);
        api.getOperationsHelper().transfer(credentials, "reference", new AssetAmount(10), "memo");
        api.getOperationsHelper().transfer(credentials, "reference", new AssetAmount(10), "public memo", false);
        api.getOperationsHelper().transfer(credentials, "reference", new AssetAmount(10), "public memo", false, fee);
    }

    @Test
    public void PurchaseApiTest() {
        api.getPurchaseApi().getPurchase(ObjectType.ACCOUNT_OBJECT.getGenericId(), "uri");
        api.getPurchaseApi().searchPurchases(ObjectType.ACCOUNT_OBJECT.getGenericId());
        api.getPurchaseApi().searchPurchases(ObjectType.ACCOUNT_OBJECT.getGenericId(), "term");
        api.getPurchaseApi().searchPurchases(ObjectType.ACCOUNT_OBJECT.getGenericId(), "term", ObjectType.BUYING_OBJECT.getGenericId());
        api.getPurchaseApi().searchPurchases(ObjectType.ACCOUNT_OBJECT.getGenericId(), "term", ObjectType.BUYING_OBJECT.getGenericId(), SearchPurchasesOrder.PURCHASED_DESC);
        api.getPurchaseApi().searchPurchases(ObjectType.ACCOUNT_OBJECT.getGenericId(), "term", ObjectType.BUYING_OBJECT.getGenericId(), SearchPurchasesOrder.PURCHASED_DESC, 100);
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
        api.getTransactionApi().getRecentTransaction(Utils.hex(id));
        api.getTransactionApi().getTransaction(1, 1);
        api.getTransactionApi().getTransaction(new TransactionConfirmation("", 1, 1,
                new ProcessedTransaction(Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), LocalDateTime.now(), 1, 1, Collections.emptyList())));
        api.getTransactionApi().createTransaction(operation);
        api.getTransactionApi().createTransaction(operation, 100);
        api.getTransactionApi().createTransaction(Arrays.asList(operation, operation));
        api.getTransactionApi().createTransaction(Arrays.asList(operation, operation), 100);
    }

    @Test
    public void ValidationApiTest() {
        api.getValidationApi().getFee(new EmptyOperation(OperationType.TRANSFER2_OPERATION));
        api.getValidationApi().getFees(Arrays.asList(new EmptyOperation(OperationType.TRANSFER2_OPERATION), new EmptyOperation(OperationType.TRANSFER2_OPERATION)));
        api.getValidationApi().getFee(OperationType.TRANSFER2_OPERATION);
    }
}
