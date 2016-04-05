package com.bignerdranch.android.careernetworkingassistant;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import java.util.UUID;

/**
 * Created by edwardlichtman on 10/7/15.
 */
public class JobApplicationFragment extends Fragment {

    private static final String ARG_JOB_APP_ID = "job_application_id";

    private JobApplication mJobApplication;
    private EditText mCompanyName;
    private EditText mPositionTitle;
    private EditText mCity;
    private EditText mState;
    private EditText mContactName;
    private Button mListedDate;
    private Button mAppliedDate;
    private CheckBox mInterviewOrganized;


    private Button SaveButton;

    public static JobApplicationFragment newInstance(UUID jobApplicationId) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_JOB_APP_ID, jobApplicationId);

        JobApplicationFragment fragment = new JobApplicationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID applicationId = (UUID) getArguments().getSerializable(ARG_JOB_APP_ID);

        if (applicationId != null) {
            mJobApplication = JobApplicationList.get(getActivity()).getJobApplication(applicationId);
        } else {
            mJobApplication = new JobApplication();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_job_application, container, false);

        createFieldsAndListeners(v);
        setExistingFieldsAndListeners(v);

        return v;
    }

    private void createFieldsAndListeners(View v) {

        /**
         * Create the Views and their listeners
         */

        //Set Company Name
        mCompanyName = (EditText) v.findViewById(R.id.EditCompany);
        mCompanyName.setText(mJobApplication.getCompanyName());
        mCompanyName.addTextChangedListener(new GenericTextWatcher(mCompanyName));

        //Set Position Title
        mPositionTitle = (EditText) v.findViewById(R.id.EditPosition);
        mPositionTitle.setText(mJobApplication.getPositionTitle());
        mPositionTitle.addTextChangedListener(new GenericTextWatcher(mPositionTitle));

        //Set City
        mCity = (EditText) v.findViewById(R.id.EditCityLocation);
        mCity.setText(mJobApplication.getCity());
        mCity.addTextChangedListener(new GenericTextWatcher(mCity));

        //Set State
        mState = (EditText) v.findViewById(R.id.EditStateLocation);
        mState.setText(mJobApplication.getState());
        mState.addTextChangedListener(new GenericTextWatcher(mState));

        //Set Contact Name
        mContactName = (EditText) v.findViewById(R.id.EditContact);
        mContactName.setText(mJobApplication.getContactName());
        mContactName.addTextChangedListener(new GenericTextWatcher(mContactName));

        //Set ListedDate
        mListedDate = (Button) v.findViewById(R.id.EditListedDate);
        if (mJobApplication.getListedDate() == null) {
            mListedDate.setText(DMTools.FormatDate(DMTools.TODAY_DATE));
        } else {
            mListedDate.setText(DMTools.FormatDate(mJobApplication.getListedDate()));
        }


        //Set AppliedDate
        mAppliedDate = (Button) v.findViewById(R.id.EditAppliedDate);
        if (mJobApplication.getAppliedDate() == null) {
            mAppliedDate.setText(DMTools.FormatDate(DMTools.TODAY_DATE));
        } else {
            mAppliedDate.setText(DMTools.FormatDate(mJobApplication.getAppliedDate()));
        }


        //Set Interview
        mInterviewOrganized = (CheckBox) v.findViewById(R.id.ChecInterview);
        if (mJobApplication.getInterviewOrganized() == null) {
            mInterviewOrganized.setChecked(false);
        } else {
            mInterviewOrganized.setChecked(mJobApplication.getInterviewOrganized());
        }
        mInterviewOrganized.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mJobApplication.setInterviewOrganized(isChecked);
            }

        });

        SaveButton = (Button) v.findViewById(R.id.butnSave);
        SaveButton.bringToFront();
        SaveButton.setEnabled(false);

    }


    private void setExistingFieldsAndListeners(View v) {

    }


    private class GenericTextWatcher implements TextWatcher{

        private View view;
        private GenericTextWatcher(View view) {
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