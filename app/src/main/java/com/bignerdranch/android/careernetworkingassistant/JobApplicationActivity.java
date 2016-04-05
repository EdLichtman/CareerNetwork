package com.bignerdranch.android.careernetworkingassistant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.util.UUID;


public class JobApplicationActivity extends SingleFragmentActivity {

    private static final String EXTRA_JOB_APP_ID =
            "com.bignerdranch.android.careernetworkingassistant.job_application_id";

    public static Intent newIntent(Context packageContext, UUID applicationId) {
        Intent intent = new Intent(packageContext, JobApplicationActivity.class);
        intent.putExtra(EXTRA_JOB_APP_ID, applicationId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {

        UUID jobApplicationId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_JOB_APP_ID);
        return JobApplicationFragment.newInstance(jobApplicationId);
    }
}
