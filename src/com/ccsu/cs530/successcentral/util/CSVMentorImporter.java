package com.ccsu.cs530.successcentral.util;


import com.ccsu.cs530.successcentral.model.Mentee;
import com.ccsu.cs530.successcentral.model.Mentor;
import com.ccsu.cs530.successcentral.service.CrudService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.rmi.ServerException;
import java.sql.*;
import javax.naming.*;
import javax.servlet.http.Part;
import javax.sql.DataSource;
import java.util.Arrays;

@SuppressWarnings("serial")
@WebServlet("/dev/csvimport")
@MultipartConfig
public class CSVMentorImporter extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServerException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        writer.println("<h2>Import Mentors from CSV?</h2>");
        writer.println("<form action=\"/scapp/dev/csvimport\" method=\"post\" enctype=\"multipart/form-data\">");
        writer.println("<input type=\"file\" name=\"csv\">");
        writer.println("<select class=\"form-control\" name=\"usertype\">" +
                "<option>Mentors</option>" +
                "<option>Mentees</option>" +
                "<option>Towns</option>" +
                "<option>Majors</option>" +
                "</select>");
        writer.println("<input type=\"submit\" value=\"Import\">");
        writer.println("</form>");
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServerException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        String importType = req.getParameter("usertype");
        if (false) {

        }
