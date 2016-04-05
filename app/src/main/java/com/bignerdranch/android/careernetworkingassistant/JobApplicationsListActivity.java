package com.bignerdranch.android.careernetworkingassistant;

import android.support.v4.app.Fragment;
import android.util.Log;

public class JobApplicationsListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new JobApplicationListFragment();

    }
}
