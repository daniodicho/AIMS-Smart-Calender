package csun.aims.aimssmartcalendar;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

/**
 * Created by Dani on 1/14/2015.
 */
public class MonthViewFragment extends Fragment {

    CalendarView calendar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.month_layout,container,false);
    }
}
