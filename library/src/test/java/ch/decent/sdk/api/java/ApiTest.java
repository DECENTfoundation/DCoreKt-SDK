package ch.decent.sdk.api.java;

import ch.decent.sdk.DCoreApi;
import ch.decent.sdk.DCoreConstants;
import ch.decent.sdk.DCoreSdk;
import ch.decent.sdk.Helpers;
import ch.decent.sdk.crypto.Address;
import ch.decent.sdk.crypto.ECKeyPair;
import ch.decent.sdk.model.*;
import ch.decent.sdk.utils.Utils;
import com.google.gson.Gson;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.LocalDateTime;

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
        api.getAccountApi().get(ObjectType.ACCOUNT_OBJECT.getGenericId());
        api.getAccountApi().getAll(Collections.singletonList(ObjectType.ACCOUNT_OBJECT.getGenericId()));
        api.getAccountApi().getByName("reference");
        api.getAccountApi().getAllByNames(Collections.singletonList("a name"));
        api.getAccountApi().countAll();
        api.getAccountApi().createCredentials("u961279ec8b7ae7bd62f304f7c1c3d345", "5Jd7zdvxXYNdUfnEXt5XokrE3zwJSs734yQ36a1YaqioRTGGLtn");
        api.getAccountApi().findAll("term");
        api.getAccountApi().findAll("term", SearchAccountsOrder.ID_ASC);
        api.getAccountApi().findAll("term", SearchAccountsOrder.ID_ASC, ObjectType.NULL_OBJECT.getGenericId());
        api.getAccountApi().findAll("term", SearchAccountsOrder.ID_ASC, ObjectType.NULL_OBJECT.getGenericId(), 10);
        api.getAccountApi().findAllReferencesByAccount(ObjectType.ACCOUNT_OBJECT.getGenericId());
        api.getAccountApi().findAllReferencesByKeys(Collections.singletonList(Address.decode("DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz")));
        api.getAccountApi().getFullAccounts(Collections.singletonList("a name or id"));
        api.getAccountApi().getFullAccounts(Collections.singletonList("a name or id"), true);
        api.getAccountApi().listAllRelative("abc");
        api.getAccountApi().listAllRelative("abc", 10);
        api.getAccountApi().searchAccountHistory(ObjectType.ACCOUNT_OBJECT.getGenericId());
        api.getAccountApi().searchAccountHistory(ObjectType.ACCOUNT_OBJECT.getGenericId(), ObjectType.NULL_OBJECT.getGenericId());
        api.getAccountApi().searchAccountHistory(ObjectType.ACCOUNT_OBJECT.getGenericId(), ObjectType.NULL_OBJECT.getGenericId(), SearchAccountHistoryOrder.FROM_ASC);
        api.getAccountApi().searchAccountHistory(ObjectType.ACCOUNT_OBJECT.getGenericId(), ObjectType.NULL_OBJECT.getGenericId(), SearchAccountHistoryOrder.FROM_ASC, 10);
    }

    @Test
    public void AssetApiTest() {
        api.getAssetApi().convertToDct(new AssetAmount(10));
        api.getAssetApi().get(ObjectType.ASSET_OBJECT.getGenericId());
        api.getAssetApi().getAll(Arrays.asList(ObjectType.ASSET_OBJECT.getGenericId(), ObjectType.ASSET_OBJECT.getGenericId()));
        api.getAssetApi().getAllByName(Arrays.asList("dct", "dct"));
        api.getAssetApi().getByName("dct");
        api.getAssetApi().getRealSupply();
        api.getAssetApi().listAllRelative("abc");
        api.getAssetApi().listAllRelative("abc", 10);
    }

    @Test
    public void BalanceApiTest() {
        api.getBalanceApi().get(ObjectType.ACCOUNT_OBJECT.getGenericId(), ObjectType.ASSET_OBJECT.getGenericId());
        api.getBalanceApi().get("account.name", ObjectType.ASSET_OBJECT.getGenericId());
        api.getBalanceApi().getAll("account.name");
        api.getBalanceApi().getAll("account.name", Arrays.asList(ObjectType.ASSET_OBJECT.getGenericId(), ObjectType.ASSET_OBJECT.getGenericId()));
        api.getBalanceApi().getAll(ObjectType.ACCOUNT_OBJECT.getGenericId());
        api.getBalanceApi().getAll(ObjectType.ACCOUNT_OBJECT.getGenericId(), Arrays.asList(ObjectType.ASSET_OBJECT.getGenericId(), ObjectType.ASSET_OBJECT.getGenericId()));
        api.getBalanceApi().getAllVesting(ObjectType.ACCOUNT_OBJECT.getGenericId());
        api.getBalanceApi().getWithAsset(ObjectType.ACCOUNT_OBJECT.getGenericId(), "DCT");
        api.getBalanceApi().getWithAsset("account.name", "DCT");
        api.getBalanceApi().getAllWithAsset(ObjectType.ACCOUNT_OBJECT.getGenericId(), Arrays.asList("DCT", "DCT"));
        api.getBalanceApi().getAllWithAsset("account.name", Arrays.asList("DCT", "DCT"));
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
        api.getContentApi().get("uri");
        api.getContentApi().get(ObjectType.CONTENT_OBJECT.getGenericId());
    }

    @Test
    public void GeneralApiTest() {
    }

    @Test
    public void HistoryApiTest() {
        ChainObject historyObj = ObjectType.OPERATION_HISTORY_OBJECT.getGenericId();
        api.getHistoryApi().listOperations(ObjectType.ACCOUNT_OBJECT.getGenericId());
        api.getHistoryApi().listOperations(ObjectType.ACCOUNT_OBJECT.getGenericId(), historyObj);
        api.getHistoryApi().listOperations(ObjectType.ACCOUNT_OBJECT.getGenericId(), historyObj, historyObj);
        api.getHistoryApi().listOperations(ObjectType.ACCOUNT_OBJECT.getGenericId(), historyObj, historyObj, 100);
    }

    @Test
    public void MiningApiTest() {
        api.getMiningApi().getMiners();
        api.getMiningApi().getMiners(Arrays.asList(ObjectType.MINER_OBJECT.getGenericId(), ObjectType.MINER_OBJECT.getGenericId()));
        api.getMiningApi().listMinersRelative();
        api.getMiningApi().listMinersRelative("lookup");
        api.getMiningApi().listMinersRelative("lookup", 1);
    }

