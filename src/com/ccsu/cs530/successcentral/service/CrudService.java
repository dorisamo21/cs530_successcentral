package com.ccsu.cs530.successcentral.service;

import com.ccsu.cs530.successcentral.model.Mentee;
import com.ccsu.cs530.successcentral.model.Mentor;
import com.ccsu.cs530.successcentral.model.User;
import com.ccsu.cs530.successcentral.util.DatabaseConnection;

import javax.xml.bind.DatatypeConverter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrudService {
    private Connection con;



    // -------- CREATE ---------

    // Create a user in the db.
    // Remember! Email is the primary key.
    public void createUser(User user)  {
        try {
            if (user.getEmail() == null) {
                throw new Exception();
            }
            con = DatabaseConnection.getConnection();
            String qry = "INSERT INTO user VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(qry);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getStudentId());
            statement.setObject(6, user.getMajor());
            statement.setObject(7, user.getGrade());
            statement.setObject(8, user.getYear());
            statement.setString(9, user.getHobbies());
            statement.setObject(10, user.getCtHometown());
            statement.setString(11, user.getOtherHometown());
            statement.setObject(12, user.getParentEducation());
            statement.setString(13, user.getLanguage());
            statement.setString(14, user.getRace());
            statement.setString(15, user.getGender());
            statement.setString(16, "0"); //USER IS NOT AN ADMIN

            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Could not create the user." + e);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection");
            }
        }
    }

    // Create a mentee in the db.
    public void createMentee(Mentee mentee) {
        try {
            con = DatabaseConnection.getConnection();

            String qry = "INSERT INTO mentee VALUES (?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(qry);
            statement.setString(1, mentee.getEmail());
            statement.setString(2, mentee.getLookingForward());
            statement.setString(3, mentee.getWhyMentor());
            statement.setString(4, mentee.getMentor() == null ? null : mentee.getMentor().getEmail());
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println("There was a problem creating the mentee" + e);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection");
            }
        }
    }

    // Create a mentor in the db.
    public void createMentor(Mentor mentor) {
        try {
            con = DatabaseConnection.getConnection();

            // Create the mentor
            String qry = "INSERT INTO mentor VALUES (?,?,?,?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(qry);
            statement.setString(1, mentor.getEmail());
            statement.setString(2, mentor.getMentorReq());
            statement.setString(3, mentor.getAttitudeForDifference());
            statement.setString(4, mentor.getChallenge());
            statement.setString(5, mentor.getForSuccessfulFirstYear());
            statement.setObject(6, mentor.getApproved());
            statement.setObject(7, mentor.getSeniorMentor());

            statement.executeUpdate();

        } catch (Exception e) {
            System.out.println("Could not create the mentor." + e);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection");
            }
        }

    }

    //create an admin-level user in the db
    public void createAdmin(User admin) {
        try {
            if (admin.getEmail() == null) {
                throw new Exception();
            }
            con = DatabaseConnection.getConnection();

            String qry = "INSERT INTO user (email, password, is_admin) VALUES (?,?,?)";
            PreparedStatement statement = con.prepareStatement(qry);
            statement.setString(1, admin.getEmail());
            statement.setString(2, admin.getPassword());
            statement.setString(3, admin.getIsAdmin() ? "1" : "0");
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Could not create the user." + e);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection");
            }
        }
    }


    // -------- READ ---------

    // Get a user with a specific email from the db.
    // Remember that email is the primary key!
    public User getUser(String email) {
        User user = new User();
        try {
            if (email == null) {
                throw new Exception();
            }
            con = DatabaseConnection.getConnection();

            String qry = "SELECT * FROM user WHERE email = ?";
            PreparedStatement statement = con.prepareStatement(qry);
            statement.setString(1, email);
            ResultSet results = statement.executeQuery();

            if (results.next()) {
                user.setEmail(results.getString("email"));
                user.setPassword(results.getString("password"));
                user.setFirstName(results.getString("first_name"));
                user.setLastName(results.getString("last_name"));
                user.setStudentId(results.getString("student_id"));
                user.setMajor(results.getString("major"));
                user.setGrade(results.getString("grade"));
                user.setYear(results.getString("year"));
                user.setHobbies(results.getString("hobbies"));
                user.setCtHometown(results.getString("ct_hometown"));
                user.setOtherHometown(results.getString("other_hometown"));
                user.setParentEducation(results.getBoolean("parent_education"));
                user.setLanguage(results.getString("language"));
                user.setRace(results.getString("race"));
                user.setGender(results.getString("gender"));
            }
        } catch (Exception e) {
            System.out.println("Could not get the user");
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection");
            }
        }

        return user;
    }

    /**
     * Get the password of a user.
     * @param email the email of the user
     * @return the hashed password of that user.
     */
    public String getPassword(String email) {
        String pwd = null;
        try {
            if (email == null) {
                throw new Exception();
            }
            con = DatabaseConnection.getConnection();

            String qry = "SELECT password FROM user WHERE email = ?";
            PreparedStatement statement = con.prepareStatement(qry);
            statement.setString(1, email);
            ResultSet results = statement.executeQuery();

            if (results.next()) {
                pwd = results.getString("password");
            }
        } catch (Exception e) {
            System.out.println("Could not get the password of the Success Central user.");
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection");
            }
        }
        return pwd;
    }

    public int getAdminCount() {
        String qry = "SELECT COUNT(*) FROM user WHERE is_admin = 1";
        PreparedStatement statement = null;
        try {
            con = DatabaseConnection.getConnection();

            statement = con.prepareStatement(qry);
            ResultSet results = statement.executeQuery();
            results.next();
            return results.getInt("COUNT(*)");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection");
            }
        }
        return -1;
    }

    // Get a mentee with a specific email from the db.
    public Mentee getMentee(String email) {
        Mentee mentee = new Mentee();
        try {
            con = DatabaseConnection.getConnection();

            String qry = "SELECT *, town.name AS town_name FROM user NATURAL JOIN mentee JOIN major ON (user.major = major.id) JOIN town ON (user.ct_hometown = town.id) WHERE email=?";
            PreparedStatement statement = con.prepareStatement(qry);
            statement.setString(1, email);

            ResultSet results = statement.executeQuery();
            if (results.next()) {
                mentee.setEmail(results.getString("email"));
                mentee.setPassword(results.getString("password"));
                mentee.setFirstName(results.getString("first_name"));
                mentee.setLastName(results.getString("last_name"));
                mentee.setStudentId(results.getString("student_id"));
                mentee.setMajor(results.getString("name"));
                mentee.setGrade(results.getString("grade"));
                mentee.setYear(results.getString("year"));
                mentee.setHobbies(results.getString("hobbies"));
                mentee.setCtHometown(results.getString("town_name"));
                mentee.setOtherHometown(results.getString("other_hometown"));
                mentee.setParentEducation(results.getBoolean("parent_education"));
                mentee.setLanguage(results.getString("language"));
                mentee.setRace(results.getString("race"));
                mentee.setGender(results.getString("gender"));
                mentee.setEmail(email);
                mentee.setLookingForward(results.getString("looking_forward"));
                mentee.setWhyMentor(results.getString("why_mentor"));

                if (results.getString("mentor") != null) {
                    mentee.setMentor(getMentor(results.getString("mentor")));
                }
            }
        } catch (Exception e) {
            System.out.println("Could not get the mentee");
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection");
            }
        }
        return mentee;
    }


    // Get a mentor with a specific email from the db.
    public Mentor getMentor(String email) {
        Mentor mentor = new Mentor();
        try {
            con = DatabaseConnection.getConnection();

            String qry = "SELECT *, town.name AS town_name, major.name AS major_name\n" +
                    "FROM user NATURAL JOIN mentor JOIN town ON (user.ct_hometown = town.id) JOIN major ON (user.major = major.id) WHERE email = ?";
            PreparedStatement statement = con.prepareStatement(qry);
            statement.setString(1, email);

            ResultSet results = statement.executeQuery();
            if (results.next()) {
                mentor.setEmail(email);
                mentor.setPassword(results.getString("password"));
                mentor.setFirstName(results.getString("first_name"));
                mentor.setLastName(results.getString("last_name"));
                mentor.setStudentId(results.getString("student_id"));
                mentor.setMajor(results.getString("major_name"));
                mentor.setGrade(results.getString("grade"));
                mentor.setYear(results.getString("year"));
                mentor.setHobbies(results.getString("hobbies"));
                mentor.setCtHometown(results.getString("town_name"));
                mentor.setOtherHometown(results.getString("other_hometown"));
                mentor.setParentEducation(results.getBoolean("parent_education"));
                mentor.setLanguage(results.getString("language"));
                mentor.setRace(results.getString("race"));
                mentor.setGender(results.getString("gender"));
                mentor.setMentorReq(results.getString("mentor_req"));
                mentor.setAttitudeForDifference(results.getString("attitude_for_diff"));
                mentor.setChallenge(results.getString("challenge"));
                mentor.setForSuccessfulFirstYear(results.getString("for_successful_1st_year"));
                mentor.setApproved(results.getBoolean("is_approved"));
                mentor.setSeniorMentor(results.getBoolean("is_senior_mentor"));
            }
        } catch (Exception e) {
            System.out.println("Could not get the mentee");
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection");
            }
        }
        return mentor;
    }

    /**
     * Returns a list of Mentees for paging purposes.
     * @param first the first Mentor you'd like to return
     * @param total the total number of records per page
     * @return the list of Mentors to be found
     * @throws Exception
     */
    public List<Mentee> getMentees(int first, int total, String search,String sortBy) {
        List<Mentee> mentees = new ArrayList<>();
        String tmpQry = "";

        try {
            con = DatabaseConnection.getConnection();

            String qry = "SELECT * FROM user NATURAL JOIN mentee JOIN major ON user.major = major.id ";
            if(search != null){
                qry += "WHERE user.first_name like '%"+search+"%' OR user.last_name like '%"+search+"%' ";
            }
            if(sortBy != null && !sortBy.equals("")){
                if(sortBy.equals("matched_1")){
                    qry += " ORDER BY mentee.mentor DESC ";
                } else if(sortBy.equals("matched_0")){
                    qry += " ORDER BY mentee.mentor ASC ";
                }else{
                    qry += " ORDER BY user." + sortBy + " ";
                }
            }

            qry += " LIMIT ?,?";

            tmpQry = qry;

            PreparedStatement statement = this.con.prepareStatement(qry);
            statement.setInt(1, first);
            statement.setInt(2, total);

            ResultSet results = statement.executeQuery();


            while (results.next()) {
                Mentee mentee = new Mentee();
                mentee.setEmail(results.getString("email"));
                mentee.setPassword(results.getString("password"));
                mentee.setFirstName(results.getString("first_name"));
                mentee.setLastName(results.getString("last_name"));
                mentee.setStudentId(results.getString("student_id"));
                mentee.setMajor(results.getString("name"));
                mentee.setGrade(results.getString("grade"));
                mentee.setYear(results.getString("year"));
                mentee.setHobbies(results.getString("hobbies"));
                mentee.setCtHometown(results.getString("ct_hometown"));
                mentee.setOtherHometown(results.getString("other_hometown"));
                mentee.setParentEducation(results.getBoolean("parent_education"));
                mentee.setLanguage(results.getString("language"));
                mentee.setRace(results.getString("race"));
                mentee.setGender(results.getString("gender"));
                mentee.setLookingForward(results.getString("looking_forward"));
                mentee.setWhyMentor(results.getString("why_mentor"));
                mentee.setMentor(getMentor(results.getString("mentor")));

                mentees.add(mentee);
            }

        } catch (Exception e) {
            System.out.println("Could not get the mentors"+tmpQry);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection");
            }
        }

        return mentees;
    }


    /**
     * Return all mentees who don't have a mentor.
     * @return a list of mentees.
     */
    public List<Mentee> getOrphanMentees() {
        List<Mentee> mentees = new ArrayList<>();

        try {
            con = DatabaseConnection.getConnection();

            String qry = "SELECT *, town.name AS town_name, major.name AS major_name\n" +
                    "FROM user NATURAL JOIN mentee JOIN town ON (user.ct_hometown = town.id) JOIN major ON (user.major = major.id)\n" +
                    "WHERE mentee.mentor IS NULL";
            PreparedStatement statement = con.prepareStatement(qry);

            ResultSet results = statement.executeQuery();


            while (results.next()) {
                Mentee mentee = new Mentee();
                mentee.setEmail(results.getString("email"));
                mentee.setPassword(results.getString("password"));
                mentee.setFirstName(results.getString("first_name"));
                mentee.setLastName(results.getString("last_name"));
                mentee.setStudentId(results.getString("student_id"));
                mentee.setMajor(results.getString("major_name"));
                mentee.setGrade(results.getString("grade"));
                mentee.setYear(results.getString("year"));
                mentee.setHobbies(results.getString("hobbies"));
                mentee.setCtHometown(results.getString("town_name"));
                mentee.setOtherHometown(results.getString("other_hometown"));
                mentee.setParentEducation(results.getBoolean("parent_education"));
                mentee.setLanguage(results.getString("language"));
                mentee.setRace(results.getString("race"));
                mentee.setGender(results.getString("gender"));
                mentee.setLookingForward(results.getString("looking_forward"));
                mentee.setWhyMentor(results.getString("why_mentor"));
                mentee.setMentor(getMentor(results.getString("mentor")));



                mentees.add(mentee);

            }

        } catch (Exception e) {
            System.out.println("Could not get the orphan mentees. Poor bastards.");
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection");
            }
        }

        return mentees;
    }


    /**
     * Return a list of mentees associated with a specific mentor, identified by mentor id
     * @param mentorEmail the email ID of the mentor.
     * @return the mentor's IDs.
     */
    public List<Mentee> getMyMentees(String mentorEmail) {
        List<Mentee> mentees = new ArrayList<>();
        try {
            con = DatabaseConnection.getConnection();

            String qry = "SELECT *, major.name as major_name\n" +
                    "FROM user NATURAL JOIN mentee JOIN major ON (user.major=major.id)\n" +
                    "WHERE mentee.mentor = ?";
            PreparedStatement statement = con.prepareStatement(qry);
            statement.setString(1, mentorEmail);

            ResultSet results = statement.executeQuery();

            while (results.next()) {
                Mentee mentee = new Mentee();
                mentee.setEmail(results.getString("email"));
                mentee.setPassword(results.getString("password"));
                mentee.setFirstName(results.getString("first_name"));
                mentee.setLastName(results.getString("last_name"));
                mentee.setStudentId(results.getString("student_id"));
                mentee.setMajor(results.getString("major_name"));
                mentee.setGrade(results.getString("grade"));
                mentee.setYear(results.getString("year"));
                mentee.setHobbies(results.getString("hobbies"));
                mentee.setCtHometown(results.getString("ct_hometown"));
                mentee.setOtherHometown(results.getString("other_hometown"));
                mentee.setParentEducation(results.getBoolean("parent_education"));
                mentee.setLanguage(results.getString("language"));
                mentee.setRace(results.getString("race"));
                mentee.setGender(results.getString("gender"));
                mentee.setLookingForward(results.getString("looking_forward"));
                mentee.setWhyMentor(results.getString("why_mentor"));
                mentee.setMentor(getMentor(results.getString("mentor")));

                mentees.add(mentee);
            }
        } catch (Exception e) {
            System.out.println("Something went wrong getting all the mentees for a given mentor" + e);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection");
            }
        }
        return mentees;
    }

    /**
     * Returns a list of Mentors which fits to the conditions
     * @param first the first Mentor you'd like to return
     * @param total the total number of records per page
     * @return the list of Mentors to be found
     * @throws Exception
     */
    public List<Mentor> getMentorsWithMenteeCount(int first, int total,String findName,String sortBy) {
        List<Mentor> mentors = new ArrayList<>();
        Map<Integer, String> majors = getAllMajors();
        String tmpQry = "";

        try {
            con = DatabaseConnection.getConnection();

            String qry = "SELECT COUNT(mentee.email) as mentee_count, user.*, mentor.*, major.*, town.name AS town_name ";
            qry += "FROM user NATURAL JOIN mentor LEFT JOIN mentee ON user.email = mentee.mentor JOIN major ON (user.major=major.id) JOIN town ON (user.ct_hometown = town.id) ";
            if(findName != null){
                qry += "WHERE user.first_name like '%"+findName+"%' OR user.last_name like '%"+findName+"%' ";
            }
            qry += "GROUP BY mentor.email ";
            if(sortBy != null && !sortBy.equals("")){
                if(sortBy.equals("mentee_cnt_desc")){
                    qry += " ORDER BY mentee_count DESC ";
                } else if(sortBy.equals("mentee_cnt_asc")) {
                    qry += " ORDER BY mentee_count ASC ";
                } else if(sortBy.equals("is_approved_1")){
                    qry += " ORDER BY mentor.is_approved DESC";
                } else if(sortBy.equals("is_approved_0")){
                    qry += " ORDER BY mentor.is_approved ASC";
                }else{
                    qry += " ORDER BY user." + sortBy + "";
                }
            }
            qry += " LIMIT ?,? ";

            tmpQry = qry;
            PreparedStatement statement = con.prepareStatement(qry);
            statement.setInt(1, first);
            statement.setInt(2, total);

            ResultSet results = statement.executeQuery();

            while (results.next()) {
                Mentor mentor = new Mentor();
                mentor.setEmail(results.getString("email"));
                mentor.setPassword(results.getString("password"));
                mentor.setFirstName(results.getString("first_name"));
                mentor.setLastName(results.getString("last_name"));
                mentor.setStudentId(results.getString("student_id"));
                mentor.setMajor(majors.get(Integer.parseInt(results.getString("major"))));
                mentor.setGrade(results.getString("grade"));
                mentor.setYear(results.getString("year"));
                mentor.setHobbies(results.getString("hobbies"));
                mentor.setCtHometown(results.getString("town_name"));
                mentor.setOtherHometown(results.getString("other_hometown"));
                mentor.setParentEducation(results.getBoolean("parent_education"));
                mentor.setLanguage(results.getString("language"));
                mentor.setRace(results.getString("race"));
                mentor.setGender(results.getString("gender"));
                mentor.setMentorReq(results.getString("mentor_req"));
                mentor.setAttitudeForDifference(results.getString("attitude_for_diff"));
                mentor.setChallenge(results.getString("challenge"));
                mentor.setForSuccessfulFirstYear(results.getString("for_successful_1st_year"));
                mentor.setApproved(results.getBoolean("is_approved"));
                mentor.setSeniorMentor(results.getBoolean("is_senior_mentor"));
                mentor.setMenteeCount(results.getInt("mentee_count"));

                mentors.add(mentor);
            }

        } catch (Exception e) {
            System.out.println("Could not get the mentors with mentee count. " + tmpQry);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection");
            }
        }

        return mentors;
    }

    /**
     * Returns a list of Mentors which fits to the conditions
     * @param first the first Mentor you'd like to return
     * @param total the total number of records per page
     * @return the list of Mentors to be found
     * @throws Exception
     */
    public List<Mentor> getMatchedMentorsWithMenteeCount(int first, int total,String findName,String sortBy) {
        List<Mentor> mentors = new ArrayList<>();
        Map<Integer, String> majors = getAllMajors();
        String tmpQry = "";

        try {
            con = DatabaseConnection.getConnection();

            String qry = "SELECT COUNT(mentee.email) as mentee_count, user.*, mentor.*, major.*, town.name AS town_name ";
            qry += "FROM user NATURAL JOIN mentor LEFT JOIN mentee ON user.email = mentee.mentor JOIN major ON (user.major=major.id) JOIN town ON (user.ct_hometown = town.id) ";
            qry += "GROUP BY mentor.email ";
            qry += "HAVING mentee_count>0 ";
            if(sortBy != null && !sortBy.equals("")){
                if(sortBy.equals("mentee_cnt_desc")){
                    qry += " ORDER BY mentee_count DESC ";
                } else if(sortBy.equals("mentee_cnt_asc")) {
                    qry += " ORDER BY mentee_count ASC ";
                } else if(sortBy.equals("is_approved_1")){
                    qry += " ORDER BY mentor.is_approved DESC";
                } else if(sortBy.equals("is_approved_0")){
                    qry += " ORDER BY mentor.is_approved ASC";
                }else{
                    qry += " ORDER BY user." + sortBy + "";
                }
            }
            qry += " LIMIT ?,? ";

            tmpQry = qry;
            PreparedStatement statement = con.prepareStatement(qry);
            statement.setInt(1, first);
            statement.setInt(2, total);

            ResultSet results = statement.executeQuery();

            while (results.next()) {
                Mentor mentor = new Mentor();
                mentor.setEmail(results.getString("email"));
                mentor.setPassword(results.getString("password"));
                mentor.setFirstName(results.getString("first_name"));
                mentor.setLastName(results.getString("last_name"));
                mentor.setStudentId(results.getString("student_id"));
                mentor.setMajor(majors.get(Integer.parseInt(results.getString("major"))));
                mentor.setGrade(results.getString("grade"));
                mentor.setYear(results.getString("year"));
                mentor.setHobbies(results.getString("hobbies"));
                mentor.setCtHometown(results.getString("town_name"));
                mentor.setOtherHometown(results.getString("other_hometown"));
                mentor.setParentEducation(results.getBoolean("parent_education"));
                mentor.setLanguage(results.getString("language"));
                mentor.setRace(results.getString("race"));
                mentor.setGender(results.getString("gender"));
                mentor.setMentorReq(results.getString("mentor_req"));
                mentor.setAttitudeForDifference(results.getString("attitude_for_diff"));
                mentor.setChallenge(results.getString("challenge"));
                mentor.setForSuccessfulFirstYear(results.getString("for_successful_1st_year"));
                mentor.setApproved(results.getBoolean("is_approved"));
                mentor.setSeniorMentor(results.getBoolean("is_senior_mentor"));
                mentor.setMenteeCount(results.getInt("mentee_count"));

                mentors.add(mentor);
            }

        } catch (Exception e) {
            System.out.println("Could not get the mentors with mentee count. " + tmpQry);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection");
            }
        }

        return mentors;
    }



    /**
     * Returns a list of Approvede Mentors
     * @param first the first Mentor you'd like to return
     * @param total the total number of records per page
     * @return the list of Mentors to be found
     * @throws Exception
     */
    public List<Mentor> getApprovedMentorsWithMenteeCount(int first, int total) {
        List<Mentor> mentors = new ArrayList<>();
        Map<Integer, String> majors = getAllMajors();

        try {
            con = DatabaseConnection.getConnection();

            String qry = "SELECT COUNT(mentee.email) as mentee_count, user.*, mentor.*, major.*, town.name AS town_name ";
            qry += "FROM user NATURAL JOIN mentor LEFT JOIN mentee ON user.email = mentee.mentor JOIN major ON (user.major=major.id) JOIN town ON (user.ct_hometown = town.id) ";
            qry += "WHERE is_approved=1 GROUP BY mentor.email LIMIT ?,?";
            PreparedStatement statement = con.prepareStatement(qry);
            statement.setInt(1, first);
            statement.setInt(2, total);

            ResultSet results = statement.executeQuery();

            while (results.next()) {
                Mentor mentor = new Mentor();
                mentor.setEmail(results.getString("email"));
                mentor.setPassword(results.getString("password"));
                mentor.setFirstName(results.getString("first_name"));
                mentor.setLastName(results.getString("last_name"));
                mentor.setStudentId(results.getString("student_id"));
                mentor.setMajor(majors.get(Integer.parseInt(results.getString("major"))));
                mentor.setGrade(results.getString("grade"));
                mentor.setYear(results.getString("year"));
                mentor.setHobbies(results.getString("hobbies"));
                mentor.setCtHometown(results.getString("town_name"));
                mentor.setOtherHometown(results.getString("other_hometown"));
                mentor.setParentEducation(results.getBoolean("parent_education"));
                mentor.setLanguage(results.getString("language"));
                mentor.setRace(results.getString("race"));
                mentor.setGender(results.getString("gender"));
                mentor.setMentorReq(results.getString("mentor_req"));
                mentor.setAttitudeForDifference(results.getString("attitude_for_diff"));
                mentor.setChallenge(results.getString("challenge"));
                mentor.setForSuccessfulFirstYear(results.getString("for_successful_1st_year"));
                mentor.setApproved(results.getBoolean("is_approved"));
                mentor.setSeniorMentor(results.getBoolean("is_senior_mentor"));
                mentor.setMenteeCount(results.getInt("mentee_count"));

                mentors.add(mentor);
            }

        } catch (Exception e) {
            System.out.println("Could not get the mentors with mentee count. " +e);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection");
            }
        }

        return mentors;
    }


    /**
     * Get all the mentors in the program
     * @return a list of mentors.
     */
    public List<Mentor> getAllMentors() {
        int mentorCount = countMentors();
        return getMentorsWithMenteeCount(0, mentorCount, "","");
    }

    /**
     * Get all the mentors in the program
     * @return a list of mentors.
     */
    public List<Mentor> getApprovedMentors() {
        int mentorCount = countApprovedMentors();
        return getApprovedMentorsWithMenteeCount(0, mentorCount);
    }

    /**
     * Get a map of all majors and their corresponding IDs.
     * @return a map like "Accounting" -> 1, "Anthropology" -> 2
     */
    public Map<Integer, String> getAllMajors() {
        Map<Integer, String> map = new HashMap<>();

        try {
            con = DatabaseConnection.getConnection();

            PreparedStatement statement = con.prepareStatement("SELECT * FROM major");
            ResultSet results = statement.executeQuery();

            while (results.next()) {
                map.put(results.getInt("id"), results.getString("name"));
            }
        } catch (Exception e) {
            System.out.println("Could not get all the majors.");
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection");
            }
        }
        return map;
    }


    /**
     * Get a map of all Connecticut towns and their corresponding IDs.
     * @return a map of all Connecticut towns.
     */
    public Map<Integer, String> getAllConnecticutTowns() {
        Map<Integer, String> map = new HashMap<>();

        try {
            con = DatabaseConnection.getConnection();

            PreparedStatement statement = con.prepareStatement("SELECT * FROM town");
            ResultSet results = statement.executeQuery();

            while (results.next()) {
                map.put(results.getInt("id"), results.getString("name"));
            }
        } catch (Exception e) {
            System.out.println("Could not get all the majors.");
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection");
            }
        }
        return map;
    }

    /**
     * Checks if user is an admin in the database
     * @param email of user
     * @return confirmation if user is admin
     */
    public boolean isAdmin(String email) {
        try {
            if (email == null) {
                throw new Exception();
            }
            con = DatabaseConnection.getConnection();

            String qry = "SELECT is_admin FROM user WHERE email = ?";
            PreparedStatement statement = con.prepareStatement(qry);
            statement.setString(1, email);
            ResultSet results = statement.executeQuery();

            if (results.next()) {
                return results.getInt("is_admin") == 1;
            }
        } catch (Exception e) {
            System.out.println("Could not get admin status of the Success Central user.");
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection");
            }
        }
        return false;
    }





    // -------- UPDATE ---------

    // Update a user in the db.
    public void updateUser(User user) {
        try {
            con = DatabaseConnection.getConnection();

            String qry = "UPDATE user SET password = ?, first_name = ?, last_name = ?, ";
            qry += "student_id = ?, major = ?, grade = ?, year = ?, hobbies = ?, ct_hometown = ?, other_hometown = ?, ";
            qry += "parent_education = ?, language = ?, race = ?, gender = ? ";
            qry += "WHERE email = ?";

            PreparedStatement statement = con.prepareStatement(qry);

            if (user.getEmail() == null) {
                throw new Exception();
            }

            statement.setString(1, user.getPassword());

            statement.setString(2, user.getFirstName());

            statement.setString(3, user.getLastName());

            statement.setString(4, user.getStudentId());

            statement.setObject(5, user.getMajor());

            statement.setObject(6, user.getGrade());

            statement.setObject(7, user.getYear());

            statement.setString(8, user.getHobbies());

            statement.setObject(9, user.getCtHometown());

            statement.setString(10, user.getOtherHometown());

            statement.setObject(11, user.getParentEducation());

            statement.setString(12, user.getLanguage());

            statement.setString(13, user.getRace());

            statement.setString(14, user.getGender());

            statement.setString(15, user.getEmail());


            statement.executeUpdate();

        } catch (Exception e) {
            System.out.println("Could not update the entire user" + e);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection");
            }
        }
    }

    // Update a user in the db.
    public void updateUserPassword(User user) {
        try {
            con = DatabaseConnection.getConnection();

            String qry = "UPDATE user SET password = ? WHERE email = ?";

            PreparedStatement statement = con.prepareStatement(qry);

            if (user.getEmail() == null) {
                throw new Exception();
            }

            if (user.getPassword() != null) {
                statement.setString(1, user.getPassword());
            }
            statement.setString(2, user.getEmail());


            statement.executeUpdate();

        } catch (Exception e) {
            System.out.println("Could not update the user password "+e);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection");
            }
        }
    }

    // Update a mentee in the db.
    public void updateMentee(Mentee mentee) {

        try {
            con = DatabaseConnection.getConnection();

            String qry = "UPDATE mentee SET looking_forward = ?, why_mentor = ?, mentor = ? WHERE email = ?";
            PreparedStatement statement = con.prepareStatement(qry);
            statement.setString(1, mentee.getLookingForward());
            statement.setString(2, mentee.getWhyMentor());
            statement.setString(3, mentee.getMentor() == null ? null : mentee.getMentor().getEmail());
            statement.setString(4, mentee.getEmail());
            statement.executeUpdate();

        } catch (Exception e) {
            System.out.println("Could not update the mentee");
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection");
            }
        }
    }

    //Send Matching notice to mentor and his/her mentees
    // Update a mentee in the db.
    public void noticeMatched(String mentorEmail) {
        try {
            con = DatabaseConnection.getConnection();

            EmailService email = new EmailService();
            Mentor mentor = getMentor(mentorEmail);
            Map<Integer, String> majors = getAllMajors();

            email.matchNoticeToMentor(mentor,getMyMentees(mentorEmail),majors);

            for(int i=0;i<getMyMentees(mentorEmail).size();i++) {

                email.matchNoticeToMentee(getMyMentees(mentorEmail).get(i), mentor);
            }

        } catch (Exception e) {
            System.out.println("Could not send notices" + e);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection");
            }
        }
    }

    // Update a mentee in the db.
    public void updateMenteeWithMentor(String menteeEmail, String mentorEmail) {
        System.out.println("Trying to update mentee with mentor");
        System.out.println(menteeEmail);
        System.out.println(mentorEmail);
        try {
            con = DatabaseConnection.getConnection();

            String qry = "UPDATE mentee SET mentor = ? WHERE email = ?";
            PreparedStatement statement = con.prepareStatement(qry);
            statement.setString(1, mentorEmail);
            statement.setString(2, menteeEmail);
            statement.executeUpdate();
            System.out.println("Successful Update");

        } catch (Exception e) {
            System.out.println("Could not update the mentee");
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection");
            }
        }
    }

    // Update a mentor in the db.
    public void updateMentor(Mentor mentor) {
        try {
            con = DatabaseConnection.getConnection();

            String qry = "UPDATE mentor SET mentor_req = ?, attitude_for_diff = ?, challenge = ?, for_successful_1st_year = ?, ";
            qry += "is_approved = ?, is_senior_mentor = ? WHERE email = ?";
            PreparedStatement statement = con.prepareStatement(qry);
            statement.setString(1, mentor.getMentorReq());
            statement.setString(2, mentor.getAttitudeForDifference());
            statement.setString(3, mentor.getChallenge());
            statement.setString(4, mentor.getForSuccessfulFirstYear());
            statement.setObject(5, mentor.getApproved() ? 1 : 0);
            statement.setObject(6, mentor.getSeniorMentor() ? 1 : 0);

            statement.executeUpdate();

        } catch (Exception e) {
            System.out.println("Could not update the mentor");
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection");
            }
        }
    }

    // Update a mentor in the db.
    public void updateMentorLevel(Mentor mentor) {
        try {
            con = DatabaseConnection.getConnection();

            String qry = "UPDATE mentor SET is_senior_mentor=? WHERE email = ?";
            PreparedStatement statement = con.prepareStatement(qry);
   //            statement.setInt(5, mentor.getApproved() ? 1 : 0);
            statement.setInt(1, mentor.getSeniorMentor() ? 0 : 1);
            statement.setString(2, mentor.getEmail());
            statement.executeUpdate();

        } catch (Exception e) {
            System.out.println("Could not update the mentor");
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection");
            }
        }
    }

    //------ ACCEPT AS A MENTOR ------
    // Accept as a mentor (
    public void updateMentorApproval(Mentor mentor) {
        try {
            con = DatabaseConnection.getConnection();

            String qry = "UPDATE mentor SET is_approved=? WHERE email = ?";
            PreparedStatement statement = con.prepareStatement(qry);
            statement.setInt(1, mentor.getApproved() ? 0 : 1);
            statement.setString(2, mentor.getEmail());
            statement.executeUpdate();

            // emailing part
            EmailService email = new EmailService();
            email.sendEmailToApproved(mentor.getEmail());

        } catch (Exception e) {
            System.out.println("Could not update the mentor"+e);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection");
            }
        }
    }




    // ---------- DELETE ----------

    // -------- DELETE ---------
    // Delete a mentee in the db.
    public void deleteMentee(String email) {

        try {
            con = DatabaseConnection.getConnection();

            String qry1 = "DELETE FROM mentee WHERE email=?";
            PreparedStatement statement1 = con.prepareStatement(qry1);
//            statement.setString(1, mentee.getEmail());
            statement1.setString(1, email);
            statement1.executeUpdate();

            String qry2 = "DELETE FROM user WHERE email=?";
            PreparedStatement statement2 = con.prepareStatement(qry2);
            statement2.setString(1, email);
            statement2.executeUpdate();

        } catch (Exception e) {
            System.out.println("Could not delete the mentee");
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection");
            }
        }
    }

    // -------- DELETE ---------
    // Delete a mentor in the db.
    // Update the mentee to have null
    public void deleteMentor(Mentor mentor) {

        try {
            con = DatabaseConnection.getConnection();

            String qry1 = "UPDATE mentee set mentor=null WHERE mentor=?";
            PreparedStatement statement1 = con.prepareStatement(qry1);
            statement1.setString(1,mentor.getEmail());
            statement1.executeUpdate();

            String qry2 = "DELETE FROM mentor WHERE email=?";
            PreparedStatement statement2 = con.prepareStatement(qry2);
            statement2.setString(1, mentor.getEmail());
            statement2.executeUpdate();

            String qry3 = "DELETE FROM user WHERE email=?";
            PreparedStatement statement3 = con.prepareStatement(qry3);
            statement3.setString(1, mentor.getEmail());
            statement3.executeUpdate();

        } catch (Exception e) {
            System.out.println("Could not delete the mentor");
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection");
            }
        }
    }






    // -------- OTHER ----------

    /**
     * Return the total number of mentors.
     * @return total number of mentors.
     */
    public int countMentors() {
        int mentorCount = 0;
        try {
            con = DatabaseConnection.getConnection();

            String qry = "SELECT COUNT(*) FROM mentor";
            PreparedStatement statement = con.prepareStatement(qry);
            ResultSet results = statement.executeQuery();
            if (results.next()) { // need to do this to get first result
                mentorCount = results.getInt("COUNT(*)");
            }
        } catch (Exception e) {
            System.out.println("There was a problem counting the mentors");
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection");
            }
        }

        return mentorCount;
    }
    /**
     * Return the total number of mentors.
     * @return total number of mentors.
     */
    public int countMatchedMentors() {
        int mentorCount = 0;
        try {
            con = DatabaseConnection.getConnection();

            String qry = "SELECT count(DISTINCT(mentor)) FROM mentee";
            PreparedStatement statement = con.prepareStatement(qry);
            ResultSet results = statement.executeQuery();
            if (results.next()) { // need to do this to get first result
                mentorCount = results.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("There was a problem counting the mentors");
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection");
            }
        }
        return mentorCount;
    }


    /**
     * Return the total number of mentors.
     * @return total number of mentors.
     */
    public int countApprovedMentors() {
        int mentorCount = 0;
        try {
            con = DatabaseConnection.getConnection();

            String qry = "SELECT COUNT(*) FROM mentor WHERE is_approved=1";
            PreparedStatement statement = con.prepareStatement(qry);
            ResultSet results = statement.executeQuery();
            if (results.next()) { // need to do this to get first result
                mentorCount = results.getInt("COUNT(*)");
            }
        } catch (Exception e) {
            System.out.println("There was a problem counting the mentors");
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection");
            }
        }
        return mentorCount;
    }

    /**
     * Return the total number of mentees.
     * @return total number of mentees.
     */
    public int countMentees() {
        int menteeCount = 0;
        try {
            con = DatabaseConnection.getConnection();

            String qry = "SELECT COUNT(*) FROM mentee";
            PreparedStatement statement = con.prepareStatement(qry);
            ResultSet results = statement.executeQuery();
            if (results.next()) { // need to do this to get first result
                menteeCount = results.getInt("COUNT(*)");
            }
        } catch (Exception e) {
            System.out.println("There was a problem counting the mentees");
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection");
            }
        }
        return menteeCount;
    }

    /**
     * Return the user associated with a forgot password token.
     * @param token
     * @return the user associated with that token.
     */
    public String getUserEmailFromForgotPasswordToken(String token) {
        String userEmail = null;
        try {
            con = DatabaseConnection.getConnection();

            String qry = "SELECT email FROM password_reset WHERE token = ?";
            PreparedStatement statement = con.prepareStatement(qry);
            statement.setString(1, token);
            ResultSet results = statement.executeQuery();
            if (results.next()) { // need to do this to get first result
                userEmail = results.getString("email");
            }
        } catch (Exception e) {
            System.out.println("There was a problem retrieving the user's email with the forgotten password");
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection");
            }
        }
        return userEmail;
    }

    /**
     * Delete a request to change a password.
     * @param token the identifier of the password change request to be deleted.
     */
    public void deleteChangePasswordRequest(String token) {
        try {
            con = DatabaseConnection.getConnection();

            String qry = "DELETE FROM password_reset WHERE token = ?";
            PreparedStatement statement = con.prepareStatement(qry);
            statement.setString(1, token);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println("There was a problem deleting the expired password change request.");
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection");
            }
        }
    }

    /**
     * Delete a request to change a password.
     * @param email the user's email
     */
    public void deleteChangePasswordRequestForUser(String email) {
        try {
            con = DatabaseConnection.getConnection();

            String qry = "DELETE FROM password_reset WHERE email = ?";
            PreparedStatement statement = con.prepareStatement(qry);
            statement.setString(1, email);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println("There was a problem deleting the users password change requests.");
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection");
            }
        }
    }

    /**
     * Create a change password record
     * @param email the email of the user.
     * @param token the unique identifier to pass in as a url parameter.
     */
    public void createPasswordReset(String email, String token) {
        try {
            con = DatabaseConnection.getConnection();

            String qry = "INSERT INTO password_reset VALUES (?,?)";
            PreparedStatement statement = con.prepareStatement(qry);
            statement.setString(1, email);
            statement.setString(2, token);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println("There was a problem creating the password reset");
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection");
            }
        }
    }
}