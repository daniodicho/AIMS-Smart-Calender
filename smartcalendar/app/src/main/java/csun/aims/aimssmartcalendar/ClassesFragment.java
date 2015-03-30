package csun.aims.aimssmartcalendar;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * Created by Dani on 1/14/2015.
 */
public class ClassesFragment extends Fragment {
    DatabaseManager db;
    Spinner difficultySpinner;
    Spinner unitSpinner;
    String viewclassesID;

    public static final String LOGCAT = "UDB";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.add_class,container,false);

        db = new DatabaseManager(getActivity());

        Button add = (Button)v.findViewById(R.id.add_class);

        difficultySpinner = (Spinner) v.findViewById(R.id.difficultySpinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.nums, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        difficultySpinner.setAdapter(adapter);

        unitSpinner = (Spinner) v.findViewById(R.id.unitSpinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(),
                R.array.nums, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        unitSpinner.setAdapter(adapter2);

        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                addClass(getView());
                FragmentManager fm = getActivity().getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container,new ViewCalssesFragment(),viewclassesID);
                ft.commit();
            }
        });

        return v;
    }

    public void addClass(View v){
        EditText className = (EditText)getActivity().findViewById(R.id.className);


        String diff = difficultySpinner.getSelectedItem().toString();
        String temp = diff.substring(0,1);
        int difficulty = Integer.parseInt(temp);

        String uni = unitSpinner.getSelectedItem().toString();
        temp = uni.substring(0,1);
        int units = Integer.parseInt(temp);

        CheckBox requiresReading = (CheckBox)getActivity().findViewById(R.id.requiresReading);

        boolean reading = false;
        if (requiresReading.isChecked()) {
            reading = true;
        }

        String days = "MW";


        TimePicker start = (TimePicker)getActivity().findViewById(R.id.classStartTime);
        TimePicker end = (TimePicker)getActivity().findViewById(R.id.classEndTime);

        String strstartTime = start.getCurrentHour() + ":" + start.getCurrentMinute();
        String strendTime = end.getCurrentHour() + ":" + end.getCurrentMinute();

        long id = db.insertClass(className.getText().toString(), strstartTime, strendTime, difficulty, units, days, reading);
        if(id<0){
            Log.d(LOGCAT, "ROWS NOT INSERTED");
            Toast.makeText(getActivity().getApplicationContext(),"Something went wrong", Toast.LENGTH_LONG).show();

        }
        else{
            Log.d(LOGCAT, "ROWS INSERTED");
            Toast.makeText(getActivity().getApplicationContext(),"Class was added successfully", Toast.LENGTH_LONG).show();
        }

    }
}