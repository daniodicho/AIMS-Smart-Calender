package csun.aims.aimssmartcalendar;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Created by Dani on 1/14/2015.
 */
public class AssignmentFragment extends Fragment {
    DatabaseManager db;
    public static final String LOGCAT = "UDB";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AssignmentFragment context = this;
        View v= inflater.inflate(R.layout.add_assignment, container, false);

        db = new DatabaseManager(getActivity());

        Button add = (Button)v.findViewById(R.id.add_assignment);

           add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                addAssignment(getView());
//                Cursor c = db.getAssignment(0);
//                displayAssignment(c);
            }
        });

        Toast.makeText(getActivity().getApplicationContext(),add.getText(),Toast.LENGTH_LONG);

//        try {
//            String destPath = "/data/data/" + Main.packageName +"/databases/AssignmentDB";
//            File f = new File(destPath);
//            if (!f.exists()) {
//                CopyDB( Main.baseContext.getAssets().open("mydb"),
//                        new FileOutputStream(destPath));
//                }
//            } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            } catch (IOException e) {
//            e.printStackTrace();
//            }
        return v;
    }

    public void addAssignment(View v){
        EditText name = (EditText)getActivity().findViewById(R.id.assignmentName);
        RadioButton hw = (RadioButton)getActivity().findViewById(R.id.homeworkRB);
        RadioButton test = (RadioButton)getActivity().findViewById(R.id.testRB);
        int type = 0;

            if (hw.isChecked())
                type = 0;
            if (test.isChecked())
                type = 1;

        TimePicker tp = (TimePicker)getActivity().findViewById(R.id.timePicker);
        DatePicker dp = (DatePicker)getActivity().findViewById(R.id.datePicker);

        String strDate = dp.getYear() + "-" + (dp.getMonth() + 1) + "-" + dp.getDayOfMonth();
        String strTime = tp.getCurrentHour() + ":" + tp.getCurrentMinute();

        double grade = 76.0;  //test value
        long id = db.insertAssignment(name.getText().toString(), strDate, strTime, "Math", false, type, grade);
        if(id<0){
            Log.d(LOGCAT, "ROWS NOT INSERTED");
        }
        else{
            Log.d(LOGCAT, "ROWS INSERTED");
        }

    }

    public void CopyDB(InputStream inputStream, OutputStream outputStream)
    throws IOException {
        //---copy 1K bytes at a time---
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
            }
        inputStream.close();
        outputStream.close();
    }

    public void displayAssignment(Cursor c)
    {
       Toast.makeText(this.getActivity().getApplicationContext()
               ,
                "id: " + c.getString(0) + "\n" +
                        "Title: " + c.getString(1) + "\n" +
                        "Due Date: " + c.getString(2),
       Toast.LENGTH_SHORT).show();
    }
}