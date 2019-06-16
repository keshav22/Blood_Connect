package blood.connect.kesha.firebasefcm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.*[a-z]*";

    FirebaseDatabase database;
    DatabaseReference users;
    SharedPreferences sh;
    Button singin;
    TextView googlesign;
    EditText email1,passwd1;
    @SuppressLint("WrongConstant")
    private boolean isNetworkAvailable()
    {
        return ((ConnectivityManager)getSystemService("connectivity")).getActiveNetworkInfo() != null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //userna=(EditText)findViewById(R.id.username);
        //singup=(Button)findViewById(R.id.singup1);

        googlesign=(TextView)findViewById(R.id.googlesignin);
        email1=(EditText)findViewById(R.id.email);
        passwd1=(EditText)findViewById(R.id.passwd);
        singin =(Button)findViewById(R.id.singin);
        database=FirebaseDatabase.getInstance();
        users=database.getReference("Users");

        googlesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(MainActivity.this,signup.class);
                startActivity(in);
                finish();
            }
        });


        singin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean m=check();
                if(m==true)
                {
                    users.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child(email1.getText().toString()).exists())
                            {
                                if(email1.getText().toString().compareTo("")!=0)
                                {
                                    User login=dataSnapshot.child(email1.getText().toString()).getValue(User.class);
                                    if(login.getPass().equals(passwd1.getText().toString().trim()))
                                    {
                                        sh=getSharedPreferences("Signin",MODE_PRIVATE);
                                        SharedPreferences.Editor editor1=sh.edit();
                                        editor1.putString("userna",email1.getText().toString());
                                        editor1.commit();
                                        email1.setText("");
                                        passwd1.setText("");
                                        Intent in=new Intent(MainActivity.this,Main2Activity.class);
                                        startActivity(in);
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),"username or password incorrect",Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"username or password incorrect",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
            }
        });
    }

    public boolean check()
    {
        if(!isNetworkAvailable())
        {
            Toast.makeText(getApplicationContext(),"Turn on the internet",Toast.LENGTH_SHORT).show();
            finish();
        }
       /* else if(userna.getText().length()<=0)
        {
            Toast.makeText(getApplicationContext(),"enter username",Toast.LENGTH_SHORT).show();
            userna.requestFocus();

        }*/
        else if(email1.getText().toString().length() > 0 == false )
        {
            Toast.makeText(getApplicationContext(),"Please enter your username",Toast.LENGTH_SHORT).show();
            email1.requestFocus();
        }
        else if(passwd1.getText().toString().length()<=0)
        {
            Toast.makeText(getApplicationContext(),"Enter password",Toast.LENGTH_SHORT).show();
            passwd1.requestFocus();
        }
        else
            return true;
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in =new Intent(getApplicationContext(),Main3Activity.class);
        startActivity(in);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
