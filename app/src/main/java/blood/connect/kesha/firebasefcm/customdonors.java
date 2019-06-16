package blood.connect.kesha.firebasefcm;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by kesha on 25-06-2018.
 */

public class customdonors extends ArrayAdapter<String>{

    private Activity context;
    private String[] name;
    //private String[] name;
    private String[] city;
    private String[] mobile;
    private String[] email;
    private String[] bloodtype;


    int way=3;

    public customdonors(Activity contex, String[] n, String[] m, String[] c, String[] e,String[] b )
    {
        super(contex, R.layout.faculties_list,m);
        this.context=contex;
        this.name=n;
        this.city=c;
        this.mobile=m;
        this.email=e;
        this.bloodtype=b;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        final View v=inflater.inflate(R.layout.faculties_list,null,true);

        TextView n=(TextView)v.findViewById(R.id.name);

        n.setText(name[position]);

        TextView d = (TextView) v.findViewById(R.id.bloodtype1);
        d.setText(bloodtype[position]);

        TextView m = (TextView) v.findViewById(R.id.mobile);
        m.setText(mobile[position]);
        TextView r = (TextView) v.findViewById(R.id.address);
        r.setText(city[position]);
        TextView e = (TextView) v.findViewById(R.id.email);
        e.setText(email[position]);

        return v;
    }
}