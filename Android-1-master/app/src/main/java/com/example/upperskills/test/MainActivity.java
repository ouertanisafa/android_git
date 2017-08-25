package com.example.upperskills.test;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import java.util.Calendar;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity  {
    private static final String Tag = "MainActivity";
    private TextView dateDeNaissance;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private  TextView nom,prenom,cin,email,adresse ;
    final Context context = this;
    int MY_REQUEST_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.home);

        //region button_suivant
        nom =(TextView) findViewById(R.id.nom);
        prenom=(TextView) findViewById(R.id.prenom);
        dateDeNaissance =(TextView) findViewById(R.id.date);
        cin= (TextView) findViewById(R.id.cin);
        email=(TextView) findViewById(R.id.email);
        adresse =(TextView) findViewById(R.id.adresse);


        ImageButton suivantButton = (ImageButton) findViewById(R.id.suivant);
        ImageButton facebook = (ImageButton) findViewById(R.id.facebook);

        suivantButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                //// controle de saisie

                if(nom.getText().toString().trim().matches("") && nom.getText().toString().length()<3){

                    showDialog("nom");


                }else if(prenom.getText().toString().trim().matches("") && prenom.getText().toString().length()<3){
                    showDialog("Prenom");
                }else if(dateDeNaissance.getText().toString().trim().matches("")){
                    showDialog("Date de naissance");
                } else if(!email.getText().toString().trim().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
                    showDialog("Email");
                }else if(!cin.getText().toString().trim().matches("[0-9]{8}")){
                    showDialog("Cin");
                }else if(adresse.getText().toString().trim().matches("")){
                    showDialog("Adresse");
                }else startActivity(new Intent(MainActivity.this,Main2Activity.class));
                /////






               //startActivity(new Intent(MainActivity.this, Main2Activity.class));

              /*  Log.d("nom "," "+nom.getText().toString());
                try {
                    sendPostReq(nom.getText().toString(), prenom.getText().toString(), dateDeNaissance.getText().toString()
                            ,email.getText().toString(),cin.getText().toString(),adresse.getText().toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
            }
        });
        facebook.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivityForResult(new Intent(MainActivity.this,FaceConnect.class),MY_REQUEST_ID);

            }
        });


        //endregion

        //region date_de_naissance

        dateDeNaissance = (TextView) findViewById(R.id.date);
        nom = (TextView) findViewById(R.id.nom);
        dateDeNaissance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal=Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MainActivity.this,

                       mDateSetListener,
                        year,month,day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.CYAN));
                dialog.show();


            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                //Log.d(Tag, "onDateSet: date " + i + "/" + i1 + "/" + i2);
                month=month+1;
                String date = day + "/" + month + "/" + year;
                dateDeNaissance.setText(date);
                //Log.d(Tag, "a="+nom.getText());
             }
            };

//endregion



        }

    /*private void sendPostReq(final String name, String prenom, String date_de_naissance, String email, String cin, String adresse
    ) throws JSONException {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = "http://10.0.2.2:3000/name";
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("nom", name);
        jsonBody.put("prenom",prenom);
        jsonBody.put("date_de_naissance",date_de_naissance);
        //jsonBody.put("telephone");
        jsonBody.put("email",email);
        jsonBody.put("cin",cin);
        jsonBody.put("adresse",adresse);
        final String requestBody = jsonBody.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("VOLLEY", response);


                    showDialog("Succés");


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
                showDialog("echec");

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                    // can get more details such as response.headers
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        requestQueue.add(stringRequest);
    }*/

    private void showDialog(String msg){
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);

        // set title
        alertDialogBuilder.setTitle("Erreur");

        // set dialog message
        alertDialogBuilder
                .setMessage("Veuillez remplire le champ : "+msg+" !!")
                .setCancelable(false)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        dialog.dismiss();
                    }
                });


        // create alert dialog
        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();


    }
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == MY_REQUEST_ID) {
            if (resultCode == RESULT_OK) {
                nom.setText(data.getStringExtra("firstName"));
                prenom.setText(data.getStringExtra("lastName"));


            }
        }
    }



}

