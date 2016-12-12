package com.web.lab2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/")
public class MainServlet extends HttpServlet {
    static Receiver[] receivers = {
        new Receiver("JK89KS", new Point(49, 24), true),
        new Receiver("LO92LS", new Point(60, 31), true),
        new Receiver("KE90SL", new Point(23, 34), true),
        new Receiver("MK10KO", new Point(65, 41), true),
        new Receiver("MK90IS", new Point(29, 22), false),
        new Receiver("LO12KS", new Point(54, 21), true),
        new Receiver("EID39O", new Point(12, 34), false),
        new Receiver("MSKE12", new Point(17, 44), true),
        new Receiver("MSD123", new Point(56, 55), true),
        new Receiver("SDOE12", new Point(49, 12), false),
        new Receiver("SIEWO1", new Point(46, 65), true),
        new Receiver("SKEO12", new Point(12, 35), true),
        new Receiver("SDJJE1", new Point(42, 35), false),
        new Receiver("OWEO23", new Point(24, 45), true),
        new Receiver("MSDJ12", new Point(30, 12), true),
        new Receiver("SLK33O", new Point(50, 53), true),
        new Receiver("NSDN34", new Point(34, 34), true),
        new Receiver("SS2435", new Point(35, 46), false),
        new Receiver("88SDWD", new Point(37, 34), true),
        new Receiver("SS2435", new Point(32, 56), true),
    };


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();

        String jdbcUrl = "jdbc:postgresql://localhost/receivers";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(jdbcUrl, "postgres", "postgres");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Receiver r: receivers) {
            try {
                r.askTemperature();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            Statement statement = conn.createStatement();
            String sql = "";
            for (Receiver r: receivers) {
                sql += String.format("('%s', '(%f,%f)', %d, '%s'),",
                        r.getSerial(),
                        r.getCoordinates().getX(),
                        r.getCoordinates().getY(),
                        r.getTemperature(),
                        r.getReceivedAt());
            }
            statement.executeUpdate("INSERT INTO receivers VALUES " + sql.substring(0, sql.length() - 1));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        req.setAttribute("results", gson.toJson(receivers));
        req.getRequestDispatcher("receivers.jsp").forward(req, resp);
    }
}
