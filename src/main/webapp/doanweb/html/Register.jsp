<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tiệm gà sao hỏa</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/doanweb/styles/style.css">
    <link rel="icon" href="<%= request.getContextPath() %>/doanweb/images/Page1/LoadWeb.png" type="image/png">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">


    <script src="<%= request.getContextPath() %>/doanweb/js/index.js"></script>
    <!-- bootstarp stackpath cdn -->

    <!-- Bootstrap icons cdn-->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

    <style>
        h3 {
            color: #343a40;
            font-size: 2.5rem;
            font-weight: 700;
        }
        .login-btn {
            width: 30%;
            background-color: #343a40;
            color: aliceblue;
            font-size: 1.2rem;
            font-weight: 600;
            /* center */
            display: block;
            margin: 0 auto;
        }

        #loginForm {
            width: 80%;
            margin: 0 auto;
        }
        #signUpForm input, #loginForm input {
            border: 1px solid #343a40;
            padding: 10px;
        }
        #signUpForm input:focus, #loginForm input:focus {
            background-color: aliceblue;
        }
        #signUpForm button, #loginForm button {
            background-color: #343a40;
            color: aliceblue;
            border: none;
            font-weight: 600;
        }
        #signUpForm button:hover, #loginForm button:hover {
            background-color: #BC1F23;
            color: aliceblue;
            border: none;
        }
        #showSignUp {
            color: #BC1F23;
            font-weight: 600;
        }
        #showSignUp:hover {
            color: #343a40;
            text-decoration: none;
        }
    </style>
</head>
<body>
<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-light bg-dark py-4 fixed-top">
    <div class="container-fluid mr-5">

        <button class="navbar-toggler" onclick="toggleMenu()">
            <span><i id="nav-bar-icon" class="bi bi-list"></i></span>
        </button>

        <div class="search-bar">
            <input type="text" class="search-input" id="searchInput" placeholder="Tìm kiếm...">
            <button class="search-button" id="searchButton">
                <i class="bi bi-search"></i>
            </button>
        </div>

        <div id="searchResults" class="search-results"></div>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ml-auto">

                <li class="nav-item">
                    <a class="nav-link active" href="#">Trang Chủ</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<%= request.getContextPath() %>/shop">Cửa Hàng</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="<%= request.getContextPath() %>/about">Thông tin</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<%= request.getContextPath() %>/contact">Liên hệ</a>

                </li>
                <li class="nav-item" id="admin-btn" style="display: none;">
                    <a class="nav-link" href="<%= request.getContextPath() %>/doanweb/Admin.jsp">Quản trị</a>
                </li>
                <div class="nav-item" id="nav-icons">
                    <!-- <i class="bi bi-search"></i> -->
                    <a href="<%= request.getContextPath() %>/doanweb/html/Menu/Login.html"><i class="bi bi-person-fill"
                                                                                              id="user-icon"></i></a>
                    <!-- User Menu -->

                    <div class="user-menu" id="user-menu">
                        <span id="greeting"></span>
                        <a href="<%= request.getContextPath() %>/doanweb/html/Menu/Profile.html">Tài khoản của tôi</a>
                        <a href="javascript:void(0);" onclick="logout()">Đăng xuất</a>
                    </div>

                </div>

            </ul>
        </div>


        <div id="mySideBar" class="sidebar">
            <div class="sidebar-header">
                <img src="<%= request.getContextPath() %>/doanweb/images/Page1/LogoWeb.png" alt="Logo" class="logo">
                <span id="closeBtn" class="close-btn" onclick="toggleMenu()">&times;</span>
            </div>
            <div class="sidebar-content">
                <div class="menu-section">
                    <h4><a href="<%= request.getContextPath() %>/doanweb/index.jsp">TRANG CHỦ</a></h4>
                </div>
                <div class="menu-section">
                    <h4><a href="<%= request.getContextPath() %>/doanweb/html/Cart.jsp">GIỎ HÀNG</a></h4>
                </div>
                <div class="menu-section">
                    <h4><a href="<%= request.getContextPath() %>/doanweb/html/AboutUs.jsp">VỀ MFS</a></h4>
                </div>
                <div class="menu-section">
                    <h4><a href="<%= request.getContextPath() %>/doanweb/html/sidebar/chinhsach.jsp">CHÍNH SÁCH</a></h4>
                </div>
                <div class="menu-section">
                    <h4><a href="javascript:void(0);" class="toggle-menu">THỰC ĐƠN</a></h4>
                    <ul class="submenu">
                        <li><a href="<%= request.getContextPath() %>/doanweb#">Combo gà phải thử</a></li>
                    </ul>
                </div>
                <div class="menu-section">
                    <h4><a href="<%= request.getContextPath() %>/doanweb/html/sidebar/DichVu.html">DỊCH VỤ</a></h4>
                </div>
                <div class="menu-section">
                    <h4><a href="<%= request.getContextPath() %>/doanweb/html/Menu/ContactUs.html">LIÊN HỆ</a></h4>
                </div>
                <div class="menu-section">
                    <h4><a href="<%= request.getContextPath() %>/doanweb/html/sidebar/job.html">TUYỂN DỤNG</a></h4>
                </div>

                <div class="menu-section user" id="user-sidebar">
                    <h4><i class="bi bi-person"></i><a href="<%= request.getContextPath() %>Login.html">ĐĂNG

                </div>
                <div class="menu-section user-logged-in" id="user-logged-in" style="display: none;">
                    <span id="greeting-menu"></span>
                    <h4><i class="bi bi-person"></i><a href="javascript:void(0);" onclick="logout()">ĐĂNG XUẤT</a></h4>
                </div>
            </div>
        </div>
