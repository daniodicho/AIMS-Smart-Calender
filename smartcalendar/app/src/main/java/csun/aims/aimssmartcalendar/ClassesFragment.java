package csun.aims.aimssmartcalendar;

import android.app.Fragment;
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

/**
 * Created by Dani on 1/14/2015.
 */
public class ClassesFragment extends Fragment {
    DatabaseManager db;
    Spinner difficultySpinner;
    Spinner unitSpinner;

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
//                Cursor c = db.getAssignment(0);
//                displayAssignment(c);
            }
        });

        return v;
    }

    public void addClass(View v){
        EditText className = (EditText)getActivity().findViewById(R.id.className);


        String diff = difficultySpinner.getSelectedItem().toString();

        int difficulty = diff.charAt(0);

        String uni = unitSpinner.getSelectedItem().toString();

        int units = uni.charAt(0);

        CheckBox requiresReading = (CheckBox)getActivity().findViewById(R.id.requiresReading);

        boolean reading = false;
        if (requiresReading.isChecked()) {
            reading = true;
        }


        TimePicker start = (TimePicker)getActivity().findViewById(R.id.classStartTime);
        TimePicker end = (TimePicker)getActivity().findViewById(R.id.classEndTime);

        String strstartTime = start.getCurrentHour() + ":" + start.getCurrentMinute();
        String strendTime = end.getCurrentHour() + ":" + end.getCurrentMinute();

        long id = db.insertClass(className.getText().toString(), strstartTime, strendTime, difficulty, units, reading);
        if(id<0){
            Log.d(LOGCAT, "ROWS NOT INSERTED");
        }
        else{
            Log.d(LOGCAT, "ROWS INSERTED");
        }

    }
}