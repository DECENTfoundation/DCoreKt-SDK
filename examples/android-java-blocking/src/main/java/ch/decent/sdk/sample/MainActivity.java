package ch.decent.sdk.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsMessage;
import android.util.Log;

import com.google.common.base.Optional;

import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ch.decent.sdk.DCoreApi;
import ch.decent.sdk.DCoreConstants;
import ch.decent.sdk.DCoreSdk;
import ch.decent.sdk.crypto.Credentials;
import ch.decent.sdk.exception.ObjectNotFoundException;
import ch.decent.sdk.model.Asset;
import ch.decent.sdk.model.AssetAmount;
import ch.decent.sdk.model.BaseOperation;
import ch.decent.sdk.model.ObjectType;
import ch.decent.sdk.model.OperationHistory;
import ch.decent.sdk.model.ProcessedTransaction;
import ch.decent.sdk.model.TransactionConfirmation;
import ch.decent.sdk.net.TrustAllCerts;
import io.reactivex.Single;
import kotlin.Pair;
import okhttp3.OkHttpClient;

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
                .getBalanceWithAsset(credentials.getAccount(), "DCT")
                .blockingGet();
        Log.i("BALANCE", balance.getFirst().format(balance.getSecond().getAmount(), 2));

//        transfer
        AssetAmount amount = balance.getFirst().amount(0.12345);
        TransactionConfirmation confirmation = api.getOperationsHelper()
                .transfer(credentials, "u3a7b78084e7d3956442d5a4d439dad51", amount, "hello memo")
                .blockingGet();
        Log.i("TRANSACTION", confirmation.toString());

//        verify
        ProcessedTransaction trx = api.getTransactionApi().getTransaction(confirmation)
                .blockingGet();
        Log.i("TRANSACTION EXIST", trx.toString());

//        history
        List<OperationHistory> history = api.getHistoryApi().getAccountHistory(credentials.getAccount())
                .blockingGet();
        Log.i("HISTORY", history.toString());
    }
}
