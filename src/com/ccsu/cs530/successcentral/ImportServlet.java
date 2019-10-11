package com.ccsu.cs530.successcentral;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

@WebServlet("/import")
@MultipartConfig
public class ImportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("email") != null) {
            req.getRequestDispatcher("import.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("email") != null) {
            String message = "File submitted.";
            Part filePart = req.getPart("csv");
//        String[] fileName = filePart.getSubmittedFileName().split("\\\\");
//        req.getSession().setAttribute("fileName", fileName[fileName.length - 1]);
            String[][] table = readFileToTable(filePart);
            req.getSession().setAttribute("fileContents", readFileToTable(filePart));
            resp.sendRedirect("import");
        } else {
            resp.sendRedirect("login");
        }
    }

    private String[][] readFileToTable(Part file) throws IOException {
        InputStream fileStream = file.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(fileStream));
        List<List<String>> table = new ArrayList<>();
        List<String> row;
        parseLine(br); //SKIP HEADER ROW
        while ((row = parseLine(br)) != null){
            for (int i = 0; i < row.size(); i++) {
                row.set(i, row.get(i).replaceAll("^\"|\"$", ""));
            }
            table.add(row);
        }
        String[][] tableArray = new String[table.size()][];
        for (int i = 0; i < table.size(); i++){
            List<String> r = table.get(i);
            String[] rowArray = new String[r.size()];
            for (int j = 0; j < r.size(); j++){
                rowArray[j] = r.get(j);
            }
            tableArray[i] = rowArray;
        }
        return tableArray;
    }

    //Courtesy of https://agiletribe.wordpress.com/2012/11/23/the-only-class-you-need-for-csv-files/
    private List<String> parseLine(Reader r) throws IOException {
        int ch = r.read();
        while (ch == '\r') {
            ch = r.read();
        }
        if (ch<0) {
            return null;
        }
        Vector<String> store = new Vector<>();
        StringBuffer curVal = new StringBuffer();
        boolean inquotes = false;
        boolean started = false;
        while (ch>=0) {
            if (inquotes) {
                started=true;
                if (ch == '\"') {
                    inquotes = false;
                }
                else {
                    curVal.append((char)ch);
                }
            }
            else {
                if (ch == '\"') {
                    inquotes = true;
                    if (started) {
                        // if this is the second quote in a value, add a quote
                        // this is for the double quote in the middle of a value
                        curVal.append('\"');
                    }
                }
                else if (ch == ',') {
                    store.add(curVal.toString());
                    curVal = new StringBuffer();
                    started = false;
                }
                else if (ch == '\r') {
                    //ignore LF characters
                }
                else if (ch == '\n') {
                    //end of a line, break out
                    break;
                }
                else {
                    curVal.append((char)ch);
                }
            }
            ch = r.read();
        }
        store.add(curVal.toString());
        return store;
    }

}
