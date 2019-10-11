package com.ccsu.cs530.successcentral.model;


public class Mentor extends User {
    private String mentorReq;
    private String attitudeForDifference;
    private String challenge;
    private String forSuccessfulFirstYear;
    private Boolean isApproved;
    private Boolean isSeniorMentor;



    // Derived property
    private Integer menteeCount;

    public Mentor() {}

    public String getMentorReq() {
        return mentorReq;
    }

    public void setMentorReq(String mentorReq) {
        this.mentorReq = mentorReq;
    }

    public String getAttitudeForDifference() {
        return attitudeForDifference;
    }

    public void setAttitudeForDifference(String attitudeForDifference) {
        this.attitudeForDifference = attitudeForDifference;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public String getForSuccessfulFirstYear() {
        return forSuccessfulFirstYear;
    }

    public void setForSuccessfulFirstYear(String forSuccessfulFirstYear) {
        this.forSuccessfulFirstYear = forSuccessfulFirstYear;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }

    public Boolean getSeniorMentor() {
        return isSeniorMentor;
    }

    public void setSeniorMentor(Boolean seniorMentor) {
        isSeniorMentor = seniorMentor;
    }

    public Integer getMenteeCount() {
        return menteeCount;
    }

    public void setMenteeCount(Integer menteeCount) {
        this.menteeCount = menteeCount;
    }


}
