<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Xác Thực OTP</title>
  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    /* Custom CSS */
    body {
      background-color: #f8f9fa;
    }
    .container {
      max-width: 500px;
      margin-top: 100px;
      padding: 20px;
      background: #ffffff;
      border-radius: 8px;
      box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    }
    h3 {
      text-align: center;
      color: #343a40;
      margin-bottom: 20px;
    }
    .alert {
      margin-bottom: 20px;
      border-radius: 4px;
    }
    label {
      font-weight: bold;
    }
    input[type="text"] {
      margin-bottom: 15px;
    }
    button {
      width: 100%;
      padding: 10px;
      background-color: #0d6efd;
      color: white;
      border: none;
      border-radius: 4px;
      font-size: 16px;
      cursor: pointer;
    }
    button:hover {
      background-color: #0b5ed7;
    }
  </style>
</head>
<body>
<div class="container">
  <h3>Nhập mã OTP đã được gửi qua email/SMS</h3>

  <!-- Hiển thị thông báo lỗi nếu có -->
  <c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
  </c:if>

  <!-- Form xác thực OTP -->
  <form action="<%= request.getContextPath() %>/verifyOTP" method="post">
    <div class="mb-3">
      <label for="otp" class="form-label">Mã OTP:</label>
      <input type="text" class="form-control" id="otp" name="otp" placeholder="Nhập mã OTP" required>
    </div>
    <button type="submit" class="btn btn-primary">Xác thực</button>
  </form>
</div>

<!-- Bootstrap JS (optional) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>