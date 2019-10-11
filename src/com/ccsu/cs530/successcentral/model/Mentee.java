package com.ccsu.cs530.successcentral.model;


public class Mentee extends User {
    private String lookingForward;
    private String whyMentor;
    private Mentor mentor;

    public Mentee() {}

    public String getLookingForward() {
        return lookingForward;
    }

    public void setLookingForward(String lookingForward) {
        this.lookingForward = lookingForward;
    }

    public String getWhyMentor() {
        return whyMentor;
    }

    public void setWhyMentor(String whyMentor) {
        this.whyMentor = whyMentor;
    }

    public Mentor getMentor() {
        return mentor;
    }

    public void setMentor(Mentor mentor) {
        this.mentor = mentor;
    }
}
