package blood.connect.kesha.firebasefcm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main2Activity extends AppCompatActivity {

    Button donars,requests;
    SharedPreferences sh;
    NavigationView navigationView;
    DrawerLayout mDrawer;
    ListView list;
    ActionBarDrawerToggle mDrawerToggle;
    FirebaseDatabase database;
    DatabaseReference users;
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        donars=(Button)findViewById(R.id.donars);
        requests=(Button)findViewById(R.id.requests);
        sh=getSharedPreferences("Signin",getApplicationContext().MODE_PRIVATE);
        String n=sh.getString("userna","");
        list=(ListView)findViewById(R.id.requests1);
        navigationView=(NavigationView)findViewById(R.id.navigation);
        View header=navigationView.getHeaderView(0);
        database=FirebaseDatabase.getInstance();
        users=database.getReference("Users");
        if(n.compareTo("")==0)
        {
            Menu m=navigationView.getMenu();


            m.findItem(R.id.Signout).setVisible(false);
            m.findItem(R.id.requests).setVisible(true);
            m.findItem(R.id.Signup).setVisible(true);
            }
        else
        {

            Menu m=navigationView.getMenu();

            m.findItem(R.id.Signout).setVisible(true);
            m.findItem(R.id.requests).setVisible(true);
            m.findItem(R.id.Signup).setVisible(true);
            TextView user=(TextView)header.findViewById(R.id.nametext);
            user.setText(n);
            ImageView img=(ImageView)header.findViewById(R.id.image);
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/fir-75aaa.appspot.com/o/profilepictures%2F"+user.getText().toString()+".jpg?alt=media&token=bae0cd9f-560e-4952-8910-e3a35da4fd40").into(img);
        }



        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle= new ActionBarDrawerToggle(this,mDrawer, R.string.Open, R.string.Close);
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        donars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExecuteTask ex=new ExecuteTask();
                ex.execute();
            }
        });

        requests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExecuteTask1 ex=new ExecuteTask1();
                ex.execute();
            }
        });
        navigationView=(NavigationView)findViewById(R.id.navigation);
        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {

                    case R.id.requests:
                        sh=getSharedPreferences("Signin",getApplicationContext().MODE_PRIVATE);
                        String n=sh.getString("userna","");
                        if(n.compareTo("")!=0) {
                            Intent in2 = new Intent(Main2Activity.this, generaterequest.class);
                            startActivity(in2);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"First sign in",Toast.LENGTH_SHORT).show();
                            Intent in3= new Intent(Main2Activity.this,MainActivity.class);
                            startActivity(in3);
                        }
                        break;

                    case R.id.Signup:
                        Intent in=new Intent(Main2Activity.this,signup.class);
                        startActivity(in);
                        break;
                    case R.id.Signout:
                        sh=getSharedPreferences("Signin",MODE_PRIVATE);
                        SharedPreferences.Editor editor1=sh.edit();
                        editor1.putString("userna","");
                        editor1.commit();
                        Intent in1=new Intent(Main2Activity.this,Main3Activity.class);
                        startActivity(in1);
                        finish();
                        break;

                }

                return false;
            }
        });



    }
    class ExecuteTask1 extends AsyncTask<String,Integer,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("http://argetim2k17.com/yef/allrequests.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String str;
                String l = "";

                while ((str = bufferedReader.readLine()) != null) {
                    l += str;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return l;
            }
            catch (Exception e)
            {

            }

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            // txt.setText(s);

            String[] n;
            String[] m;
            String[] b;
            String[] c;
            String[] me;


            try {
                JSONArray array = new JSONArray(s);
                if (array != null)
                {
                    int size = array.length();

                    n = new String[size];
                    //n=new String[size];
                    me = new String[size];
                    b = new String[size];

                    c = new String[size];
                    m = new String[size];
                    int i = 0;
                    int j = 0;
                    for (i = 0; j < array.length(); j++) {
                        JSONObject obj = array.getJSONObject(j);



                            n[j] = obj.getString("name");
                            m[j] = obj.getString("mobile");
                            c[j] = obj.getString("city");
                            me[j] = obj.getString("message");
                            b[j] = obj.getString("blood");



                    }
                    customrequests cs = new customrequests(Main2Activity.this, n, m, c,me, b);
                    list.setAdapter(cs);
                }

            } catch(JSONException e1){
                e1.printStackTrace();
            }



        }
    }
    class ExecuteTask extends AsyncTask<String,Integer,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("http://argetim2k17.com/yef/allprofile.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String str;
                String l = "";

                while ((str = bufferedReader.readLine()) != null) {
                    l += str;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return l;
            }
            catch (Exception e)
            {

            }

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            // txt.setText(s);

            String[] n;
            String[] m;
            String[] b;
            String[] c;
            String[] e;
            String[] d;

            try {
                JSONArray array = new JSONArray(s);
                if (array != null)
                {
                    int size = array.length();

                    n = new String[size];
                    //n=new String[size];
                    m = new String[size];
                    b = new String[size];
                    e = new String[size];
                    c = new String[size];
                    d = new String[size];
                    int i = 0;
                    int j = 0;
                    for (i = 0; j < array.length(); j++) {
                        JSONObject obj = array.getJSONObject(j);

                        d[i] = obj.getString("donator");
                        if (d[i].compareTo("1") == 0) {

                            n[i] = obj.getString("name");
                            m[i] = obj.getString("mobile");
                            c[i] = obj.getString("city");
                            e[i] = obj.getString("email");
                            b[i] = obj.getString("bloodtype");
                            i++;
                        }

                    }
                    customdonors cs = new customdonors(Main2Activity.this, n, m, c, e, b);
                    list.setAdapter(cs);
                }

                } catch(JSONException e1){
                    e1.printStackTrace();
                }



        }
    }
}
