package com.example.upperskills.test;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

public class FaceConnect extends AppCompatActivity {
    LoginButton loginButton;
    CallbackManager callbackManager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fb_layout);
        loginButton = (LoginButton) findViewById(R.id.fb_login);

        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // textView.setText("Login Succeed \n" + loginResult.getAccessToken().getUserId() + "\n" + loginResult.getAccessToken().getToken());
                setFacebookData(loginResult);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }

    private void setFacebookData(final LoginResult loginResult)
    {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        try {
                            Log.i("Response",response.toString());



                            String firstName = response.getJSONObject().getString("first_name");
                            String lastName = response.getJSONObject().getString("last_name");
                            String gender = response.getJSONObject().getString("gender");

                            Intent resultData = new Intent();
                            resultData.putExtra("firstName", firstName);
                            resultData.putExtra("lastName", lastName);
                            setResult(Activity.RESULT_OK, resultData);
                            finish();



                        } catch (JSONException e) {
                            Log.i("Json Erreur",e.toString());
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,first_name,last_name,birthday,gender");
        request.setParameters(parameters);
        request.executeAsync();


    }



    protected void onActivityResult(int reqCode, int resCode, Intent data){
        callbackManager.onActivityResult(reqCode,resCode,data);
    }

}


