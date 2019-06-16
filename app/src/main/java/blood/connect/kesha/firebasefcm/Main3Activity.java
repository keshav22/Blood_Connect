package blood.connect.kesha.firebasefcm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class Main3Activity extends AppCompatActivity {

    Button signin,signup,guest;
    SharedPreferences sh;
    TextView txt;

    @SuppressLint("WrongConstant")
    private boolean isNetworkAvailable()
    {
        return ((ConnectivityManager)getSystemService("connectivity")).getActiveNetworkInfo() != null;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

       // txt=(TextView)findViewById(R.id.heading);
       // Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/yourfont.ttf");
        //txt.setTypeface(typeface);
        sh=getSharedPreferences("Signin",getApplicationContext().MODE_PRIVATE);
        String n=sh.getString("userna","");
        if(n.compareTo("")!=0)
        {
            Intent in=new Intent(Main3Activity.this,Main2Activity.class);
            startActivity(in);
            finish();
        }

        if(!isNetworkAvailable())
        {
            Toast.makeText(getApplicationContext(),"Turn on internet first",Toast.LENGTH_SHORT).show();
            finish();
        }

        signin = (Button)findViewById(R.id.signinbtn);
        signup = (Button)findViewById(R.id.signupbtn);
        guest  = (Button)findViewById(R.id.guestlogin);

        FirebaseInstanceId.getInstance().getToken();
        FirebaseMessaging.getInstance().subscribeToTopic("Token");

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(in);
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),signup.class);
                startActivity(in);
                finish();
            }
        });

        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),Main2Activity.class);
                startActivity(in);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
