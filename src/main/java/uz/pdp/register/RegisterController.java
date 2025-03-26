package uz.pdp.register;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uz.pdp.ApiResultDTO;

import java.io.IOException;
import java.io.PrintWriter;

public class RegisterController extends HttpServlet {

    private final RegisterService registerService = new RegisterService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("register.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApiResultDTO resultDTO = registerService.register(req);
        if (!resultDTO.isSuccess()) {
            resp.setContentType("text/html");
            PrintWriter writer = resp.getWriter();
            writer.write("<h1 style='color:red'>" + resultDTO.getMessage() + "</h1>");
            writer.write("<br>");
            writer.write("<a href='/register'>Back</a>");
            writer.flush();
        } else
            resp.sendRedirect("login");
    }
}
