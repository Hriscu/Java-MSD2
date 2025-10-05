package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/ControllerServlet")
public class ControllerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,IOException {

        // log info
        logRequest(request);

        String choice = request.getParameter("choice");
        String format = request.getParameter("format"); // text pentru client desktop

        if ("text".equalsIgnoreCase(format)) {
            // răspuns pentru desktop client
            response.setContentType("text/plain;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("Selected choice: " + choice);
            }
        } else {
            // forward pentru browser
            if ("1".equals(choice)) {
                request.getRequestDispatcher("page1.html").forward(request, response);
            } else if ("2".equals(choice)) {
                request.getRequestDispatcher("page2.html").forward(request, response);
            } else {
                response.setContentType("text/html;charset=UTF-8");
                try (PrintWriter out = response.getWriter()) {
                    out.println("<h3>Invalid choice. Please select 1 or 2.</h3>");
                }
            }
        }
    }

    private void logRequest(HttpServletRequest request) {
        String method = request.getMethod();
        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        String language = request.getHeader("Accept-Language");
        String params = request.getQueryString();

        String logLine = "HTTP Method: " + method + ", IP: " + ip +
                ", User-Agent: " + userAgent + ", Language: " + language +
                ", Params: " + params;

        System.out.println(logLine);
        getServletContext().log(logLine);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
