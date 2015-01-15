package csun.aims.aimssmartcalendar;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.os.Bundle;

/**
 * Created by Dani on 1/14/2015.
 */
public class MonthViewFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.month_layout,container,false);
    }
}
