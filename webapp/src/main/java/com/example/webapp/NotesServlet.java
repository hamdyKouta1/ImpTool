package com.example.webapp;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.*;
import java.util.*;

@WebServlet("/NotesServlet")
public class NotesServlet extends HttpServlet {
    static String path= System.getProperty("user.home");
    private static String DATA_FILE = path+"/jar-lib-imp-tool/db-note.json";
   // private static String DATA_FILE = path+"\\jar-lib-imp-tool\\db-note.json";
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            File file = new File(DATA_FILE);
            JSONArray notes = readNotes(file);

            String noteId = request.getParameter("id");
            if (noteId != null) {
                JSONObject note = findNoteById(notes, noteId);
                response.getWriter().write(note.toString());
            } else {
                response.getWriter().write(notes.toString());
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving notes");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response, false);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response, true);
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            File file = new File(DATA_FILE);
            JSONArray notes = readNotes(file);
            String noteId = request.getParameter("id");

            JSONArray updatedNotes = new JSONArray();
            for (int i = 0; i < notes.length(); i++) {
                JSONObject note = notes.getJSONObject(i);
                if (!note.getString("id").equals(noteId)) {
                    updatedNotes.put(note);
                }
            }

            writeNotes(file, updatedNotes);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error deleting note");
        }
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response, boolean isUpdate)
            throws IOException {
        try {
            File file = new File(DATA_FILE);
            JSONArray notes = readNotes(file);
            
            StringBuilder sb = new StringBuilder();
            String line;
            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }

            JSONObject newNote = new JSONObject(sb.toString());
            if (isUpdate) {
                updateNote(notes, newNote);
            } else {
                if (!newNote.has("color")) {
                    newNote.put("color", getRandomColorClass());
                }
                notes.put(newNote);
            }

            writeNotes(file, notes);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                "Error processing request");
        }
    }

    private JSONArray readNotes(File file) throws IOException, JSONException {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            return new JSONArray();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            return content.length() > 0 ? new JSONArray(content.toString()) : new JSONArray();
        }
    }

    private void writeNotes(File file, JSONArray notes) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(notes.toString(4));
        }
    }

    private JSONObject findNoteById(JSONArray notes, String id) throws JSONException {
        for (int i = 0; i < notes.length(); i++) {
            JSONObject note = notes.getJSONObject(i);
            if (note.getString("id").equals(id)) {
                return note;
            }
        }
        return null;
    }

    private void updateNote(JSONArray notes, JSONObject updatedNote) throws JSONException {
        for (int i = 0; i < notes.length(); i++) {
            JSONObject note = notes.getJSONObject(i);
            if (note.getString("id").equals(updatedNote.getString("id"))) {
                notes.put(i, updatedNote);
                break;
            }
        }
    }

    private String getRandomColorClass() {
        String[] colors = {"bg-cyan", "bg-light-green", "bg-light-red", 
                          "bg-light-gray", "bg-light-orange"};
        return colors[new Random().nextInt(colors.length)];
    }
}