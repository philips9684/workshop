package com.example.hp.exworkshop;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Register_Activity extends AppCompatActivity {

    private EditText Dis, Use, Pas, Pac;
    private Button Reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Dis = (EditText) findViewById(R.id.dn);
        Use = (EditText) findViewById(R.id.un);
        Pas = (EditText) findViewById(R.id.pw);
        Pac = (EditText) findViewById(R.id.pwc);
        Reg = (Button) findViewById(R.id.btreg);

        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(setVali()){
                    new Regiter
                            (Use.getText().toString()
                            ,Pas.getText().toString()
                            ,Pac.getText().toString()
                            ,Dis.getText().toString())
                            .execute();
                }else{
                    Toast.makeText(Register_Activity.this,"Enter Plz",Toast.LENGTH_LONG).show();
                }
            }
        });
            }





    private boolean setVali() {
        String display = Dis.getText().toString();
        String User = Use.getText().toString();
        String Pass = Pas.getText().toString();
        String PassC = Pac.getText().toString();

        if(display.isEmpty()) return false;
        if(User.isEmpty()) return false;
        if(Pass.isEmpty()) return false;
        if(!Pass.equals(PassC)) return false;
        else
        return true;
    }

    private class Regiter extends AsyncTask<Void,Void,String>{

        private String Usern,Passw,PasswC,DisN;

        public Regiter(String usern, String passw, String passwC, String disN) {
            this.Usern = usern;
            this.Passw = passw;
            this.PasswC = passwC;
            this.DisN = disN;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(Register_Activity.this,s,Toast.LENGTH_LONG).show();
            try{
                JSONObject rootObj = new JSONObject(s);
                if(rootObj.has("result")){
                    JSONObject resultObj= rootObj.getJSONObject("result");
                    if(resultObj.getInt("result")==1){
                        Toast.makeText(Register_Activity.this,resultObj.getString("result_desc"),Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(Register_Activity.this,resultObj.getString("result_desc"),Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (JSONException e) {

            }
        }

        @Override
        protected String doInBackground(Void... voids) {

            OkHttpClient client = new OkHttpClient();
            Request request;
            Response response;

            RequestBody requestBody = new FormBody.Builder()
                    .add("username",Usern)
                    .add("password",Passw)
                    .add("password_con",PasswC)
                    .add("display_name",DisN)
                    .build();

            request = new Request.Builder()
        .url("http://kimhun55.com/pollservices/signup.php")
        .post(requestBody).build();

            try{
                response = client.newCall(request).execute();
                if(response.isSuccessful()){
                    return response.body().string();
                }
            }catch (IOException ex){
                ex.printStackTrace();
            }




            return null;
        }
    }
}
