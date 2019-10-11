package com.ccsu.cs530.successcentral.model;

public class Match implements Comparable<Match> {
    private Mentor mentor;
    private Mentee mentee;
    private int compatibility;

    public String percentMatch;

    public Match() {}

    public Match(Mentor mentor, Mentee mentee) {
        this.mentor = mentor;
        this.mentee = mentee;
    }

    public Mentor getMentor() {
        return mentor;
    }

    public void setMentor(Mentor mentor) {
        this.mentor = mentor;
    }

    public Mentee getMentee() {
        return mentee;
    }

    public void setMentee(Mentee mentee) {
        this.mentee = mentee;
    }

    public int getCompatibility() {
        return compatibility;
    }

    public void setCompatibility(int compatibility) {

        this.compatibility = compatibility;
        this.percentMatch = (compatibility * 100 / 11) + "%";
    }

    public boolean getGenderMatch() {
        if (mentor != null && mentee != null) {
            if (mentor.getGender() != null && mentee.getGender() != null) {
                return (mentor.getGender().equalsIgnoreCase(mentee.getGender()));
            }
            return false;
        } else {
            return false;
        }
    }

    public boolean getHometownMatch() {
        if (mentor != null && mentee != null) {
            if (mentor.getCtHometown() != null && mentee.getCtHometown() != null) {
                if (mentor.getCtHometown().equals(mentee.getCtHometown())) {
                    if (!mentor.getCtHometown().equals("Outside Connecticut")) {
                        return true;
                    } else {
                        return (mentor.getOtherHometown().equals(mentee.getOtherHometown()));
                    }
                } else {
                    return false;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    public boolean checkHobby(String hobby, User user) {
        if (user != null) {
            if (user.getHobbiesArray() != null){
                for (String hobbyMentee : user.getHobbiesArray()) {
                    if (hobby.equals(hobbyMentee)) {
                        return true;
                    }
                }
            }else { return false; }
        }
        return false;
    }

    public boolean checkLanguage(String language, User user) {
        if (user != null) {
            if (user.getLanguageArray() != null) {
                for (String languageMentee : user.getLanguageArray()) {
                    if (language.equals(languageMentee)) {
                        return true;
                    }
                }
            } else { return false; }
        }
        return false;
    }

    public boolean checkRace(String race, User user) {
        if (user != null) {
            if (user.getLanguageArray() != null) {
                for (String raceMentee : user.getRaceArray()) {
                    if (race.equals(raceMentee)) {
                        return true;
                    }
                }
            } else {return false;}
        }
        return false;
    }

    public boolean getMajorMatch() {
        if (mentor != null && mentee != null) {
            if (mentor.getMajor() != null && mentee.getMajor() != null) {
                return (mentor.getMajor().equalsIgnoreCase(mentee.getMajor()));
            }
            return false;
        } else {
            return false;
        }
    }

    public String getPercentMatch() {
        return percentMatch;
    }

    public boolean getParentalEdMatch(){
        if (mentor != null && mentee != null) {
            if (mentor.getParentEducation() != null && mentee.getParentEducation() != null) {
                return (mentor.getParentEducation().equals(mentee.getParentEducation()));
            }
            return false;
        } else {
            return false;
        }    }

    @Override
    public int compareTo(Match other) {
        if (compatibility > other.compatibility) {
            return 1;
        } else if (compatibility < other.compatibility) {
            return -1;
        } else {
            // for now, if their compatibilities are the same, just sort by name
            String fullName = mentee.getLastName() + mentee.getFirstName();
            String otherFullName = other.getMentor().getLastName() + other.getMentor().getFirstName();
            return (fullName.compareTo(otherFullName));
        }
    }

//function not currently working..
    public String outputRow(String att, Match match){
        String result = "";
        if (att.equals("first_name")){
            result += "<th class= text-center col-2><a href=MentorInfo>" + getAttribute(match.mentor, att) + "</td>";
        }
        else if (att.equals(("percent_match"))){
            result += "<th class= text-center col-2>" + getAttribute(match.mentor, att) + "</td>";
        }
        else if (getAttribute(match.mentor, att).equals(getAttribute(match.mentee, att))){
            result += "<td class=table-success col-2>" + getAttribute(match.mentor, att) + "</td>";
        }else{
            result += "<td class=table-danger col-2>" + getAttribute(match.mentor, att) + "</td>" ;
        }
        return result;
    }

    public String getAttribute (User user, String attribute) {
        String result = "";
        switch(attribute) {
            case "email":
                result = user.getEmail();
                break;
            case "first_name":
                result = user.getFirstName();
                break;
            case "last_name":
                result = user.getLastName();
                break;
            case "major":
                result = user.getMajor();
                break;
            case "hobby":
                result = user.getHobbies();
                break;
            case "hometown":
                result = user.getCtHometown();
                break;
            case "language":
                result = user.getLanguage();
                break;
            case "race":
                result = user.getRace();
                break;
            case "gender":
                result = user.getGender();
                break;
        }
        return result;
    }
}
