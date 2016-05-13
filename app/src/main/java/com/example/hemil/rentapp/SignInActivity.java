package com.example.hemil.rentapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by hemil on 5/12/2016.
 */
public class SignInActivity extends MainActivity {

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

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

//                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
//
//                    @Override
//                    public void onCompleted(JSONObject object, GraphResponse response) {
//                        try {
//                            email = object.getString("email");
//                            Log.d("Email", email);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//                Bundle params = new Bundle();
//                params.putString("fields", "id, email, name, link");
//                request.setParameters(params);
//                request.executeAsync();

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
}