//        if (importType.equals("Mentors")){
//            writer.println("Importing not supported for type " + importType);
//        }
        else {
            try {
                Part filePart = req.getPart("csv"); // Retrieves <input type="file" name="file">
                InputStream fileStream = filePart.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(fileStream));
                //confirm correct type
                String header = br.readLine(); //skip header
                String[] headerItems = header.split(",");
                if (headerItems.length == 1) {
                    String line;
                    int counter = 0;
                    DatabaseConnection dc = new DatabaseConnection();
                    dc.open();
                    while ((line = br.readLine()) != null) {
                        if (importType.equals("Towns") && headerItems[0].equals("TOWN")){
                            insertTownIntoDB(line, dc);
                        } else if (importType.equals("Majors") && headerItems[0].equals("MAJOR")){
                            insertMajorIntoDB(line, dc);
                        } else {
                            writer.println("<p>Imported table structure does not match table schema for " + importType + ".</p>");
                        }
                        counter++;
                    }
                    writer.println("<p>" + counter + " entries added to " + importType + " table.</p>");
                } else if (headerItems.length == 21){
                    if (importType.equals("Mentees")){
                        transferMenteesToDB(br);
                    } else if (importType.equals("Mentors")){
                        transferMentorsToDB(br);
                    } else {
                        writer.println("<p>Imported table structure does not match table schema for " + importType + ".</p>");
                    }
                } else {
                    writer.println("<p>Imported table structure does not match table schema for " + importType + ".</p>");
                }
                br.close();
            } catch (ServletException | SQLException | NamingException e) {
                writer.println("<p>Exception thrown: " + e + "</p>");
            }
        }
        writer.close();
    }

    private void insertMajorIntoDB(String line, DatabaseConnection dc) throws SQLException{
        dc.executeUpdateCommand("INSERT into `major`" +
                "(`name`)" +
                " VALUES " +
                " ('" + line + "');");
    }

    private void insertTownIntoDB(String line, DatabaseConnection dc) throws SQLException {
        dc.executeUpdateCommand("INSERT into `town`" +
                "(`name`)" +
                " VALUES " +
                " ('" + line + "');");
    }

    private void transferMenteesToDB(BufferedReader br) throws IOException{
        CrudService crud = new CrudService();
        String line;
        while ((line = br.readLine()) != null) {
            String[] traits = line.split(",");
            Mentee mentee = new Mentee();
            mentee.setEmail(traits[0]);
            mentee.setFirstName(traits[1]);
            mentee.setLastName(traits[2]);
            mentee.setStudentId(traits[3]);
            mentee.setMajor(traits[4]);
            mentee.setYear(traits[5]);
            mentee.setHobbies(createHobbyList(Arrays.copyOfRange(traits, 6, 10)));
            mentee.setCtHometown(traits[11]);
            mentee.setOtherHometown(traits[12]);
            mentee.setParentEducation(traits[13].equals("Y")?true:false);
            mentee.setLanguage(createLanguageList(traits[14], traits[15], traits[16]));
            mentee.setRace(createLanguageList(traits[17], traits[18], traits[19]));
            mentee.setGender(traits[20]);
            mentee.setLookingForward("Long form answer about what I'm looking forward to.");
            mentee.setWhyMentor("Long form answer about why I am seeking a mentor.");
            crud.createUser(mentee);
            crud.createMentee(mentee);
        }
    }

    private void transferMentorsToDB(BufferedReader br) throws IOException{
        CrudService crud = new CrudService();
        String line;
        while ((line = br.readLine()) != null) {
            String[] traits = line.split(",");
            Mentor mentor = new Mentor();
            mentor.setEmail(traits[0]);
            mentor.setFirstName(traits[1]);
            mentor.setLastName(traits[2]);
            mentor.setStudentId(traits[3]);
            mentor.setMajor(traits[4]);
            mentor.setYear(traits[5]);
            mentor.setHobbies(createHobbyList(Arrays.copyOfRange(traits, 6, 10)));
            mentor.setCtHometown(traits[11]);
            mentor.setOtherHometown(traits[12]);
            mentor.setParentEducation(traits[13].equals("Y")?true:false);
            mentor.setLanguage(createLanguageList(traits[14], traits[15], traits[16]));
            mentor.setRace(createLanguageList(traits[17], traits[18], traits[19]));
            mentor.setGender(traits[20]);
            mentor.setMentorReq("Long form answer about what mentors require.");
            mentor.setAttitudeForDifference("Long form answer about what mentors need to handle different interests from mentees.");
            mentor.setChallenge("Long form answer about a challenge I've faced.");
            mentor.setForSuccessfulFirstYear("Long form answer about what mentees need for a successful first year.");
            crud.createUser(mentor);
            crud.createMentor(mentor);
        }
    }

    private String createHobbyList(String[] hobbies){
        String hobbyList = "";
        for (int i = 0; i < hobbies.length - 1; i++){
            hobbyList += hobbies[i] + ",";
        }
        hobbyList += hobbies[hobbies.length - 1];
        return hobbyList;
    }

    private String createLanguageList(String lang1, String lang2, String langother){
        if (lang1.equals("")){
            return "English Only";
        }
        String l = "";
        if (lang1.equals("Other")){
            l += langother;
        } else {
            l += lang1;
        }
        if (lang2.equals("")){
            return l;
        } else if (lang2.equals("Other")){
            l += "," + langother;
        } else {
            l += "," + lang2;
        }
        return l;
    }


    private String insertMentorIntoDB(String[] data) throws SQLException{
        DataSource ds = null;
        Connection con = null;
        PreparedStatement pr = null;
        InitialContext ic;
        int success = 0;
        String message = "";
        try {
            ic = new InitialContext();
            ds = (DataSource) ic.lookup("java:/SCDB");
            con = ds.getConnection();
            pr = con.prepareStatement(createInsertMentorSQL(data));
            pr.executeUpdate();
            pr.close();
            message = "Mentor added.";
        } catch (SQLException e) {
            e.printStackTrace();
            message = "SQL error occurred.";
        } catch (NamingException e) {
            e.printStackTrace();
            message = "Naming error occurred.";
        } finally {
            if (con != null){
                con.close();
            }
        }
        return message;
    }

    private String insertMenteeIntoDB(String[] data) throws SQLException{
        DataSource ds = null;
        Connection con = null;
        PreparedStatement pr = null;
        InitialContext ic;
        int success = 0;
        String message = "";
        try {
            ic = new InitialContext();
            ds = (DataSource) ic.lookup("java:/SCDB");
            con = ds.getConnection();
            pr = con.prepareStatement(createInsertMenteeSQL(data));
            pr.executeUpdate();
            pr.close();
            message = "Mentor added.";
        } catch (SQLException e) {
            e.printStackTrace();
            message = "SQL error occurred.";
        } catch (NamingException e) {
            e.printStackTrace();
            message = "Naming error occurred.";
        } finally {
            if (con != null){
                con.close();
            }
        }
        return message;
    }

    private String createInsertMentorSQL(String[] csvParts){
        return "INSERT INTO `mentor` " +
                "(`pwd`, `first_name`, `last_name`, `email`, `student_id`, `major`, `grade`, `junior_or_senior`, `hobby`, `hometown`, `language`, `race`, `gender`, `mentor_requirement`, `attitude_for_diff`, `challenges`, `for_successful_1st_year`, `is_senior_mentor`)" +
                " VALUES " +
                "('password', '" +  csvParts[0] + "', '" + csvParts[1]+ "', '" + csvParts[2] + "', '" + csvParts[3] + "', '" + csvParts[4] + "', '" + csvParts[5] + "', '" + csvParts[6] + "', '" + csvParts[7] +  "', '" + csvParts[8] + "', '" + csvParts[9] + "', '" + csvParts[10] +"', '" + csvParts[11] +"', '" + csvParts[12] + "', '" + csvParts[13] +"', '" + csvParts[14] + "', '" + csvParts[15] +"', '0')";
    }

    private String createInsertMenteeSQL(String[] csvParts){
        return "INSERT INTO `mentee` " +
                "(`pwd`, `first_name`, `last_name`, `email`, `studentID`, `major`, `grade`, `freshman_or_sophomore`, `hobby`, `hometown`, `language`, `race`, `gender`)" +
                " VALUES " +
                "('password', '" +  csvParts[0] + "', '" + csvParts[1]+ "', '" + csvParts[2] + "', '" + csvParts[3] + "', '" + csvParts[4] + "', '" + csvParts[5] + "', '" + csvParts[6] + "', '" + csvParts[7] +  "', '" + csvParts[8] + "', '" + csvParts[9] + "', '" + csvParts[10] +"', '" + csvParts[11] +"')";
    }



}
