<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dao.dao, entity.Products, entity.Categories" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Page</title>
    <style>
        table { border-collapse: collapse; width: 100%; margin-bottom: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: center; }
        th { background-color: #f2f2f2; }
        form { margin-bottom: 20px; }
        input, select { padding: 5px; margin: 5px 0; }
        button { padding: 5px 10px; background-color: #4CAF50; color: white; border: none; cursor: pointer; }
        button:hover { background-color: #45a049; }
    </style>
</head>
<body>
<h1>Admin Dashboard</h1>
<h2>Quản lý sản phẩm</h2>

<%
    dao daoInstance = new dao();
    List<Products> productList = daoInstance.getAllProducts();
    List<Categories> categoryList = daoInstance.getAllCategories();

    // Kiểm tra xem có hành động xóa không
    String deleteId = request.getParameter("deleteId");
    if (deleteId != null) {
        int result = daoInstance.deleteProduct(Integer.parseInt(deleteId));
        if (result > 0) {
            out.println("<p style='color:green;'>Xóa sản phẩm thành công!</p>");
        } else {
            out.println("<p style='color:red;'>Xóa sản phẩm thất bại!</p>");
        }
    }
%>

<!-- Form thêm sản phẩm -->
<form action="AddProductServlet" method="post">
    <h3>Thêm sản phẩm mới</h3>
    <input type="text" name="name" placeholder="Tên sản phẩm" required>
    <input type="text" name="description" placeholder="Mô tả sản phẩm" required>
    <input type="number" name="price" placeholder="Giá sản phẩm" required>
    <input type="number" name="stock" placeholder="Số lượng tồn" required>
    <input type="text" name="image" placeholder="Link ảnh" required>
    <select name="categoryId" required>
        <option value="">Chọn danh mục</option>
        <% for (Categories category : categoryList) { %>
        <option value="<%= category.getId() %>"><%= category.getName() %></option>
        <% } %>
    </select>
    <button type="submit">Thêm sản phẩm</button>
</form>

<!-- Hiển thị danh sách sản phẩm -->
<table>
    <tr>
        <th>ID</th>
        <th>Tên sản phẩm</th>
        <th>Mô tả</th>
        <th>Giá</th>
        <th>Số lượng</th>
        <th>Hình ảnh</th>
        <th>Danh mục</th>
        <th>Hành động</th>
    </tr>
    <% for (Products product : productList) { %>
    <tr>
        <td><%= product.getId() %></td>
        <td><%= product.getName() %></td>
        <td><%= product.getDescription() %></td>
        <td><%= product.getPrice() %></td>
        <td><%= product.getStock() %></td>
        <td><img src="<%= product.getImage() %>" alt="<%= product.getName() %>" style="width: 50px;"></td>
        <td><%= product.getCategoryId() %></td>
        <td>
            <a href="UpdateProduct.jsp?id=<%= product.getId() %>">Sửa</a> |
            <a href="admin.jsp?deleteId=<%= product.getId() %>" onclick="return confirm('Bạn có chắc chắn muốn xóa?')">Xóa</a>
        </td>
    </tr>
    <% } %>
</table>

<!-- Quản lý danh mục -->
<h2>Quản lý danh mục</h2>
<form action="AddCategoryServlet" method="post">
    <h3>Thêm danh mục mới</h3>
    <input type="text" name="categoryName" placeholder="Tên danh mục" required>
    <button type="submit">Thêm danh mục</button>
</form>
<table>
    <tr>
        <th>ID</th>
        <th>Tên danh mục</th>
    </tr>
    <% for (Categories category : categoryList) { %>
    <tr>
        <td><%= category.getId() %></td>
        <td><%= category.getName() %></td>
    </tr>
    <% } %>
</table>

<!-- Hiển thị đơn hàng -->
<h2>Danh sách đơn hàng</h2>
<!-- Đây là phần bạn cần thêm DAO và chức năng để lấy danh sách đơn hàng -->
</body>
</html>
