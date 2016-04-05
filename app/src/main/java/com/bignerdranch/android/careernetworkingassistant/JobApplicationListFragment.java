package com.bignerdranch.android.careernetworkingassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by edwardlichtman on 10/9/15.
 */
public class JobApplicationListFragment extends Fragment {

    private static final int REQUEST_JOB_APPLICATION = 1;

    private RecyclerView mApplicationRecyclerView;
    private ApplicationAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_application_list, container, false);

        mApplicationRecyclerView = (RecyclerView) view.findViewById(R.id.ApplicationRecyclerView);
        mApplicationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        JobApplicationList applicationList = JobApplicationList.get(getActivity());
        List<JobApplication> jobApplications = applicationList.getJobApplications();

        if (mAdapter == null) {
            mAdapter = new ApplicationAdapter(jobApplications);
            mApplicationRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class ApplicationHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private JobApplication mJobApplication;
        private TextView mCompanyTextView;
        private TextView mPositionTextView;
        private TextView mListedDateTextView;
        private TextView mAppliedDateTextView;
        private CheckBox mInterviewCheckBox;
        private String mDateFormat = "MMM dd, yyyy";

        public ApplicationHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mCompanyTextView = (TextView) itemView.findViewById(R.id.FieldCompany);
            mPositionTextView = (TextView) itemView.findViewById(R.id.FieldPosition);
            mListedDateTextView = (TextView) itemView.findViewById(R.id.FieldListedDate);
            mAppliedDateTextView = (TextView) itemView.findViewById(R.id.FieldAppliedDate);
            mInterviewCheckBox = (CheckBox)itemView.findViewById(R.id.ChecInterview);

        }
        public void bindJobApplication(JobApplication jobApplication) {
            mJobApplication = jobApplication;
            mCompanyTextView.setText(mJobApplication.getCompanyName());
            mPositionTextView.setText(mJobApplication.getPositionTitle());
            mListedDateTextView.setText("Listed: " +
                    DMTools.FormatDate(mJobApplication.getListedDate()));
            mAppliedDateTextView.setText("Applied: " +
                    DMTools.FormatDate(mJobApplication.getAppliedDate()));
            mInterviewCheckBox.setChecked(mJobApplication.getInterviewOrganized());

        }
        @Override
        public void onClick(View v) {
            Intent intent =  JobApplicationPagerActivity.newIntent(
                    getActivity(), mJobApplication.getId());
            startActivity(intent);
        }
    }



    public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationHolder> {
        private List<JobApplication> mJobApplications;

        public ApplicationAdapter(List<JobApplication> jobApplications) {
            mJobApplications = jobApplications;
        }

        @Override
        public ApplicationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.list_item_job_applications, parent, false);
            return new ApplicationHolder(view);
        }

        @Override
        public void onBindViewHolder(ApplicationHolder holder, int position) {
            JobApplication jobApplication = mJobApplications.get(position);
            holder.bindJobApplication(jobApplication);
        }

        @Override
        public int getItemCount() {
            return mJobApplications.size();
        }


    }

}
