package com.example.hemil.rentapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.hemil.rentapp.API.RestApiClass;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import POJO.User;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by hemil on 5/12/2016.
 */
public class SignInActivity extends MainActivity {

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private String email;
    private User user;
    private long userId;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.hemil.rentapp",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = null;
                try {
                    md = MessageDigest.getInstance("SHA");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                md.update(signature.toByteArray());
                String s = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.v("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                Log.i("K", s);
                Log.d("K", s);
                Log.e("K", s);
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (Exception e) {

        }
        super.onCreate(savedInstanceState);
        findViewById(R.id.listView_home).setVisibility(View.INVISIBLE);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.sign_in, null, false);
        mDrawer.addView(contentView, 0);

        loginButton = (LoginButton)findViewById(R.id.login_button);

        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Success","onSuccess");
                Log.d("Success", "Facebook User ID: " + loginResult.getAccessToken().getUserId() + "\n" + "Auth Token: " + loginResult.getAccessToken().getToken());

                userId = Long.valueOf(loginResult.getAccessToken().getUserId());

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            email = object.getString("email");
                            Log.d("Facebook Email", email);

                            String req_token = sharedPreferences.getString("RegToken", "");

                            user = new User(userId, email, req_token);

                            SendRegistrationRequest sendReq = new SendRegistrationRequest();
                            sendReq.execute();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Bundle params = new Bundle();
                params.putString("fields", "id, email, name, link");
                request.setParameters(params);
                request.executeAsync();

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
//                info.setText("Facebook User ID: " + loginResult.getAccessToken().getUserId() + "\n" + "Auth Token: " + loginResult.getAccessToken().getToken());
//                info.append("\nGCM ID: " + sharedPreferences.getString("GCM", ""));

            }

            @Override
            public void onCancel() {
//                info.setText("Login attempt canceled.");
                Log.d("Oncancel","oncancel");
            }

            @Override
            public void onError(FacebookException e) {
//                info.setText("Login attempt failed.");
                Log.d("Error","onError");

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public class SendRegistrationRequest extends AsyncTask<String, Void, Void> {

        RestAdapter restAdapter;
        @Override
        protected void onPreExecute(){
            final OkHttpClient okHttpClient = new OkHttpClient();
            String url = "http://ec2-54-153-29-131.us-west-1.compute.amazonaws.com:8080";
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(url)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setClient(new OkClient(okHttpClient))
                    .build();
        }

        @Override
        protected Void doInBackground(String... params) {
            final RestApiClass restApiClass = restAdapter.create(RestApiClass.class);
            JSONObject jsonObject = restApiClass.sendFacebookDetails(user);
            Log.d("Response", jsonObject.toString());
            return null;
        }

        protected void onPostExecute(){
            Log.d("Inside", "PostExceute");
        }

    }

}
