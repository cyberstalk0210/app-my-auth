package uz.pdp.login;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uz.pdp.DB;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController extends HttpServlet {

    private final DB db = new DB();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Connection connection = db.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE email = ? AND password = MD5(?)");
            preparedStatement.setString(1,req.getParameter("email"));
            preparedStatement.setString(2, req.getParameter("password"));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                resp.setContentType("text/html");
                PrintWriter writer = resp.getWriter();
                writer.write("<h1 style='color:red'>Username or password is wrong</h1>");
                writer.write("<br>");
                writer.write("<a href='/login'>Back</a>");
                writer.flush();
            }else {
                resp.setContentType("text/html");
                PrintWriter writer = resp.getWriter();
                writer.write("<h1 style='color:green'>"+resultSet.getString("name")+"</h1>");
                writer.write("<br>");
                writer.write("<a href='/'>Home</a>");
                writer.flush();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
