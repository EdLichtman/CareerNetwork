package com.bignerdranch.android.careernetworkingassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

import database.JobApplicationListDB;

/**
 * Created by edwardlichtman on 10/7/15.
 */
public class JobApplicationFragment extends Fragment {

    private static final String ARG_JOB_APP_ID = "job_application_id";
    private static final String ARG_JOB_APP_IS_NEW = "job_application_is_new";
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_DELETE = "DialogDelete";
    private static final String DIALOG_SAVE = "DialogSave";

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_DELETE = 1;
    private static final int REQUEST_SAVE = 2;

    private static final int DATE_APPLIED = R.string.txt_AppliedDate;
    private static final int DATE_LISTED = R.string.txt_ListedDate;

    private JobApplication mJobApplication;
    private EditText mCompanyName;
    private EditText mPositionTitle;
    private EditText mCity;
    private EditText mState;
    private EditText mContactName;
    private Button mListedDate;
    private Button mAppliedDate;
    private CheckBox mInterviewOrganized;

    private Button mFollowUp;

    private boolean mIsLocked;

    public static JobApplicationFragment newInstance(UUID jobApplicationId) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_JOB_APP_ID, jobApplicationId);

        JobApplicationFragment fragment = new JobApplicationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static JobApplicationFragment newInstance(UUID jobApplicationId, boolean isNewApplication) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_JOB_APP_ID, jobApplicationId);
        args.putSerializable(ARG_JOB_APP_IS_NEW, isNewApplication);

        JobApplicationFragment fragment = new JobApplicationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        UUID applicationId = (UUID) getArguments().getSerializable(ARG_JOB_APP_ID);
        if (getArguments().getSerializable(ARG_JOB_APP_IS_NEW) != null) {
            mIsLocked = ((boolean) getArguments().getSerializable(ARG_JOB_APP_IS_NEW));
        }

        if (applicationId != null) {
            mJobApplication = JobApplicationListDB.get(getActivity()).getJobApplication(applicationId);
        } else {
            mJobApplication = new JobApplication();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_job_application, container, false);

        createFields(v);
        createDataForNewApplications();
        lockOrUnlockRecord(mIsLocked);
        setFieldListeners();

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        JobApplicationListDB.get(getActivity())
                .updateJobApplication(mJobApplication);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_job_application, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_delete_application:
                FragmentManager manager = getFragmentManager();
                DialogConfirmFragment dialog =
                        DialogConfirmFragment.newInstance(R.string.delete_application_certain);
                dialog.setTargetFragment(JobApplicationFragment.this, REQUEST_DELETE);
                dialog.show(manager, DIALOG_DELETE);
                return true;
            case R.id.menu_item_unlock_application:
                updateRecordLock(item);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createFields(View v) {

        /**
         * Create the Views and their listeners
         */

        //Set Company Name
        mCompanyName = (EditText) v.findViewById(R.id.EditCompany);
        mCompanyName.setText(mJobApplication.getCompanyName());

        //Set Position Title
        mPositionTitle = (EditText) v.findViewById(R.id.EditPosition);
        mPositionTitle.setText(mJobApplication.getPositionTitle());

        //Set City
        mCity = (EditText) v.findViewById(R.id.EditCityLocation);
        mCity.setText(mJobApplication.getCity());

        //Set State
        mState = (EditText) v.findViewById(R.id.EditStateLocation);
        mState.setText(mJobApplication.getState());

        //Set Contact Name
        mContactName = (EditText) v.findViewById(R.id.EditContact);
        mContactName.setText(mJobApplication.getContactName());

        //Set ListedDate
        mListedDate = (Button) v.findViewById(R.id.EditListedDate);

        //Set AppliedDate
        mAppliedDate = (Button) v.findViewById(R.id.EditAppliedDate);

        //Set Interview
        mInterviewOrganized = (CheckBox) v.findViewById(R.id.ChecInterview);
        if (mJobApplication.getInterviewOrganized() == null) {
            mInterviewOrganized.setChecked(false);
        } else {
            mInterviewOrganized.setChecked(mJobApplication.getInterviewOrganized());
        }

        mFollowUp = (Button) v.findViewById(R.id.send_followup);

        updateDates();

    }

    private void setFieldListeners() {

        //Set Company Listener
        mCompanyName.addTextChangedListener(new genericTextWatcher(mCompanyName));

        //Set Position Listener
        mPositionTitle.addTextChangedListener(new genericTextWatcher(mPositionTitle));

        //Set City Listener
        mCity.addTextChangedListener(new genericTextWatcher(mCity));

        //Set State Listener
        mState.addTextChangedListener(new genericTextWatcher(mState));

        //Set Contact Name Listener
        mContactName.addTextChangedListener(new genericTextWatcher(mContactName));

        //Set ListedDate Listener
        mListedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(
                        mJobApplication.getListedDate(), DATE_LISTED);
                dialog.setTargetFragment(JobApplicationFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        //Set AppliedDateListener
        mAppliedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(
                        mJobApplication.getAppliedDate(), DATE_APPLIED);
                dialog.setTargetFragment(JobApplicationFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        //Set InterviewOrganizedListener
        mInterviewOrganized.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mJobApplication.setInterviewOrganized(isChecked);
            }

        });

        //Set FollowupListener
        mFollowUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, createFollowupEmail());
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_followup_subject));

                startActivity(i);


            }
        });
    }

    private void updateDates() {
        if (mJobApplication.getListedDate() == null) {
            mListedDate.setText(DMTools.formatDateTime(DMTools.TODAY_DATE));
        } else {
            mListedDate.setText(DMTools.formatDateTime(mJobApplication.getListedDate()));
        }
        if (mJobApplication.getAppliedDate() == null) {
            mAppliedDate.setText(DMTools.formatDateTime(DMTools.TODAY_DATE));
        } else {
            mAppliedDate.setText(DMTools.formatDateTime(mJobApplication.getAppliedDate()));
        }

    }

    private void lockOrUnlockRecord(boolean canEdit) {
        mCompanyName.setEnabled(canEdit);
        mPositionTitle.setEnabled(canEdit);
        mCity.setEnabled(canEdit);
        mState.setEnabled(canEdit);
        mContactName.setEnabled(canEdit);
        mListedDate.setEnabled(canEdit);
        mAppliedDate.setEnabled(canEdit);
        mInterviewOrganized.setEnabled(canEdit);
        mIsLocked = !canEdit;

    }

    private void updateRecordLock(MenuItem item) {
        if (mIsLocked) {
            lockOrUnlockRecord(mIsLocked);
            item.setIcon(R.mipmap.ic_menu_save);
            item.setTitle(R.string.save_application);
        } else {
            FragmentManager manager = getFragmentManager();
            DialogConfirmFragment dialog = DialogConfirmFragment.newInstance(
                    R.string.save_application_certain);
            dialog.setTargetFragment(JobApplicationFragment.this, REQUEST_SAVE);
            dialog.show(manager, DIALOG_SAVE);

        }

    }

    private void createDataForNewApplications() {
        if (mJobApplication.getPositionTitle() == null) {
            mJobApplication.setPositionTitle("");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            int dateName = (int) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE_NAME);
            if (dateName == DATE_LISTED) {
                mJobApplication.setListedDate(date);
            } else if (dateName == DATE_APPLIED) {
                mJobApplication.setAppliedDate(date);
            }
            updateDates();
        } else if (requestCode == REQUEST_DELETE) {
            JobApplicationListDB.get(getActivity()).deleteJobApplication(mJobApplication);
            getActivity().finish();
        } else if (requestCode == REQUEST_SAVE) {
            getActivity().finish();
        }


    }

    private String createFollowupEmail() {
        StringBuilder builder = new StringBuilder(getString(R.string.email_followup_address_line,
                mJobApplication.getContactName()) + "\n")
                .append(getString(R.string.email_followup_body,
                        mJobApplication.getPositionTitle(),
                        mJobApplication.getCompanyName(),
                        DMTools.formatDateTime(mJobApplication.getAppliedDate()),
                        "\n") + "\n\n")
                .append(getString(R.string.email_followup_closing));
        return builder.toString();
    }

    private class genericTextWatcher implements TextWatcher{

        private View view;
        private genericTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String text = charSequence.toString();
            switch(view.getId()){
                case R.id.EditCompany:
                    mJobApplication.setCompanyName(text);
                    break;
                case R.id.EditPosition:
                    mJobApplication.setPositionTitle(text);
                    break;
                case R.id.EditCityLocation:
                    mJobApplication.setCity(text);
                    break;
                case R.id.EditStateLocation:
                    mJobApplication.setState(text);
                    break;
                case R.id.EditContact:
                    mJobApplication.setContactName(text);
                    break;
            }
        }
        public void afterTextChanged(Editable editable) {

        }
    }
}
