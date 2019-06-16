package blood.connect.kesha.firebasefcm;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class generaterequest extends AppCompatActivity {

    Spinner s;
    EditText message;
    SharedPreferences sh;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generaterequest);

        btn=(Button)findViewById(R.id.btn);
        s = (Spinner) findViewById(R.id.bloodtype);
        message=(EditText)findViewById(R.id.Message);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, R.layout.custom_spinner, getResources().getStringArray(R.array.roomType));
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(aa);
        sh=getSharedPreferences("Signin",getApplicationContext().MODE_PRIVATE);
        final String n=sh.getString("userna","");


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(),"Uploaded",Toast.LENGTH_SHORT).show();
                ExecuteTask ex=new ExecuteTask();
                ex.execute(n.toString(),s.getSelectedItem().toString(),message.getText().toString());
            }
        });
    }

    class ExecuteTask extends AsyncTask<String,Integer,String> {

        @Override
        protected String doInBackground(String... strings) {
            try {

                URL url=new URL("http://argetim2k17.com/yef/requests.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post="";
                post = URLEncoder.encode("userna", "UTF-8") + "=" + URLEncoder.encode(strings[0], "UTF-8")+"&"+
                        URLEncoder.encode("message", "UTF-8") + "=" + URLEncoder.encode(strings[1], "UTF-8")+"&"+
                        URLEncoder.encode("bloodtype", "UTF-8") + "=" + URLEncoder.encode(strings[2], "UTF-8");
                bufferedWriter.write(post);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                String j;
                StringBuffer sb=new StringBuffer();
                while((j=bufferedReader.readLine())!=null)
                {
                    sb.append(j);
                }
                return sb.toString();
            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s)
        {
            //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
            super.onPostExecute(s);
            message.setText("");
            ExecuteTask1 ex=new ExecuteTask1();
            ex.execute("");
        }
    }


    class ExecuteTask1 extends AsyncTask<String,Integer,String> {

        @Override
        protected String doInBackground(String... strings) {
            try {

                URL url=new URL("http://argetim2k17.com/fcm/push_notification.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post="";
                post = URLEncoder.encode("Token", "UTF-8") + "=" + URLEncoder.encode(strings[0], "UTF-8");
                bufferedWriter.write(post);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                String j;
                StringBuffer sb=new StringBuffer();
                while((j=bufferedReader.readLine())!=null)
                {
                    sb.append(j);
                }
                return sb.toString();
            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);

        }
    }
}
