package com.bignerdranch.android.careernetworkingassistant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

import database.JobApplicationListDB;

/**
 * Created by EdwardLichtman on 4/4/16.
 */
public class JobApplicationPagerActivity extends AppCompatActivity {

    private static final String EXTRA_APP_ID =
            "com.bignerdranch.android.criminalintent.crime_id";
    private static final String EXTRA_APP_IS_NEW =
            "com.bignerdranch.android.criminalintent.isNewJobApplication";

    private static final int REQUEST_BACK = 0;

    private ViewPager mViewPager;
    private List<JobApplication> mJobApplications;

    public static Intent newIntent(Context packageContext, UUID jobApplicationId) {
        Intent intent = new Intent(packageContext, JobApplicationPagerActivity.class);
        intent.putExtra(EXTRA_APP_ID, jobApplicationId);
        return intent;
    }
    public static Intent newIntent(Context packageContext, UUID jobApplicationId, boolean isNewApplication) {
        Intent intent = new Intent(packageContext, JobApplicationPagerActivity.class);
        intent.putExtra(EXTRA_APP_ID, jobApplicationId);
        intent.putExtra(EXTRA_APP_IS_NEW, isNewApplication);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_pager);

        UUID jobApplicationId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_APP_ID);

        boolean isNewApplication = false;
        if (getIntent()
                .getSerializableExtra(EXTRA_APP_IS_NEW) != null) {
              isNewApplication = (boolean) getIntent()
                    .getSerializableExtra(EXTRA_APP_IS_NEW);
        }

        mViewPager = (ViewPager) findViewById(R.id.activity_pager_view_pager);

        mJobApplications = JobApplicationListDB.get(this).getJobApplications();
        FragmentManager fragmentManager = getSupportFragmentManager();
        final boolean finalIsNewApplication = isNewApplication;
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                JobApplication application = mJobApplications.get(position);
                return JobApplicationFragment.newInstance(application.getId(), finalIsNewApplication);
            }

            @Override
            public int getCount() {
                return mJobApplications.size();
            }
        });

        for (int i = 0; i < mJobApplications.size(); i++) {
            if (mJobApplications.get(i).getId().equals(jobApplicationId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
