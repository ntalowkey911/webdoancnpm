<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h2>Quản lý sản phẩm</h2>
<form method="post" action="ProductServlet">
  <label for="name">Tên sản phẩm:</label>
  <input type="text" id="name" name="name" required>

  <label for="description">Mô tả:</label>
  <textarea id="description" name="description"></textarea>

  <label for="price">Giá:</label>
  <input type="number" id="price" name="price" required>

  <label for="category">Danh mục:</label>
  <select id="category" name="category">
    <option value="1">Thời trang</option>
    <option value="2">Điện tử</option>
    <option value="3">Gia dụng</option>
  </select>

  <label for="brand">Thương hiệu:</label>
  <input type="text" id="brand" name="brand">

  <label for="quantity">Số lượng tồn kho:</label>
  <input type="number" id="quantity" name="quantity">

  <button type="submit">Thêm sản phẩm</button>
</form>

<table border="1">
  <tr>
    <th>ID</th>
    <th>Tên</th>
    <th>Mô tả</th>
    <th>Giá</th>
    <th>Danh mục</th>
    <th>Thương hiệu</th>
    <th>Số lượng</th>
    <th>Hành động</th>
  </tr>
  <!-- Dữ liệu sản phẩm sẽ được hiển thị ở đây -->
</table>
