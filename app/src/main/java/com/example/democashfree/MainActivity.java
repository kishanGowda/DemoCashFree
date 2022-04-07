package com.example.democashfree;

import static com.cashfree.pg.CFPaymentService.PARAM_APP_ID;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_EMAIL;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_NAME;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_PHONE;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_AMOUNT;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_CURRENCY;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_ID;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_NOTE;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cashfree.pg.CFPaymentService;
import com.cashfree.pg.ui.gpay.GooglePayStatusListener;
import com.example.democashfree.Api.ApiClient;
import com.example.democashfree.Api.GetCFRTResponse;
import com.example.democashfree.Api.GetFilterWalletResponse;
import com.example.democashfree.Api.LoginService;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    //for order id
    GetFilterWalletResponse getFilterWalletResponse;
    LoginService loginService;
    Retrofit retrofit;


    //
    Button getToken;
    //pay button
    Button pay;
    Map<String, String> params;
    private static final String TAG = "CashFree";
    String orderID;
    String amounts;
    int id;
    String token;

    //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // calling method for id and order id
        apiInIt();
        filterWallet();


        getToken=findViewById(R.id.getoken);
        getToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTokenResponse();
            }
        });

        //

        pay=findViewById(R.id.pay);







        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                paymethod(view);
            }
        });

    }
    private void paymethod(View view) {
        String stage = "PROD";
        CFPaymentService cfPaymentService = CFPaymentService.getCFPaymentServiceInstance();
        cfPaymentService.setOrientation(0);

        switch (view.getId()) {
            case R.id.pay: {
                //Log.i("Token inside Switch", token);
                cfPaymentService.selectUpiClient("com.google.android.apps.nbu.paisa.user");
                cfPaymentService.upiPayment(MainActivity.this, getInputParams(), token, stage);
//                Log.i("TAG", "paymethod: ");
//                Log.i("TAG", token);
//                cfPaymentService.doPayment(MainActivity.this, params, token, stage);
                cfPaymentService.setOrientation(0);

                break;
            }


        }
    }




    @Override
    protected  void  onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Same request code for all payment APIs.
        Log.d(TAG, "ReqCode : " + CFPaymentService.REQ_CODE);
        Log.d(TAG, "API Response : ");
        //Prints all extras. Replace with app logic.
        if (data != null) {
            Bundle  bundle = data.getExtras();
            if (bundle != null)
                for (String  key  :  bundle.keySet()) {
                    if (bundle.getString(key) != null) {
                        Log.d(TAG, key + " : " + bundle.getString(key));
                    }
                }
        }
    }

    private Map<String, String> getInputParams() {
        String appId = "132251d7dc08cd6f7ae20edc8a152231";
        String orderId = orderID;
        String orderAmount = amounts;
        String orderNote = "Test Order";
        String customerName = "John Doe";
        String customerPhone = "9900012345";
        String customerEmail = "test@gmail.com";


        params = new HashMap<>();
        params.put(PARAM_APP_ID, appId);
        params.put(PARAM_ORDER_ID, orderId);
        params.put(PARAM_ORDER_AMOUNT, orderAmount);
        params.put(PARAM_ORDER_NOTE, orderNote);
        params.put(PARAM_CUSTOMER_NAME, customerName);
        params.put(PARAM_CUSTOMER_PHONE, customerPhone);
        params.put(PARAM_CUSTOMER_EMAIL, customerEmail);
        params.put(CFPaymentService.PARAM_NOTIFY_URL, "http://your.backend.webhook");
        params.put(PARAM_ORDER_CURRENCY, "INR");
        return params;
    }





    public  void  filterWallet() {
        Call<GetFilterWalletResponse> call = loginService.filterWalletCall("Link Sent");
        call.enqueue(new Callback<GetFilterWalletResponse>() {
            @Override
            public void onResponse(Call<GetFilterWalletResponse> call, Response<GetFilterWalletResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "token change", Toast.LENGTH_SHORT).show();

                }
                try {
                    getFilterWalletResponse = response.body();

                    long millis = System.currentTimeMillis();
                    long seconds = millis / 1000;
                    Log.i("sec", String.valueOf(seconds));
                    String orgId= getFilterWalletResponse.transactions.get(0).orgId;
                    int userId=getFilterWalletResponse.transactions.get(0).userId;
                    String order=orgId+userId+seconds;
                    orderID=order;
                    Log.i("oderId", order);
                    id=getFilterWalletResponse.transactions.get(0).id;
                    Log.i("idss", String.valueOf(id));
                      amounts=getFilterWalletResponse.transactions.get(0).amount;
                    Log.i("amount", amounts);

                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i(TAG, e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<GetFilterWalletResponse> call, Throwable t) {
                Log.e("msg", String.valueOf(t.getMessage()));
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void apiInIt()
    {
        retrofit= ApiClient.getRetrofit();
        loginService= ApiClient.getApiService();
    }


    public String getTokenResponse() {
        ProgressDialog dialog=new ProgressDialog(MainActivity.this);
        dialog.setMessage("Loading");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();

        Call<GetCFRTResponse> tokenResponseCall = loginService.getCFRTCall(orderID, Integer.valueOf(amounts), "INR",id);
        tokenResponseCall.enqueue(new Callback<GetCFRTResponse>() {
            @Override
            public void onResponse(Call<GetCFRTResponse> call, Response<GetCFRTResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                }
                GetCFRTResponse tockenResponse = response.body();
                String t = tockenResponse.body.cftoken;

                final Handler handler = new Handler();
                dialog.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        token=t;
                        Log.i("TAG", "enterd");
                        Log.i("TAG", String.valueOf(token));
                        dialog.dismiss();
                    }

                }, 10000);
                Log.i("TAG", "enterd after");

                Log.i("orderId", orderID);



            }


            @Override
            public void onFailure(Call<GetCFRTResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error :((", Toast.LENGTH_LONG).show();


            }
        });
        return token;
    }



}