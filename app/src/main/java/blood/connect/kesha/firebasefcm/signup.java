package blood.connect.kesha.firebasefcm;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class signup extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference users;
    Button singup;
    ImageView img;
    EditText email1,userna,passwd1,repasswd1,name,mobo,city;
    int PICK_IMAGE=1;
    RadioGroup rg;
    Uri uri;
    RadioButton r,r1;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progress;
    ScrollView sv;
    private StorageReference mStorageRef;
    Boolean b=true,m;
    int cnt=1;
    Spinner s;
    int donator=1;
    @SuppressLint("WrongConstant")
    private boolean isNetworkAvailable()
    {
        return ((ConnectivityManager)getSystemService("connectivity")).getActiveNetworkInfo() != null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in =new Intent(getApplicationContext(),Main3Activity.class);
        startActivity(in);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        s = (Spinner) findViewById(R.id.bloodtype);

        ArrayAdapter<String> aa =new ArrayAdapter<String>(this, R.layout.custom_spinner, getResources().getStringArray(R.array.roomType));
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(aa);
        city=(EditText)findViewById(R.id.city);
        name=(EditText)findViewById(R.id.name);
        mobo=(EditText)findViewById(R.id.mobo);
        progress = new ProgressDialog(this);
        img=(ImageView)findViewById(R.id.img);
        email1=(EditText)findViewById(R.id.email);
        passwd1=(EditText)findViewById(R.id.password);
        userna =(EditText)findViewById(R.id.username);
        repasswd1=(EditText)findViewById(R.id.repass);
        singup=(Button)findViewById(R.id.signup);
        rg=(RadioGroup)findViewById(R.id.radio);
        r=(RadioButton)findViewById(R.id.radiodon);
        r1=(RadioButton)findViewById(R.id.radionon);
        sv = (ScrollView)findViewById(R.id.scr1);
        name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if(i== EditorInfo.IME_ACTION_NEXT)
                {
                    email1.requestFocus();
                }

                return true;
            }
        });

        email1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if(i== EditorInfo.IME_ACTION_NEXT)
                {
                    userna.requestFocus();
                }

                return true;
            }
        });

        userna.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if(i== EditorInfo.IME_ACTION_NEXT)
                {
                    passwd1.requestFocus();
                }

                return true;
            }
        });

        passwd1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if(i== EditorInfo.IME_ACTION_NEXT)
                {
                    repasswd1.requestFocus();
                }

                return true;
            }
        });

        repasswd1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if(i== EditorInfo.IME_ACTION_NEXT)
                {
                    city.requestFocus();
                }

                return true;
            }
        });
        city.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if(i== EditorInfo.IME_ACTION_NEXT)
                {
                    mobo.requestFocus();
                }

                return true;
            }
        });
        mobo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if(i== EditorInfo.IME_ACTION_NEXT)
                {

                    sv.scrollTo(0, sv.getBottom());
                }

                return true;
            }
        });
        database= FirebaseDatabase.getInstance();
        users=database.getReference("Users");
        mStorageRef = FirebaseStorage.getInstance().getReference();



        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               if(cnt==1) {
                   users.addListenerForSingleValueEvent(new ValueEventListener()
                   {
                       @Override
                       public void onDataChange(DataSnapshot dataSnapshot)
                       {
                           if (dataSnapshot.child(userna.getText().toString()).exists())
                           {
                               Toast.makeText(getApplicationContext(), "Username already exists", Toast.LENGTH_SHORT).show();
                           }
                           else
                           {
                               cnt=2;

                               singup.performClick();

                           }
                       }

                       @Override
                       public void onCancelled(DatabaseError databaseError)
                       {

                       }
                   });
               }
               else{
                   m=check();
                   if (m == true)
                   {
                       String selected=s.getSelectedItem().toString();
                       int don=1;

                       if(r1.isChecked())
                       {
                           don=0;
                       }
                       ExecuteTask1 ex=new ExecuteTask1();
                       ex.execute(selected,String.valueOf(don));

                       final User u = new User(name.getText().toString(),selected,userna.getText().toString(), email1.getText().toString(), passwd1.getText().toString(),don,mobo.getText().toString(),city.getText().toString());
                       users.child(u.getUsername()).setValue(u);
                       StorageReference riversRef = mStorageRef.child("profilepictures/" + userna.getText().toString() + ".jpg");
                       progress.setMessage("Uploading");
                       progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                       progress.setIndeterminate(true);
                       progress.show();
                       riversRef.putFile(uri)
                               .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                   @Override
                                   public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                                   {
                                       Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                                       name.setText("");
                                       email1.setText("");
                                       passwd1.setText("");
                                       userna.setText("");
                                       repasswd1.setText("");
                                       mobo.setText("");
                                       city.setText("");
                                       uri=null;
                                       img.setImageResource(R.mipmap.uploadimage1);
                                       progress.dismiss();
                                   }
                               })
                               .addOnFailureListener(new OnFailureListener() {
                                   @Override
                                   public void onFailure(@NonNull Exception exception) {
                                       // Handle unsuccessful uploads
                                       // ...
                                   }
                               });
                   }
               }
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });
    }

    class ExecuteTask1 extends AsyncTask<String,Integer,String> {

        @Override
        protected String doInBackground(String... strings) {
            try {

                URL url=new URL("http://argetim2k17.com/yef/profile.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post="";
                post = URLEncoder.encode("userna", "UTF-8") + "=" + URLEncoder.encode(userna.getText().toString(), "UTF-8")+"&"+
                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email1.getText().toString(), "UTF-8")+"&"+
                        URLEncoder.encode("donator", "UTF-8") + "=" + URLEncoder.encode(strings[0], "UTF-8")+"&"+
                        URLEncoder.encode("bloodtype", "UTF-8") + "=" + URLEncoder.encode(strings[1], "UTF-8")+"&"+
                        URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name.getText().toString(), "UTF-8")+"&"+
                        URLEncoder.encode("mobo", "UTF-8") + "=" + URLEncoder.encode(mobo.getText().toString(), "UTF-8")+"&"+
                URLEncoder.encode("city", "UTF-8") + "=" + URLEncoder.encode(city.getText().toString(), "UTF-8");
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
            //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
        }
    }

    private boolean check() {
        if(!isNetworkAvailable())
        {
            Toast.makeText(getApplicationContext(),"Turn on the internet",Toast.LENGTH_SHORT).show();
            finish();
        }
        else if(uri==null)
        {
            Toast.makeText(getApplicationContext(),"Select profile photo",Toast.LENGTH_SHORT).show();
            img.performClick();

        }
        else if(name.getText().length()==0)
        {
            Toast.makeText(getApplicationContext(),"Inout your name",Toast.LENGTH_SHORT).show();
            name.requestFocus();

        }
        else if(userna.getText().length()<=0)
        {
            Toast.makeText(getApplicationContext(),"enter username",Toast.LENGTH_SHORT).show();
            userna.requestFocus();
        }
        else if(email1.getText().toString().matches(emailPattern) && email1.getText().toString().length() > 0 == false )
        {
            Toast.makeText(getApplicationContext(),"Provide input to email",Toast.LENGTH_SHORT).show();
            email1.requestFocus();
        }
        else if(passwd1.getText().toString().length()<=0 && repasswd1.getText().toString().compareTo(passwd1.getText().toString())==0)
        {
            Toast.makeText(getApplicationContext(),"Password mismatch",Toast.LENGTH_SHORT).show();
            passwd1.requestFocus();
        }
        else if(city.length()==0)
        {
            Toast.makeText(getApplicationContext(),"Input your address",Toast.LENGTH_SHORT).show();
            city.requestFocus();
        }
        else if(mobo.length()!=10)
        {
            Toast.makeText(getApplicationContext(),"mobile number invalid",Toast.LENGTH_SHORT).show();
            mobo.requestFocus();
        }
        else {

            return true;
        }

        return false;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE) {
                Picasso.get().load(data.getData()).noPlaceholder().centerCrop().fit().into(img);
                uri=data.getData();
            }

        }
    }

}
