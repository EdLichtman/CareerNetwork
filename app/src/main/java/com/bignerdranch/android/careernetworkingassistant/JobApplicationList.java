package com.bignerdranch.android.careernetworkingassistant;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by edwardlichtman on 10/8/15.
 */
public class JobApplicationList {
    private static JobApplicationList sJobApplication;
    private List<JobApplication> mJobApplications;

    public static JobApplicationList get(Context context) {
        if (sJobApplication == null) {
            sJobApplication = new JobApplicationList(context);
        }
        return sJobApplication;
    }

    private JobApplicationList(Context context) {
        mJobApplications = new ArrayList<>();
        for (int i = 0; i < 0; i++) {
            JobApplication jobApplication = new JobApplication();
            switch (i%4) {
                case 0:
                    jobApplication.setCompanyName("Monster #" + (-1 * ((int) (i * -.25))));
                    jobApplication.setPositionTitle("Web UI/UX Developer");
                    jobApplication.setCity("Chicago");
                    jobApplication.setState("IL");
                    jobApplication.setContactName("John Doe");
                    jobApplication.setListedDate(DMTools.TODAY_DATE);
                    jobApplication.setAppliedDate(DMTools.TODAY_DATE);
                    jobApplication.setInterviewOrganized((i % 5 == 0) ? true : false);
                    break;
                case 1:
                    jobApplication.setCompanyName("Microsoft #" + (-1*((int) (i*-.25))));
                    jobApplication.setPositionTitle("Software Engineer");
                    jobApplication.setCity("New York City");
                    jobApplication.setState("NY");
                    jobApplication.setContactName("Bill Gates");
                    jobApplication.setListedDate(DMTools.TODAY_DATE);
                    jobApplication.setAppliedDate(DMTools.TODAY_DATE);
                    jobApplication.setInterviewOrganized((i % 5 == 0) ? true : false);
                    break;
                case 2:
                    jobApplication.setCompanyName("Geico #" + (-1 * ((int) (i * -.25))));
                    jobApplication.setPositionTitle("Solutions Developer");
                    jobApplication.setCity("Washington");
                    jobApplication.setState("DC");
                    jobApplication.setContactName("The Gecko");
                    jobApplication.setListedDate(DMTools.TODAY_DATE);
                    jobApplication.setAppliedDate(DMTools.TODAY_DATE);
                    jobApplication.setInterviewOrganized((i % 5 == 0) ? true : false);

                    break;
                case 3:
                    jobApplication.setCompanyName("Apple #" + (-1 * ((int) (i * -.25))));
                    jobApplication.setPositionTitle("Software Developer");
                    jobApplication.setCity("San Francisco");
                    jobApplication.setState("CA");
                    jobApplication.setContactName(null);
                    jobApplication.setListedDate(DMTools.TODAY_DATE);
                    jobApplication.setAppliedDate(DMTools.TODAY_DATE);
                    jobApplication.setInterviewOrganized((i % 5 == 0) ? true : false);
                    break;

            }

            mJobApplications.add(jobApplication);

        }
    }

    public void addJobApplication(JobApplication j) {
        mJobApplications.add(j);
    }

    public void deleteJobApplication(JobApplication j) {
        mJobApplications.remove(j);
    }

    public List<JobApplication> getJobApplications() {
        return mJobApplications;
    }

    public JobApplication getJobApplication(UUID id) {
        for (JobApplication jobApplication : mJobApplications) {
            if (jobApplication.getId().equals(id)) {
                return jobApplication;
            }
        }
        return null;
    }
}
