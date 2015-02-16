package csun.aims.aimssmartcalendar;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
        return v;
    }

}