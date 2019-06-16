package blood.connect.kesha.firebasefcm;

import android.app.Service;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by kesha on 05-06-2018.
 */

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;




public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {

        String token = FirebaseInstanceId.getInstance().getToken();
       // Toast.makeText(getApplicationContext(),"GG",Toast.LENGTH_SHORT).show();
        sendtoserver(token);
    }

    private void sendtoserver(String token) {
        ExecuteTask1 e=new ExecuteTask1();
        e.doInBackground(token);
    }

    class ExecuteTask1 extends AsyncTask<String,Integer,String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url=new URL("http://argetim2k17.com/fcm/register.php");
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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
        }
    }


}
