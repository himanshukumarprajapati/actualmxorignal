package com.actualmx.actualmxnew.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.actualmx.actualmxnew.R;
import com.actualmx.actualmxnew.activities.MyGridActivity;
import com.actualmx.actualmxnew.utill.ConnectionManager;
import com.actualmx.actualmxnew.utill.ExecutePostRequest;
import com.actualmx.actualmxnew.utill.PostDataExecute;
import com.actualmx.actualmxnew.utill.Pref;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


/**
 * Created by ram on 11/8/16.
 */
public class LoginFragment extends Fragment implements View.OnClickListener,PostDataExecute,GoogleApiClient.OnConnectionFailedListener {
    private View rootView;
    private ImageView fbbutton;
    private ImageView googleBTN;
    private Button login;
    private TextView register;
    private EditText uNmae,ext_password;
    private static final int RC_SIGN_IN = 0;
    private CallbackManager callbackManager;
    private GoogleApiClient mGoogleApiClient;
    private boolean isInternet;
    private ProgressBar progressBar;
    private Pref mpPref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.login_layout, container, false);
        init(rootView);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        AppEventsLogger.activateApp(getActivity().getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        isInternet =  ConnectionManager.isNetworkOnline(getActivity());
        fbbutton.setOnClickListener(this);
        googleBTN.setOnClickListener(this);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        mpPref = new Pref(getActivity());
        ////////////////////////////////////////////////////////////////Google SignIn Initilisation/////
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

// Build a GoogleApiClient with access to GoogleSignIn.API and the options above.
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        ////////////////////////////////////////////////////////////////
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code

                        AccessToken accessToken = loginResult.getAccessToken();

                        GraphRequest request = GraphRequest.newMeRequest(
                                accessToken,
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {

                                        try {
                                            JSONObject jObject = response.getJSONObject();

                                            String data = "name = " + jObject.getString("name");
                                            data += "\nid = " + jObject.getString("id");
                                            data += "\nemail = " + jObject.getString("email");
                                            data += "\nprofile pic = " + "http://graph.facebook.com/"
                                                    + jObject.getString("id") + "/picture?type=large";

                                            Toast.makeText(getActivity(),
                                                    "Welcome " + jObject.getString("name"),
                                                    Toast.LENGTH_LONG).show();

                                            String[] parts = jObject.getString("name").split("");
                                            String fName = parts[0]; // 004
                                            String lName = parts[1].length()>1?parts[1]:"";
                                            Intent intent = new Intent(getActivity(),MyGridActivity.class);
                                            intent.putExtra("from","login");
                                            startActivity(intent);
                                            getActivity().finish();
                                           /* pref.saveData(Pref.userImage, "http://graph.facebook.com/"
                                                    + jObject.getString("id") + "/picture?type=large");*/

                                            Log.e("facebook data", data);
                                        } catch (Exception eee) {
                                            Toast.makeText(getActivity(),"Please enter email id and password",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,link,email");
                        request.setParameters(parameters);
                        request.executeAsync();

                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Toast.makeText(getActivity(),"Please enter email id and password",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Toast.makeText(getActivity(),"Please enter email id and password",Toast.LENGTH_SHORT).show();
                    }
                });
        //////////////////////////////////////////////
        return rootView;
        }

    private void init(View v) {
        fbbutton  = (ImageView) v.findViewById(R.id.fblogin);
        googleBTN  = (ImageView) v.findViewById(R.id.id_signgoogle);
        register  = (TextView) v.findViewById(R.id.text_sign_up);
        login  = (Button) v.findViewById(R.id.login_btn);
        uNmae  = (EditText) v.findViewById(R.id.edit_uid);
        ext_password  = (EditText) v.findViewById(R.id.edit_password);
        progressBar = (ProgressBar) v.findViewById(R.id.progressOther);


    }
    private void onSignInClicked() {
        // User clicked the sign-in button, so begin the sign-in process and automatically
        // attempt to resolve any errors that occur.;
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Tag", "onActivityResult:" + requestCode + ":" + resultCode + ":" + data);

        if (requestCode == RC_SIGN_IN) {
//            Toast.makeText(getApplicationContext(), "RC SIGN IN MATCHED", Toast.LENGTH_SHORT).show();
            // If the error resolution was not successful we should not resolve further.
            if (resultCode == Activity.RESULT_OK) {
               // mShouldResolve = false;
//                Toast.makeText(getApplicationContext(), "Result Ok", Toast.LENGTH_SHORT).show();

                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                GoogleSignInAccount acct = result.getSignInAccount();
                String personName = acct.getDisplayName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();

                Log.e("Gplus User Detail", "email=" + personEmail + "\nname= " + personName
                        + "\nPhoto = " + personPhoto+"\nID" + personId);

                Toast.makeText(getActivity(), "Welcome " + personName, Toast.LENGTH_SHORT).show();

                /*String[] parts =personName.split(" ");
                String fName = parts[0]; // 004
                String lName = parts[1].length()>1?parts[1]:"";
                onRequestLoginbySocial(personEmail,fName,lName,"gmail");*/
                Intent intent = new Intent(getActivity(),MyGridActivity.class);
                intent.putExtra("from","login");
                startActivity(intent);
                getActivity().finish();
                //onRequestRegister();
                //onRequestLogin(personEmail,"qwerty");

            }

        } else {
           callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fblogin:
                LoginManager.getInstance().logInWithReadPermissions(this,
                        Arrays.asList("email", "public_profile", "user_friends"));
  /*              LoginManager.getInstance().logInWithReadPermissions(this,
                        Arrays.asList("email", "public_profile", "user_friends"));*/
                break;
            case R.id.id_signgoogle:
                onSignInClicked();
               /* Uri uri2 = Uri.parse("http://www.gmail.com"); // missing 'http://' will cause crashed
                Intent intent2 = new Intent(Intent.ACTION_VIEW, uri2);
                startActivity(intent2);*/
                break;
            case R.id.text_sign_up:
                RegistrationFragment mFragment = new  RegistrationFragment();
                getFragmentManager().beginTransaction().replace(R.id.id_container, mFragment).addToBackStack(null).commit();
                break;
            case R.id.login_btn:
               /* startActivity(new Intent(getActivity(), MyGridActivity.class));
                getActivity().finish();*/
                if(!isValid()) {
                String email = uNmae.getText().toString();
                    String pass = ext_password.getText().toString();
                    if (isInternet){
                        progressBar.setVisibility(View.VISIBLE);

                        RequestBody formBody = new FormEncodingBuilder()
                                .add("category_name", "noticias")
                                .add("page", String.valueOf(1))
                                .build();
                        Gson gson = new Gson();
                        Log.e("myname",email);
                        String JsonBody = gson.toJson(new LoginData(email, pass));
                        new ExecutePostRequest(getActivity(), 1, formBody,this , JsonBody)
                                .execute("http://www.actualmx.com/backup/login.php");

                    }else {
                        final Toast tag = Toast.makeText(getActivity().getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT);
                        tag.show();
                        new CountDownTimer(9000, 1000)
                        {
                            public void onTick(long millisUntilFinished) {tag.show();}
                            public void onFinish() {tag.show();}
                        }.start();
                    }
                } else {
                    Toast.makeText(getActivity(),"Please enter email id and password",Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }




    public boolean isValid() {

        return uNmae.getText().toString().equals("")&&ext_password.getText().toString().equals("");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPause() {
        super.onPause();

        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onPostRequestSuccess(int method, String resp) {

            String status ="";
        try {
            JSONObject jsonObject = new JSONObject(resp);
            status =jsonObject.getString("status");
            if(status.equalsIgnoreCase("success")) {
                JSONObject jsonObject1 =jsonObject.getJSONObject("user_Data");
                mpPref.setUserName("user",jsonObject1.getString("user_email"));
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(getActivity(), MyGridActivity.class);
                intent.putExtra("from", "login");
                startActivity(intent);
                getActivity().finish();
            } else {
                progressBar.setVisibility(View.GONE);
                uNmae.clearComposingText();
                ext_password.clearComposingText();
                Toast.makeText(getActivity(),"wrong user name or password" ,Toast.LENGTH_SHORT).show();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onPostRequestFailed(int method, String resp) {
        progressBar.setVisibility(View.GONE);
        uNmae.clearComposingText();
        ext_password.clearComposingText();

        Toast.makeText(getActivity(),"wrong user name or password" ,Toast.LENGTH_SHORT).show();
    }

    public class LoginData{
        String user_email;
        String user_password;

        public LoginData(String user_email, String user_pass) {
            this.user_email = user_email;
            this.user_password = user_pass;
        }

        public String getUser_email() {
            return user_email;
        }

        public void setUser_email(String user_email) {
            this.user_email = user_email;
        }

        public String getUser_pass() {
            return user_password;
        }

        public void setUser_pass(String user_pass) {
            this.user_password = user_pass;
        }
    }
}
