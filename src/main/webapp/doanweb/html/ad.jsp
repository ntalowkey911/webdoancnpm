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
    String deleteProductId = request.getParameter("deleteProductId");
    String deleteCategoryId = request.getParameter("deleteCategoryId");

    // Xóa sản phẩm
    if (deleteProductId != null) {
        daoInstance.deleteProduct(Integer.parseInt(deleteProductId));
    }

    // Xóa danh mục
    if (deleteCategoryId != null) {
        daoInstance.deleteCategory(Integer.parseInt(deleteCategoryId));
    }
%>

<form action="AddProduct" method="post">
    <h3>Thêm/Sửa sản phẩm</h3>
    <input type="hidden" name="id" placeholder="ID (chỉ nhập khi sửa)">
    <label>
        <input type="text" name="name" placeholder="Tên sản phẩm" required>
    </label>
    <label>
        <input type="text" name="description" placeholder="Mô tả sản phẩm" required>
    </label>
    <label>
        <input type="number" name="price" placeholder="Giá sản phẩm" required>
    </label>
    <label>
        <input type="number" name="stock" placeholder="Số lượng tồn" required>
    </label>
    <label>
        <input type="text" name="image" placeholder="Link ảnh" required>
    </label>
    <label>
        <select name="category_id" required>
            <option value="">Chọn danh mục</option>
            <% for (Categories category : categoryList) { %>
            <option value="<%= category.getId() %>"><%= category.getName() %></option>
            <% } %>
        </select>
    </label>
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
            <form action="UpdateProduct" method="post" style="display:inline;">
                <input type="hidden" name="id" value="<%= product.getId() %>">
                <input type="text" name="name" value="<%= product.getName() %>" required>
                <input type="text" name="description" value="<%= product.getDescription() %>" required>
                <input type="number" name="price" value="<%= product.getPrice() %>" required>
                <input type="number" name="stock" value="<%= product.getStock() %>" required>
                <input type="text" name="image" value="<%= product.getImage() %>" required>
                <select name="category_id" required>
                    <% for (Categories category : categoryList) { %>
                    <option value="<%= category.getId() %>" <%= (category.getId() == product.getCategoryId()) ? "selected" : "" %>>
                        <%= category.getName() %>
                    </option>
                    <% } %>
                </select><br>
                <button type="submit">Cập nhật</button>
            </form>

            <a href="DeleteProduct?id=<%= product.getId() %>" onclick="return confirm('Bạn có chắc chắn muốn xóa?')">Xóa</a>
        </td>
    </tr>
    <% } %>
</table>

<!-- Quản lý danh mục -->
<h2>Quản lý danh mục</h2>
<form action="AddCategory" method="post">
    <h3>Thêm danh mục mới</h3>
    <input type="text" name="categoryName" placeholder="Tên danh mục" required>
    <button type="submit">Thêm danh mục</button>
</form>

<table>
    <tr>
        <th>ID</th>
        <th>Tên danh mục</th>
        <th>Hành động</th>
    </tr>
    <% for (Categories category : categoryList) { %>
    <tr>
        <td><%= category.getId() %></td>
        <td><%= category.getName() %></td>
        <td>
            <a href="DeleteCategory?id=<%= category.getId() %>" onclick="return confirm('Bạn có chắc chắn muốn xóa danh mục này?')">Xóa</a>        </td>
    </tr>
    <% } %>
</table>
</body>
</html>