</nav>

<!-- login section -->
<section id="featured" class="mt-3 pt-3">
    <div class="container text-center mt-5 pt-5">
        <h3 id="form-heading" class="mt-5 pt-5">Đăng Ký </h3>
        <hr class="border border-danger border-2 opacity-75 mx-auto">
    </div>
</section>

<div class="container mt-5" id="container-register">
    <div class="row">
        <div class="col-lg-6 col-md-6 col-12 mt-0">
        </div>
        <div class="col-lg-6 col-md-6 col-12 mt-5">
            <form id="signUpForm" action="" onsubmit="SignUp()">
                <div class="form-group">
                </div>
                <div class="form-group">
                    <label for="email">Email</label>
                </div>
                <div class="form-group">
                    <label for="password">Mật khẩu</label>
                </div>
                <div class="form-group">
                    <label for="confirmPassword">Nhập lại mật khẩu</label>
                </div>
                <div class="form-group">
                </div>

                <div class="form-group">
                </div>

                <button type="submit" class="btn btn-dark" onclick="signUp()">Đăng ký</button>
            </form>
        </div>
    </div>
</div>

<script>
    function registerUser() {
        const name = document.getElementById('name').value;
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirm-password').value;
        const address = document.getElementById('address').value;

        // Kiểm tra các điều kiện
        if (!phone || !email || !password || !confirmPassword || !address || !name) {
            alert('Vui lòng nhập đầy đủ thông tin!');
            return;
        }

        if (password !== confirmPassword) {
            alert('Mật khẩu xác nhận không khớp!');
            return;
        }

        // Lấy dữ liệu cũ từ localStorage hoặc tạo mới
        const users = JSON.parse(localStorage.getItem('users')) || [];
        users.push({name, phone, email, password, address}); // Thêm tài khoản mới
        localStorage.setItem('users', JSON.stringify(users)); // Lưu vào localStorage
        alert('Đăng ký thành công!');
    }
</script>

<footer class="mt-5 p-5 bg-dark">
    <div class="row conatiner mx-auto pt-5">
        <div class="footer-one col-lg-3 col-md-6 col-12">

            <img id="logo-img-footer" src="<%= request.getContextPath() %>/doanweb/images/Page1/LogoWeb.png" alt="logo">

        </div>

        <div class="footer-one col-lg-3 col-md-6 col-12 mb-3">
            <h5 class="pb-2">Liên kết nhanh</h5>
            <ul class="text-uppercase list-unstyled">
                <li><a href="index.html">trang chủ</a></li>
                <li><a href="/html/Menu/shop.html">Cửa hàng</a></li>
                <li><a href="/html/Menu/AboutUs.html">thông tin</a></li>
                <li><a href="/html/Menu/ContactUs.html">liên hệ</a></li>
                <li><a href="/html/Menu/Cart.html">Giỏ hàng</a></li>
            </ul>
        </div>
        <div class="footer-one col-lg-3 col-md-6 col-12 mb-3">
            <h5 class="pb-2">Liên hệ với chúng tôi</h5>
            <div>
                <h6 class="text-uppercase">Địa chỉ</h6>
                <p>Khu phố 6, Phường Linh Trung, TP. Thủ Đức, TP. Hồ Chí Minh</p>
            </div>
            <div>
                <h6 class="text-uppercase">điện thoại</h6>
                <p>0849294483</p>
            </div>
            <div>
                <h6 class="text-uppercase">Email</h6>
                <p>MarsStore@gmail.com</p>
            </div>
        </div>
        <div class="Photos col-lg-3 col-md-6 col-12">
            <h5 class="pb-2">Các đơn vị tài trợ</h5>
            <div class="row">
            </div>
        </div>
        <div class="copyright mt-5">
            <div class="row container mx-auto">
                <!-- <div class="col-lg-3 col-md-6 col-12 mb-4">
                  <img src="img/payment.png" alt="payment..logo">
                </div> -->

                <div class="col-lg-6 col-md-8 col-12 mb-2 mx-auto">
                    <p>MARSSTORE WEBSITE &copy; DESIGN 2024</p>
                </div>

                <div class="col-lg-3 col-md-6 col-12">
                    <a href="https://www.facebook.com/"><i class="bi bi-facebook"></i></a>
                    <a href="https://x.com/home?lang=vi"><i class="fa-brands fa-x-twitter"></i></a>
                    <a href="https://www.linkedin.com/feed/"><i class="bi bi-linkedin"></i></a>
                    <a href="https://www.instagram.com/"><i class="bi bi-instagram"></i></a>
                </div>
            </div>
        </div>
    </div>

</footer>
<!-- bootstarp cdn -->
</body>
</html>

<%--<div class="category-filter">--%>
<%--  <form action="<%= request.getContextPath() %>/shop" method="get">--%>
<%--    <label for="category">Chọn danh mục:</label>--%>
<%--    <select name="category" id="category">--%>
<%--      <option value="">Tất cả</option>--%>
<%--      <c:forEach var="category" items="${categoriesList}">--%>
<%--        <option value="${category.id}">${category.name}</option>--%>
<%--      </c:forEach>--%>
<%--    </select>--%>
<%--    <button type="submit">Lọc</button>--%>
<%--  </form>--%>
<%--</div>--%>