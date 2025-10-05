package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Client1 {
    public static void main(String[] args) throws Exception {
        String choice = "1"; // or "2"
        String servletURL = "http://localhost:8080/Lab1_homework/ControllerServlet?"
                + "choice=" + choice
                + "&format=text";

        URL url = new URL(servletURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        System.out.println("Response from servlet:");
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
        in.close();
    }
}
