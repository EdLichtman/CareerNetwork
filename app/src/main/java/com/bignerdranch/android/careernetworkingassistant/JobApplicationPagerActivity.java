package com.bignerdranch.android.careernetworkingassistant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;
import java.util.UUID;

/**
 * Created by EdwardLichtman on 4/4/16.
 */
public class JobApplicationPagerActivity extends FragmentActivity {

    private static final String EXTRA_APP_ID =
            "com.bignerdranch.android.criminalintent.crime_id";

    private ViewPager mViewPager;
    private List<JobApplication> mJobApplications;

    public static Intent newIntent(Context packageContext, UUID jobApplicationId) {
        Intent intent = new Intent(packageContext, JobApplicationPagerActivity.class);
        intent.putExtra(EXTRA_APP_ID, jobApplicationId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_pager);

        UUID jobApplicationId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_APP_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_pager_view_pager);

        mJobApplications = JobApplicationList.get(this).getJobApplications();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                JobApplication application = mJobApplications.get(position);
                return JobApplicationFragment.newInstance(application.getId());
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
