package com.example.democashfree.Api;



import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LoginService {

    String token = "Authorization:Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTI2MSwicGhvbmUiOiIrOTE4ODg0ODMxMjgzIiwidXJsIjoidGVzdC50aGVjbGFzc3Jvb20uYml6Iiwib3JnSWQiOiI0Y2IyNTA5ZC03MGY1LTQzNWUtODc5Mi1kMjQ5Mzc3NDNiNTMiLCJicm93c2VyTG9naW5Db2RlIjpudWxsLCJkZXZpY2VMb2dpbkNvZGUiOiIrOTE4ODg0ODMxMjgzMTI2MTM4NmM2YzE2LWRkMjItNDM5ZC04MGM1LWJiOGJjMTJiYWViNSIsImlhdCI6MTY0OTA2MjIwOX0.EJAeiepOjIEy7z3Gz6Ktc4E68ZTT13r8se2ziBX7VTI";
    String link = "orgurl:test.theclassroom.biz";

//    //get user kyc
//    @Headers({token, link})
//    @GET("fee/get-user-kyc")
//    Call<GetUserKycResponse> getUserCall();
//
//
    //wallet filter
    @Headers({token, link})
    @GET("fee/filterwallet")
    Call<GetFilterWalletResponse> filterWalletCall(@Query("status") String status);

//    //deletedocument
//
//    @Headers({token, link})
//    @DELETE("fee/delete-kyc-doc")
//    Call<DeleteDocument> deleteCall(@Query("key") String key);
//
//    //wallet
//    @Headers({token, link})
//    @GET("fee/wallet")
//    Call<GetWalletResponse> getWalletCall();
//    //paid
//    @Headers({token, link})
//    @GET("fee/filterwallet")
//    Call<HistoryResponse> historyWalletCall(@Query("status") String status);
//
//    //generate otp
//    @Headers({token, link})
//    @POST("fee/generateKycOtp")
//    Call<GenerateOtpResponse> otp(@Body GetOtpRequest getOtpRequest);
//
//    //verify
//    @Headers({token, link})
//    @POST("fee/verifyKycOtp")
//    Call<VerifyResponse> verifyCall(@Body VerifyRequest verifyRequest);
//
//    //updateUserKYC
//    @Headers({token, link})
//    @POST("fee/update-user-kyc")
//    Call<UpdateUserKycResponse> updateCall(@Body UpdateUserKycRequest userKycRequest);
//    // upload doc
//
//    @Multipart
//    @Headers({token, link})
//    @POST("fee/upload-kyc-doc")
//    Call<DocUploadResponse> docCall(@Part MultipartBody.Part image);
//
//
//
//
    @Headers({token, link})
    @GET("fee/getcfrt")
    Call<GetCFRTResponse> getCFRTCall(@Query("orderId") String orderId, @Query("orderAmount") int orderAmount, @Query("orderCurrency") String orderCurrency, @Query("id") int id);

//    @GET("{pincode}")
//    Call<List<PincodeResponse>> PINCODE_RESPONSE_CALL(@Path("pincode") Long pincode);


}
