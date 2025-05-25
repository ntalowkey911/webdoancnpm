package controll;

import entity.Products;
import dao.dao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/search")
public class SearchController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 4.1.4 Hệ thống sẽ lấy từ khóa của người dùng nhập vào và tiến hành tìm kiếm
        String name = request.getParameter("name");
        List<Products> products = dao.searchProducts(name);

        // 4.1.15 Hệ thống thiết lập kiểu dữ liệu phản hồi là HTML
        response.setContentType("text/html");
        // 4.1.16 Hệ thống tạo đối tượng PrintWriter để ghi nội dung HTML trả về cho client
        PrintWriter out = response.getWriter();

        // 4.1.17 Hệ thống tạo ra đối tượng StringBuilder để xây dựng nội dung HTML kết quả tìm kiếm
        StringBuilder htmlResponse = new StringBuilder();
        // 4.3.18 Hệ thống kiểm tra nếu không có sản phẩm nào được tìm thấy
        if (products.isEmpty()) {
            // 4.3.19 Hệ thống gửi thông báo No products found
            htmlResponse.append("<p>No products found</p>");
        } else {
            // 4.1.18 Hệ thống duyệt qua danh sách sản phẩm và tạo nội dung HTML cho từng sản phẩm
            for (Products p : products) {
                htmlResponse.append("<div class='search-result'>");

                // 4.1.19 Hệ thống tạo đường dẫn đến trang chi tiết sản phẩm
                htmlResponse.append("<a href='detail?id=").append(p.getId()).append("'>");

                // 4.1.20 Hệ thống thêm hình ảnh sản phẩm (getImage() trả về đường dẫn ảnh)
                htmlResponse.append("<img class='img-fluid' src='")
                        .append(request.getContextPath()).append("/").append(p.getImage())
                        .append("' alt='").append(p.getName()).append("' />");

                // 4.1.21 Hệ thống thêm tên và giá sản phẩm trong cùng một dòng
                htmlResponse.append("<p>").append(p.getName()).append(" - <span class='product-price'>")
                        .append(p.getPrice()).append("K VND</span></p>");

                // 4.1.22 Hệ thống đóng lại thẻ <a>
                htmlResponse.append("</a>");
                // 4.1.23 Hệ thống đóng thẻ div
                htmlResponse.append("</div>");
            }
        }

        //4.1.24 Hệ thống gửi nội dung HTML về client
        out.print(htmlResponse.toString());
        out.flush();
    }
}
