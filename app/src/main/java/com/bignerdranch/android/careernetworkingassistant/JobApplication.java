package com.bignerdranch.android.careernetworkingassistant;

import java.util.Date;
import java.util.UUID;

/**
 * Created by edwardlichtman on 9/13/15.
 */
public class JobApplication {

    private UUID mId;
    
    private String mCompanyName;
    private String mPositionTitle;
    private String mCity;
    private String mState;
    private String mContactName;
    private String mWhereYouApply;
    private Date mListedDate;
    private Date mAppliedDate;
    private Boolean mInterviewOrganized;

    public JobApplication() {
        //Generate unique identifier
        mId = UUID.randomUUID();
        mCompanyName = "";
        mPositionTitle = "";
        mCity = "";
        mState = "";
        mContactName = "";
        mWhereYouApply = "";
        mListedDate = DMTools.TODAY_DATE;
        mAppliedDate = DMTools.TODAY_DATE;
        mInterviewOrganized = false;
    }

    public UUID getId() {
        return mId;
    }

    public String getCompanyName() {
        return mCompanyName;
    }

    public void setCompanyName(String companyName) {
        mCompanyName = companyName;
    }

    public String getPositionTitle() {
        return mPositionTitle;
    }

    public void setPositionTitle(String positionTitle) {
        mPositionTitle = positionTitle;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        mState = state;
    }

    public String getContactName() {
        return mContactName;
    }

    public void setContactName(String contactName) {
        mContactName = contactName;
    }

    public String getWhereYouApply() {
        return mWhereYouApply;
    }

    public void setWhereYouApply(String whereYouApply) {
        mWhereYouApply = whereYouApply;
    }

    public Date getListedDate() {
        return mListedDate;
    }

    public void setListedDate(Date listedDate) {
        mListedDate = listedDate;
    }

    public Date getAppliedDate() {
        return mAppliedDate;
    }

    public void setAppliedDate(Date appliedDate) {
        mAppliedDate = appliedDate;
    }

    public Boolean getInterviewOrganized() {
        return mInterviewOrganized;
    }

    public void setInterviewOrganized(Boolean interviewOrganized) {
        mInterviewOrganized = interviewOrganized;
    }
}
