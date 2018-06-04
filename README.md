# DCore Kotlin SDK

provides API for accessing the DCore Blockchain

## Android Project Setup

* add sdk repository url and jcenter() to **top level** *build.gradle*

    ```groovy
     allprojects {
         repositories {
             google()
             jcenter()
             maven { url "http://ec2-18-196-32-67.eu-central-1.compute.amazonaws.com/artifactory/libs-release" }
         }
     }
    ```

* add sdk dependencies to **app module** *build.gradle*

    ```groovy
        dependencies {
            implementation('ch.decent:dcore-sdk-kt:0.6.2') {
                exclude group: 'org.threeten', module: 'threetenbp'
            }
            implementation 'com.jakewharton.threetenabp:threetenabp:1.1.0'
        }
    ```
        
* enable multidex support to **app module** *build.gradle*

    ```groovy
        android {
            defaultConfig {
                multiDexEnabled true
            }
        }
    ```
            
    if minSdkVersion < 21 add also dependency for mutlidex

    ```groovy

        dependencies {
            compile 'com.android.support:multidex:1.0.3'
        }
    ```
            
* add internet permission to *AndroidManifest.xml*

        <manifest
            xmlns:android="http://schemas.android.com/apk/res/android"
            package="ch.decent.sdk.sample"
            >
        
            <uses-permission android:name="android.permission.INTERNET" />
        
* use the API

    ```java
    public class MainActivity extends AppCompatActivity {
    
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
    
    //        setup dcore api
            OkHttpClient client = TrustAllCerts.wrap(new OkHttpClient().newBuilder()).build();
            DCoreApi api = DCoreSdk.createApi(client, "wss://stage.decentgo.com:8090");
    
    //        create user credentials
            Optional<Credentials> credentials = api.createCredentials("u961279ec8b7ae7bd62f304f7c1c3d345", "5Jd7zdvxXYNdUfnEXt5XokrE3zwJSs734yQ36a1YaqioRTGGLtn");
    
    //        balance
            Optional<BigDecimal> balance = api.getBalance("u3a7b78084e7d3956442d5a4d439dad51", "DCT");
            Log.i("BALANCE", balance.toString());
    
    //        transfer
            TransactionConfirmation confirmation = api.transfer(credentials.get(), "u3a7b78084e7d3956442d5a4d439dad51", 0.12345, "hello memo", false);
            Log.i("TRANSACTION", confirmation.toString());
    
    //        verify
            Optional<ProcessedTransaction> trx = api.getTransaction(confirmation);
            Log.i("TRANSACTION EXIST", trx.toString());
    
    //        history
            List<OperationHistory> history = api.getAccountHistory("u961279ec8b7ae7bd62f304f7c1c3d345");
            Log.i("HISTORY", history.toString());
        }
    }
    ```