/*
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
*/

    @Test
    public void PurchaseApiTest() {
        api.getPurchaseApi().get(ObjectType.ACCOUNT_OBJECT.getGenericId(), "uri");
        api.getPurchaseApi().findAll(ObjectType.ACCOUNT_OBJECT.getGenericId());
        api.getPurchaseApi().findAll(ObjectType.ACCOUNT_OBJECT.getGenericId(), "term");
        api.getPurchaseApi().findAll(ObjectType.ACCOUNT_OBJECT.getGenericId(), "term", ObjectType.PURCHASE_OBJECT.getGenericId());
        api.getPurchaseApi().findAll(ObjectType.ACCOUNT_OBJECT.getGenericId(), "term", ObjectType.PURCHASE_OBJECT.getGenericId(), SearchPurchasesOrder.PURCHASED_DESC);
        api.getPurchaseApi().findAll(ObjectType.ACCOUNT_OBJECT.getGenericId(), "term", ObjectType.PURCHASE_OBJECT.getGenericId(), SearchPurchasesOrder.PURCHASED_DESC, 100);
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
        api.getTransactionApi().createTransaction(operation, 100);
        api.getTransactionApi().createTransaction(Arrays.asList(operation, operation));
        api.getTransactionApi().createTransaction(Arrays.asList(operation, operation), 100);
    }

    @Test
    public void ValidationApiTest() {
        api.getValidationApi().getFee(new EmptyOperation(OperationType.TRANSFER2_OPERATION));
        api.getValidationApi().getFee(new EmptyOperation(OperationType.TRANSFER2_OPERATION), DCoreConstants.DCT_ASSET_ID);
        api.getValidationApi().getFees(Arrays.asList(new EmptyOperation(OperationType.TRANSFER2_OPERATION), new EmptyOperation(OperationType.TRANSFER2_OPERATION)));
        api.getValidationApi().getFees(Arrays.asList(new EmptyOperation(OperationType.TRANSFER2_OPERATION), new EmptyOperation(OperationType.TRANSFER2_OPERATION)), DCoreConstants.DCT_ASSET_ID);
        api.getValidationApi().getFee(OperationType.TRANSFER2_OPERATION);
        api.getValidationApi().getFee(OperationType.TRANSFER2_OPERATION, DCoreConstants.DCT_ASSET_ID);
    }
}
