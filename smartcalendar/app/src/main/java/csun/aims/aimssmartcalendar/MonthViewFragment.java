package csun.aims.aimssmartcalendar;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

/**
 * Created by Dani on 1/14/2015.
 */
public class MonthViewFragment extends Fragment {

    CalendarView calendar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.month_layout, container, false);
        calendar = (CalendarView) v.findViewById(R.id.calendar);
        initializeCalendar();

        return v;
    }

    public void initializeCalendar() {

        // sets whether to show the week number.
        calendar.setShowWeekNumber(false);

        // sets the first day of week according to Cal.
        // here we set Monday as the first day of the Cal
        calendar.setFirstDayOfWeek(2);

        //The background color for the selected week.
        calendar.setSelectedWeekBackgroundColor(getResources().getColor(R.color.green));

        //sets the color for the dates of an unfocused month.
        calendar.setUnfocusedMonthDateColor(getResources().getColor(R.color.transparent));

        //sets the color for the separator line between weeks.
        calendar.setWeekSeparatorLineColor(getResources().getColor(R.color.transparent));

        //sets the color for the vertical bar shown at the beginning and at the end of the selected date.
        calendar.setSelectedDateVerticalBar(R.color.darkgreen);

        //sets the listener to be notified upon selected date change.
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            //show the selected date as a toast
//            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                Toast.makeText(getActivity().getApplicationContext(), month+1 + "/" + day + "/" + year, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
