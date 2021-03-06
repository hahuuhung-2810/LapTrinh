package com.hungno.adeducation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DanhSachHS extends AppCompatActivity implements AdapterView.OnItemClickListener {
EditText eMSSV,eHoten,eKhoa,eLop,edtSearch;
Button btnThem;
ProgressDialog progressDialog;
ListView listView;
SimpleAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_hs);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        eMSSV=findViewById(R.id.edtMSSV);
        eHoten=findViewById(R.id.edtHoten);
        eKhoa=findViewById(R.id.edtKhoa);
        eLop=findViewById(R.id.edtLop);
        edtSearch=findViewById(R.id.edtSearch);

        btnThem=findViewById(R.id.btnThem);

        progressDialog=new ProgressDialog(DanhSachHS.this);
        progressDialog.setMessage("Loading...");

    btnThem.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            themHocSinh();
            progressDialog.show();
        }
    });


        listView=findViewById(R.id.lv_DanhSachHS);
        listView.setOnItemClickListener(this);
        getDSHS();
    }

    private void getDSHS() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbzlNQfYQ8rhKcq86wJLQyvK5DDoO_azVx82IQrP1ThI7jN_uMb6m6EGN-xbLCZhSMd8PA/exec?action=getItem", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseItemts(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        int soketTimeout=50000;
        RetryPolicy retryPolicy=new DefaultRetryPolicy(soketTimeout,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void parseItemts(String jsonResposnce) {
        ArrayList<HashMap<String,String>> list=new ArrayList<>();
        try {
            JSONObject jsonObject=new JSONObject(jsonResposnce);
            JSONArray jsonArray=jsonObject.getJSONArray("Diemdanh");

            for (int i=0; i<jsonArray.length();i++){
                JSONObject Json=jsonArray.getJSONObject(i);

                String MSSV=Json.getString("MSSV");
                String HoTen=Json.getString("HoTen");
                String Khoa=Json.getString("Khoa");
                String Lop=Json.getString("Lop");

                HashMap<String,String> DSHS=new HashMap<>();
                DSHS.put("MSSV",MSSV);
                DSHS.put("HoTen",HoTen);
                DSHS.put("Khoa",Khoa);
                DSHS.put("Lop",Lop);

                list.add(DSHS);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        listAdapter=new SimpleAdapter(this,list,R.layout.list_dshs,
                new String[]{"MSSV","HoTen","Khoa","Lop"},new int[]{R.id.txtMSSV,R.id.txtHoTen,R.id.txtKhoa,R.id.txtLop});
        listView.setAdapter(listAdapter);
        progressDialog.hide();
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DanhSachHS.this.listAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void themHocSinh() {
         String sMSSV = eMSSV.getText().toString();
         String sTen = eHoten.getText().toString();
         String sKhoa = eKhoa.getText().toString();
         String sLop = eLop.getText().toString();

            StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbyP3n0lhKwVikOtFK_5QoWLk7o6T9GGS-5iekWUTtohPgkGSs0Gtu2umAOZykrJp8ZiMw/exec", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                startActivity(new Intent(getApplicationContext(),DanhSachHS.class));
            progressDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params=new HashMap<>();
                params.put("action","addStudent");
                params.put("vMSSV",sMSSV);
                params.put("vHoTen",sTen);
                params.put("vKhoa",sKhoa);
                params.put("vLop",sLop);
                return params;
            }
        };
        int sk=50000;
        RetryPolicy retryPolicy=new DefaultRetryPolicy(sk,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(this,DetailDSHS.class);
        HashMap<String,String>map=(HashMap)parent.getItemAtPosition(position);
        String MSSV=map.get("MSSV").toString();
        String HoTen=map.get("HoTen").toString();
        String Khoa=map.get("Khoa").toString();
        String Lop=map.get("Lop").toString();

        intent.putExtra("MSSV",MSSV);
        intent.putExtra("HoTen",HoTen);
        intent.putExtra("Khoa",Khoa);
        intent.putExtra("Lop",Lop);
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}