package apps.hillavas.com.meditation;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.billingclient.util.IabBroadcastReceiver;
import com.android.billingclient.util.IabHelper;
import com.android.billingclient.util.IabResult;
import com.android.billingclient.util.Inventory;
import com.android.billingclient.util.Purchase;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpResponseHandler;

import net.jhoobin.jhub.CharkhoneSdkApp;

import classes.tools.IrancellApi;
import classes.tools.LocalStorage;
import classes.tools.Validator;
import cz.msebera.android.httpclient.Header;


public class IntroActivity extends Activity implements View.OnClickListener, IabBroadcastReceiver.IabBroadcastListener {
    //
//
    Button btnOk;
    EditText txtPhone;
    //
    String code, phoneNumber = "";

    Handler readSmsHandler = new Handler();
    private long readSmsDelay = 500;
    private long nowDate;

    // Debug tag, for logging
    private static final String TAG = "Meditation";
    // Does the user have the premium upgrade?
    private boolean mIsPremium = false;
    // SKUs for our products: the premium upgrade (non-consumable) and gas (consumable)
    private static final String SKU_PREMIUM = "NiniYar_SKU";
    // (arbitrary) request code for the purchase flow
    private static final int RC_REQUEST = 10001;
    // The helper object
    private IabHelper mHelper;
    // Provides purchase notification while this app is running
    private IabBroadcastReceiver mBroadcastReceiver;
    String payload = "";
    private IrancellApi irancellApi;
    LocalStorage localStorage;

    public String[] getSecrets() {
        return getResources().getStringArray(R.array.secrets);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        localStorage = new LocalStorage(getApplicationContext());


//        if (!localStorage.getSharedPreferences().getBoolean("tour", false)) {
//
//            if (!localStorage.getSharedPreferences().getBoolean("pay", false)) {
//
//                localStorage.saveLocalData("tour", true);
//
//                startActivity(new Intent(IntroActivity.this, TourActivity.class));
//
//                finish();
//                return;
//            }
//        }

//        localStorage.getSharedPreferences().edit().remove("tour").commit();


        CharkhoneSdkApp.initSdk(getApplicationContext(), getSecrets(), true);

//
//
        btnOk = (Button) findViewById(R.id.btn_ok);
        txtPhone = (EditText) findViewById(R.id.txt_phone);

        btnOk.setVisibility(View.INVISIBLE);
        txtPhone.setVisibility(View.INVISIBLE);

        btnOk.setOnClickListener(this);

        irancellApi = new IrancellApi();
//
//
        String base64EncodedPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCIHg4cW9avnZYkKOet/k/TSsXngpWXtVpuwvxXhfn3HdrWV47LA28aBrODL1n9+corT+F5nIVa5pd3p2xR99ob8rv3pssYkkBU9Z21d+JVx4tLxTXetazZCaL8Uux3sJTHNHC6Yuab/SXtZLK/2ArYRKbmbaNBo8CJgHXTNMRSBwIDAQAB";

        // Create the helper, passing it our context and the public key to verify signatures with
        Log.d(TAG, "Creating IAB helper.");
        mHelper = new IabHelper(this, base64EncodedPublicKey);

        // enable debug logging (for a production application, you should set this to false).
        mHelper.enableDebugLogging(false);

        // Start setup. This is asynchronous and the specified listener
        // will be called once setup completes.
        Log.d(TAG, "Starting setup.");
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Setup finished.");

                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    alert("Problem setting up in-app billing: " + result);
                    return;
                }

                // Have we been disposed of in the meantime? If so, quit.
                if (mHelper == null) return;

//                // IAB is fully set up. Now, let's get an inventory of stuff we own.
                Log.d(TAG, "Setup successful. Querying inventory.");
                try {
                    mHelper.queryInventoryAsync(mGotInventoryListener);
                } catch (IabHelper.IabAsyncInProgressException e) {

                    alert("Error querying inventory. Another async operation in progress.");
                }
            }
        });
//        String number = localStorage.getSharedPreferences().getString("phoneNumber", null);
//        if (number != null) {
//            txtPhone.setText(number);
//            checkAccount(number);
//
//
//        }

    }
