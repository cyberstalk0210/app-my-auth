package uz.pdp.register;

import jakarta.servlet.http.HttpServletRequest;
import uz.pdp.ApiResultDTO;
import uz.pdp.DB;

import java.sql.*;

public class RegisterService {

    private final DB db = new DB();

    public ApiResultDTO register(HttpServletRequest req) {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String prepassword = req.getParameter("prepassword");

        if (!password.equals(prepassword))
            return new ApiResultDTO("Passwords do not match");

        try {
             Connection connection = db.createConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT EXISTS(SELECT 1 FROM users WHERE email = ?)");
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            if (resultSet.getBoolean(1))
                return new ApiResultDTO("User already exists");

            preparedStatement = connection.prepareStatement("INSERT INTO users(name, email, password) VALUES(?, ?, md5(?))");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            return new ApiResultDTO(e.getMessage());
        }

        return new ApiResultDTO();
    }
}
