package csun.aims.aimssmartcalendar;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ViewCalssesFragment extends Fragment {
    String classfragID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.classes_layout, container, false);

        Button b = (Button)v.findViewById(R.id.newClass);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // FragmentManager fm = Main.getFragmentManager();
                FragmentTransaction ft = Main.fm.beginTransaction();
                ft.replace(R.id.container,new ClassesFragment(),classfragID);
                ft.commit();
            }
        });

        TextView tv =(TextView)v.findViewById(R.id.title2);

        tv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent dbmanager = new Intent(getActivity(),AndroidDatabaseManager.class);
                startActivity(dbmanager);
            }
        });
        return v;
    }

}