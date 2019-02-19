package ch.decent.sdk.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import ch.decent.sdk.DCoreApi;
import ch.decent.sdk.DCoreSdk;
import ch.decent.sdk.crypto.Credentials;
import ch.decent.sdk.model.*;
import ch.decent.sdk.net.TrustAllCerts;
import kotlin.Pair;
import okhttp3.OkHttpClient;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        setup dcore api
        OkHttpClient client = TrustAllCerts.wrap(new OkHttpClient().newBuilder()).build();
        DCoreApi api = DCoreSdk.create(
                client,
                "wss://stagesocket.decentgo.com:8090",
                "https://stagesocket.decentgo.com:8090",
                LoggerFactory.getLogger("SDK_SAMPLE"));

//        create user credentials
        Credentials credentials = api.getAccountApi()
                .createCredentials("u961279ec8b7ae7bd62f304f7c1c3d345", "5Jd7zdvxXYNdUfnEXt5XokrE3zwJSs734yQ36a1YaqioRTGGLtn")
                .blockingGet();

//        balance
        Pair<Asset, AssetAmount> balance = api.getBalanceApi()
                .getWithAsset(credentials.getAccount(), "DCT")
                .blockingGet();
        Log.i("BALANCE", balance.getFirst().format(balance.getSecond().getAmount()));

//        receiver
        Account receiver = api.getAccountApi().getByName("u3a7b78084e7d3956442d5a4d439dad51")
                .blockingGet();
        Log.i("RECIEVER ACCOUNT", receiver.toString());

//        transfer
        AssetAmount amount = balance.getFirst().amount(0.12345);
        Memo memo = new Memo("hello memo", credentials, receiver);
        TransferOperation operation = new TransferOperation(credentials.getAccount(), receiver.getId(), amount, memo);

        TransactionConfirmation confirmation = api.getBroadcastApi()
                .broadcastWithCallback(credentials.getKeyPair(), operation)
                .blockingGet();

        Log.i("TRANSACTION", confirmation.toString());

//        verify
        ProcessedTransaction trx = api.getTransactionApi().get(confirmation)
                .blockingGet();
        Log.i("TRANSACTION EXIST", trx.toString());

//        history
        List<OperationHistory> history = api.getHistoryApi().listOperations(credentials.getAccount())
                .blockingGet();
        Log.i("HISTORY", history.toString());
    }
}
