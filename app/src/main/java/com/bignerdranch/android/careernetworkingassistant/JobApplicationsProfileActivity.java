package com.bignerdranch.android.careernetworkingassistant;

import android.support.v4.app.Fragment;

/**
 * Created by EdwardLichtman on 4/11/16.
 */
public class JobApplicationsProfileActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new JobApplicationsProfileFragment();

    }
}