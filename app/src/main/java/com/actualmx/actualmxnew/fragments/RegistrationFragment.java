package com.actualmx.actualmxnew.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.actualmx.actualmxnew.R;
import com.actualmx.actualmxnew.utill.ConnectionManager;
import com.actualmx.actualmxnew.utill.ExecutePostRequest;
import com.actualmx.actualmxnew.utill.PostDataExecute;
import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by ram on 11/8/16.
 */
public class RegistrationFragment extends Fragment implements PostDataExecute {
    private View rootView;
    private EditText fname;
    private EditText email;
    private EditText password;
    private  EditText confirmpass;
    private Button submit;
    private TextView cancle;
    private String firstName,pass,email_id,cpass;
    private ProgressBar progressBar;
    private boolean isInternet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.registration_layout, container, false);
        init(rootView);
        isInternet =  ConnectionManager.isNetworkOnline(getActivity());
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Canceled",Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()) {
                    onRequestLogin();

                }
            }
        });
        return rootView;

    }


    private void init(View v) {
        fname = (EditText)v.findViewById(R.id.txt_firstname);
        email = (EditText)v.findViewById(R.id.txt_email);
        password = (EditText)v.findViewById(R.id.txt_password);
        confirmpass = (EditText)v.findViewById(R.id.txt_confirmpassword);
        submit = (Button)v.findViewById(R.id.button_registration);
        cancle = (TextView)v.findViewById(R.id.text_sign_up);
        firstName =fname.getText().toString();
        pass = password.getText().toString();
        email_id =email.getText().toString();
        cpass = confirmpass.getText().toString();
        progressBar = (ProgressBar) v.findViewById(R.id.progressOther);
    }

    private boolean isValid(){
        init(rootView);
        if(firstName.equalsIgnoreCase("") && !isValidNmae(firstName)){
            Toast.makeText(getActivity(),"Please enter valid first Name",Toast.LENGTH_SHORT).show();
        return false;
        } else if(!isEmailValid(email_id)){
            Toast.makeText(getActivity(),"please enter valid email id",Toast.LENGTH_SHORT).show();
            return false;
        } else if(pass.equals("") || pass.length()<5 || !pass.equals(cpass)){
            Toast.makeText(getActivity(),"please enter valid password",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public boolean isEmailValid( String email ) {
        String regExpn = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if ( matcher.matches() )
            return true;
        else
            return false;
    }

    private boolean isValidNmae(String str){

        if(str.length()<=1){
            return false;
        }

        for( int i = 0; i < str.length() ;i++ ){

            if( (str.charAt(i)<65&&str.charAt(i)!=32)||(str.charAt(i)>90&&str.charAt(i)<97)||(str.charAt(i)>122) ){
                return false;
            }
        }
        return true;
    }

    private void onRequestLogin(){

            if (isInternet){
                progressBar.setVisibility(View.VISIBLE);

                RequestBody formBody = new FormEncodingBuilder()
                        .add("category_name", "noticias")
                        .add("page", String.valueOf(1))
                        .build();
                Gson gson = new Gson();
                Log.e("myname",email_id);
                String JsonBody = gson.toJson(new RegistrationData(firstName,email_id,pass, pass));
                new ExecutePostRequest(getActivity(), 1, formBody,this , JsonBody)
                        .execute("http://www.actualmx.com/backup/register.php");

            }else {
                final Toast tag = Toast.makeText(getActivity().getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT);
                tag.show();
                new CountDownTimer(9000, 1000)
                {
                    public void onTick(long millisUntilFinished) {tag.show();}
                    public void onFinish() {tag.show();}
                }.start();
            }
    }


    @Override
    public void onPostRequestSuccess(int method, String resp) {
        Toast.makeText(getActivity(),resp,Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onPostRequestFailed(int method, String resp) {
        Toast.makeText(getActivity(),resp,Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
    }

    public class RegistrationData{
        String user_name;
        String user_email;
        String user_user_password;
        String user_user_Confirm_password;

        public RegistrationData(String user_email, String user_name, String user_user_Confirm_password, String user_user_password) {
            this.user_email = user_email;
            this.user_name = user_name;
            this.user_user_Confirm_password = user_user_Confirm_password;
            this.user_user_password = user_user_password;
        }



        public String getUser_email() {
            return user_email;
        }

        public String getUser_name() {
            return user_name;
        }

        public String getUser_user_Confirm_password() {
            return user_user_Confirm_password;
        }

        public String getUser_user_password() {
            return user_user_password;
        }
    }
}
