package com.ccsu.cs530.successcentral.service;

import com.ccsu.cs530.successcentral.model.Match;
import com.ccsu.cs530.successcentral.model.Mentee;
import com.ccsu.cs530.successcentral.model.Mentor;
import com.ccsu.cs530.successcentral.util.DatabaseConnection;

import java.sql.Connection;
import java.util.*;

public class MatchingService {
    private Connection con = DatabaseConnection.getConnection();
    private CrudService crud = new CrudService();


    /**
     * Return a numerical measure of how good a match the mentor
     * is with the mentee. The higher the better.
     * @param mentor the mentor to be considered.
     * @param mentee the mentee to be considered.
     * @return the match number.
     */
    public int evaluateMatch(Mentor mentor, Mentee mentee) {
        int val = 0;

        if (mentor.getGender() != null && mentee.getGender() != null) {
            if (mentor.getGender().equals(mentee.getGender())) {
                val++;
            }
        }
        if (mentor.getMajor() != null && mentee.getMajor() != null) {
            if (mentor.getMajor().equals(mentee.getMajor())) {
                val++;
            }
        }
        if (mentor.getLanguage() != null && mentee.getLanguage() != null) {
            String[] menteeArray = mentee.getLanguage().split(",");
            String[] mentorArray = mentor.getLanguage().split(",");
            for (int i=0; i<menteeArray.length; i++){
                for (int k=0; k<mentorArray.length; k++){
                    if (menteeArray[i].equals(mentorArray[k])){
                        val++;
                    }
                }
            }
        }
        if (mentor.getCtHometown() != null && mentee.getCtHometown() != null) {
            if (mentor.getCtHometown().equals(mentee.getCtHometown())) {
                val++;
            }
        }

        if (mentor.getHobbies() != null && mentee.getHobbies() != null) {
            String[] menteeArray = mentee.getHobbies().split(",");
            String[] mentorArray = mentor.getHobbies().split(",");
            for (int i=0; i<menteeArray.length; i++){
                for (int k=0; k<mentorArray.length; k++){
                    if (menteeArray[i].equals(mentorArray[k])){
                        val++;
                    }
                }
            }
        }


        if (mentor.getRace() != null && mentee.getRace() != null) {
            String[] menteeArray = mentee.getRace().split(",");
            String[] mentorArray = mentor.getRace().split(",");
            for (int i=0; i<menteeArray.length; i++){
                for (int k=0; k<mentorArray.length; k++){
                    if (menteeArray[i].equals(mentorArray[k])){
                        val++;
                    }
                }
            }
        }
        if (mentor.getParentEducation() != null && mentee.getParentEducation() != null) {
            if (mentor.getParentEducation().equals(mentee.getParentEducation())) {
                val++;
            }
        }
        return val;
    }

    /**
     * Return list of top n mentor matches for a mentee
     * @param mentee
     * @param n the "n" in return the top "n" matches.
     * @return a list of Match objects.
     */
    public List<Match> getTopNMatches(Mentee mentee, int n) {
        List<Match> matches = new ArrayList<>();

        List<Mentor> mentors = crud.getApprovedMentors();
        for (Mentor mentor : mentors) {
            Match match = new Match();
            match.setMentee(mentee);
            match.setMentor(mentor);
            match.setCompatibility(evaluateMatch(mentor, mentee));
            matches.add(match);
        }

        Collections.sort(matches);

        List<Match> topN = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if (i < matches.size()) {
                    topN.add(i, matches.get(matches.size() - i - 1)); // need to go in reverse order here.
                }
            }
        return topN;
    }

    public List<Match> getMyMatches(Mentor mentor) {
        List<Match> matches = new ArrayList<>();

        List<Mentee> myMentees = crud.getMyMentees(mentor.getEmail());
        for (Mentee mentee : myMentees) {
            Match match = new Match();
            match.setMentee(mentee);
            match.setMentor(mentor);
            match.setCompatibility(evaluateMatch(mentor, mentee));
            matches.add(match);
        }

        Collections.sort(matches);
        Collections.reverse(matches);

        return matches;
    }

    public List<Match> getAllMatches(Mentee mentee) {
        List<Match> matches = new ArrayList<>();

        List<Mentor> mentors = crud.getApprovedMentors();
        for (Mentor mentor : mentors) {
            Match match = new Match();
            match.setMentee(mentee);
            match.setMentor(mentor);
            match.setCompatibility(evaluateMatch(mentor, mentee));
            matches.add(match);
        }

        Collections.sort(matches);
        Collections.reverse(matches);

        return matches;
    }
}
