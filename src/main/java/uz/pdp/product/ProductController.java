package uz.pdp.product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

@WebServlet("/product")
public class ProductController extends HttpServlet {

    private final ProductService productService = new ProductService();

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        //page: 1, size: 10
        //page: 2, size: 10
        //page: 3, size: 20


        int page = Integer.parseInt(Objects.requireNonNullElse(request.getParameter("page"), "1"));
        int size = Integer.parseInt(Objects.requireNonNullElse(request.getParameter("size"), "10"));

        List<Product> products = productService.list(page, size);
        PrintWriter writer = response.getWriter();
        StringBuilder sb = new StringBuilder(
                """
                        <!DOCTYPE html>
                        <html lang="en">
                        <head>
                            <meta charset="UTF-8">
                            <meta name="viewport" content="width=device-width, initial-scale=1.0">
                            <title>Product Table</title>
                            <style>
                                table {
                                    width: 100%;
                                    border-collapse: collapse;
                                }
                                th, td {
                                    border: 1px solid black;
                                    padding: 8px;
                                    text-align: left;
                                }
                                th {
                                    background-color: #f2f2f2;
                                }
                            </style>
                        </head>
                        <body>
                            <table>
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Name</th>
                                        <th>Price</th>
                                        <th>Quantity</th>
                                        <th>Status</th>
                                    </tr>
                                </thead>
                                <tbody>
                        """
        );


        products.forEach(product -> {
            sb.append("<tr>");
            sb.append("<td>" + product.getId() + "</td>");
            sb.append("<td>" + product.getName() + "</td>");
            sb.append("<td>" + product.getPrice() + "</td>");
            sb.append("<td>" + product.getQuantity() + "</td>");
            sb.append("</tr>");
        });

        sb.append("""
                                </tbody>
                            </table>
                        </body>
                        </html>
                """);
        writer.write(sb.toString());
        response.setContentType("text/html");
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Product product = Product.builder()
                .name(req.getParameter("name"))
                .price(Double.valueOf(req.getParameter("price")))
                .quantity(Integer.valueOf(req.getParameter("quantity")))
                .status(Boolean.parseBoolean(req.getParameter("status")))
                .build();

        Product added = productService.add(product);

        resp.getWriter().write(added.toString());
    }
}