//

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_ok:

                phoneNumber = String.valueOf(txtPhone.getText());


                Intent fillInIntent = new Intent();
                if (!Validator.isMobileNumber(phoneNumber)) {
                    alert("شماره ایرانسلی معتبر نیست.");
                    return;
                }

                //localStorage.saveLocalData("phoneNumber", phoneNumber);

                //checkAccount(phoneNumber);

                fillInIntent.putExtra("msisdn", phoneNumber);
                mHelper.setFillInIntent(fillInIntent);


                payload = "";
                try {

                    mHelper.launchPurchaseFlow(IntroActivity.this, SKU_PREMIUM, RC_REQUEST,
                            mPurchaseFinishedListener, payload);

                    btnOk.setVisibility(View.INVISIBLE);

                } catch (IabHelper.IabAsyncInProgressException e) {
                    alert("Error launching purchase flow. Another async operation in progress.");
                }

                break;


        }
    }

    void checkAccount(final String phoneNumber) {

        if (phoneNumber == null) {
            return;
        }

        btnOk.setVisibility(View.INVISIBLE);
        txtPhone.setVisibility(View.INVISIBLE);


        irancellApi.isSubscribed(phoneNumber, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                JsonObject result = new JsonParser().parse(new String(responseBody)).getAsJsonObject();

                if (result.get("IsSuccessfull").getAsBoolean()) { // if is false

                    if (result.get("Result").getAsBoolean()) {

                        goToHome();

                    } else {
                        sendMessageDialog();
                    }

                } else {

                    Toast.makeText(IntroActivity.this, result.get("Message").getAsString(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(IntroActivity.this, "خطا در برقراری ارتباط", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFinish() {
                super.onFinish();

                btnOk.setVisibility(View.VISIBLE);
                txtPhone.setVisibility(View.VISIBLE);
            }
        });

    }

    void sendMessageDialog() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(new ContextThemeWrapper(IntroActivity.this, R.style.myDialog));
        builder1.setMessage(
                "جهت اشتراک در الفچین عدد ۱ را به ۷۳۸۷۳۷ ارسال کنید."
        );
        builder1.setTitle("اشتراک");
        //builder1.setCancelable(false);

        builder1.setPositiveButton(
                "ارسال",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Uri sms_uri = Uri.parse("smsto:738737");
                        Intent sms_intent = new Intent(Intent.ACTION_SENDTO, sms_uri);
                        sms_intent.putExtra("sms_body", "1");
                        startActivity(sms_intent);

                        dialog.cancel();
                        IntroActivity.this.finish();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    //
    // Listener that's called when we finish querying the items and subscriptions we own
    private IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d(TAG, "Query inventory finished.");

            // Have we been disposed of in the meantime? If so, quit.
            if (mHelper == null) return;

            // Is it a failure?
            if (result.isFailure()) {
                //alert("Failed to query inventory: " + result);
                // return;
            }
            if (result.isSuccess()) {


            }

            Log.d(TAG, "Query inventory was successful.");

            /*
             * Check for items we own. Notice that for each purchase, we check
             * the developer payload to see if it's correct! See
             * verifyDeveloperPayload().
             */

            // Do we have the premium upgrade?
            Purchase premiumPurchase = inventory.getPurchase(SKU_PREMIUM);
            mIsPremium = (premiumPurchase != null && verifyDeveloperPayload(premiumPurchase));

            if (mIsPremium) {

                if (!localStorage.getSharedPreferences().getBoolean("disabelCharging", false)) {

                    DisableAccount(localStorage.getSharedPreferences().getString("phoneNumber", ""));

                } else {
                    // go to app
                    goToHome();
                }
            } else {
                btnOk.setVisibility(View.VISIBLE);
                txtPhone.setVisibility(View.VISIBLE);
            }

        }
    };

    @Override
    public void receivedBroadcast() {
        // Received a broadcast notification that the inventory of items has changed
        Log.d(TAG, "Received broadcast notification. Querying inventory.");
        try {
            mHelper.queryInventoryAsync(mGotInventoryListener);
        } catch (IabHelper.IabAsyncInProgressException e) {
            alert("Error querying inventory. Another async operation in progress.");
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);
        if (mHelper == null) return;

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        } else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }

    /**
     * Verifies the developer payload of a purchase.
     */
    private boolean verifyDeveloperPayload(Purchase p) {
        String verifyPayload = p.getDeveloperPayload();

        if (verifyPayload.equals(payload)) {
            return true;
        }

        return false;
    }

    // Callback for when a purchase is finished
    private IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);
            btnOk.setVisibility(View.VISIBLE);


            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            if (result.isFailure()) {
                alert("خطا در پرداخت.");
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                alert("خطا در تایید پرداخت");
                return;
            }

            Log.d(TAG, "Purchase successful.");

            if (purchase.getSku().equals(SKU_PREMIUM)) {
                // bought the premium upgrade!
                Log.d(TAG, "Purchase is premium upgrade. Congratulating user.");
                //alert("Thank you for upgrading to premium!");
                mIsPremium = true;

                DisableAccount(phoneNumber);
            }
        }
    };

    void DisableAccount(final String phoneNumber) {


        btnOk.setVisibility(View.INVISIBLE);
        txtPhone.setVisibility(View.INVISIBLE);

        localStorage.saveLocalData("phoneNumber", phoneNumber);

        irancellApi.disabelCharging(phoneNumber, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                JsonObject result = new JsonParser().parse(new String(responseBody)).getAsJsonObject();

                if (result.get("IsSuccessfull").getAsBoolean()) { // if is false

                    if (result.get("Result").getAsBoolean()) {

                        localStorage.saveLocalData("disabelCharging", true);

                    }

                } else {

                    Toast.makeText(IntroActivity.this, result.get("Message").getAsString(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Toast.makeText(IntroActivity.this, "خطا در برقراری ارتباط", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFinish() {

                goToHome();


            }
        });
    }


    // We're being destroyed. It's important to dispose of the helper here!
    @Override
    public void onDestroy() {
        super.onDestroy();

        // very important:
        if (mBroadcastReceiver != null) {
            unregisterReceiver(mBroadcastReceiver);
        }

        // very important:
        Log.d(TAG, "Destroying helper.");
        if (mHelper != null) {
            mHelper.disposeWhenFinished();
            mHelper = null;
        }
    }

    private void alert(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    void goToHome() {

        localStorage.saveLocalData("pay", true);


        //startActivity(new Intent(IntroActivity.this, UnityPlayerActivity.class));
        //finish();
    }

}
