package com.ccsu.cs530.successcentral.model;

public class User {

   private String email;
   private String password;
   private String firstName;
   private String lastName;
   private String studentId;
   private String major;
   private String grade;
   private String year;
   private String hobbies;
   private String ctHometown;
   private String otherHometown;
   private Boolean parentEducation;
   private String language;
   private String race;
   private String gender;
   private Boolean isAdmin;



   public User() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getHobbies() {
        return hobbies;
    }

    public String[] getHobbiesArray() {
       String[] hobbiesArray = null;
       if (this.hobbies != null) {
           hobbiesArray = this.hobbies.split(",");
       }
       return hobbiesArray;
    }

    public String getHobbiesList() {
        String[] hobbiesArray = null;
        String res = "";
        if (this.hobbies != null) {
            hobbiesArray = this.hobbies.split(",");
            for (int i=0; i<hobbiesArray.length; i++){
                res += hobbiesArray[i] + " \n";
            }
        }
        return res;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getCtHometown() {
        return ctHometown;
    }

    public String getHometown() {
        if (this.ctHometown != null) {
            if (this.ctHometown.equals("Outside Connecticut")){
                return otherHometown;
            } else {
                return ctHometown;
            }
        }
        return ctHometown;
    }

    public void setCtHometown(String ctHometown) {
        this.ctHometown = ctHometown;
    }

    public String getOtherHometown() {
        return otherHometown;
    }

    public void setOtherHometown(String otherHometown) {
        this.otherHometown = otherHometown;
    }

    public Boolean getParentEducation() {
           return this.parentEducation;
    }

    public void setParentEducation(Boolean parentEducation) {
        this.parentEducation = parentEducation;
    }

    public String getLanguage() {
           return this.language;
    }

    public String[] getLanguageArray() {
        String[] languageArray = new String[]{};
        if (this.language != null) {
            languageArray = this.language.split(",");
        }
        return languageArray;
    }

    public String getLanguageList() {
        String[] languageArray = null;
        String res = "";
        if (this.language != null) {
            languageArray = this.language.split(",");
            for (int i=0; i<languageArray.length; i++){
                res += languageArray[i] + " \n";
            }
        }
        return res;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getRace() {
        return race;
    }

    public String[] getRaceArray() {
        String[] raceArray = null;
        if (this.race != null) {
            raceArray = this.race.split(",");
        }
        return raceArray;
    }

    public String getRaceList() {
        String[] raceArray = null;
        String res = "";
        if (this.race != null) {
            raceArray = this.race.split(",");
            for (int i=0; i<raceArray.length; i++){
                res += raceArray[i] + " \n";
            }
        }
        return res;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getGender() {
       return this.gender;
//       if (this.gender != null && this.gender.equals("Male")){
//           return "Male";
//       } else {
//           return "Female";
//       }
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Boolean getIsAdmin() {
        return this.isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
