<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/checkout.css">
    <link rel="stylesheet" href="font/font-awesome-pro-v6-6.2.0/css/all.min.css" />
    <title>Document</title>
</head>
<style>:root {
    --white: #fff;
    --black: #000;
    --text-color: #333;
    --red: #B5292F;
}

.checkout-page.active {
    transform: translateX(0);
}

.checkout-header {
    background-color: var(--white);
    text-align: center;
    padding: 20px;
    border-bottom: 1px solid #eee;
    position: sticky;
    top: 0;
    width: 100%;
    z-index: 10;
}

.checkout-return {
    float: left;
}

.checkout-return button {
    float: left;
    border: none;
    width: 30px;
    background-color: transparent;
    outline: none;
    font-size: 20px;
    cursor: pointer;
    color: var(--text-color);
}

.checkout-title {
    font-weight: 700;
    font-size: 20px;
    color: var(--text-color);
}

.checkout-section {
    padding-top: 15px;
    display: flex;
    column-gap: 20px;
}

.checkout-row,
.checkout-col-right {
    background-color: var(--white);
    border-radius: 5px;
    border: 1px solid rgba(0, 0, 0, 0.15);
    box-shadow: 0px 4px 10px rgb(0 0 0 / 3%);
    margin-bottom: 20px;
}

.checkout-col-right {
    border: 1px solid var(--red);
    padding: 15px;
    height: 100%;
}

.checkout-col-right .checkout-content-label {
    font-size: 17px;
}

.content-group {
    padding: 15px 20px
}

.checkout-content-label {
    color: var(--black);
    font-weight: 600;
    margin-bottom: 15px;
}

.checkout-col-left {
    width: 70%;

}

.checkout-col-right {
    width: 30%;

}

.checkout-col-title {
    padding: 15px 20px 15px 33px;
    position: relative;
    font-size: 15px;
    line-height: 100%;
    text-transform: uppercase;
    color: #222222;
    font-weight: 700;
    border-bottom: 1px solid #E7E7E7;
}

.checkout-col-title:before {
    background: var(--red);
    width: 4px;
    height: 17px;
    left: 20px;
    top: 50%;
    -webkit-transform: translateY(-50%);
    transform: translateY(-50%);
    display: block;
    content: "";
    position: absolute;
}

.checkout-type-order {
    display: flex;
    column-gap: 15px;
}

.type-order-btn {
    width: 50%;
    height: 50px;
    cursor: pointer;
    font-size: 16px;
    background-color: #f6f6f6;
    border: none;
    border-radius: 5px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-weight: 600;
}

.type-order-btn:hover,
.type-order-btn.active {
    border: 1px solid var(--red);
    background-color: var(--white);
    color: var(--text-color);
}

.type-order-btn i {
    font-size: 22px;
    margin-right: 20px;
}

#tudenlay-group {
    display: none;
}

.content-group.chk-ship {
    display: flex;
    flex-direction: column;
}

.date-order {
    display: flex;
    justify-content: space-between;
    gap: 10px;
}

.pick-date {
    padding: 10px 15px;
    border-radius: 5px;
    background-color: #f6f6f6;
    color: #666;
    text-align: center;
    border: none;
    cursor: pointer;
    font-weight: 600;
    flex: 1;
    transition: background-color 0.3s, color 0.3s;
    text-decoration: none; /* Loại bỏ dấu gạch chân */
}


.pick-date.active {
    background-color: #B5292F; /* Màu đỏ */
    color: #fff; /* Màu trắng cho chữ */
}

.pick-date:hover {
    background-color: #d9534f; /* Màu đỏ nhạt hơn khi hover */
    color: #fff;
}



.delivery-time:not(:last-child) {
    margin-bottom: 15px;
}

.delivery-time {
    display: flex;
    align-items: center;
}

.choise-time {
    font-size: 14px;
    border: 1px solid var(--red);
    height: 32px;
    outline: none !important;
    margin-left: 30px;
    background-color: var(--white);
    border-radius: 5px;
    padding: 3px
}

.note-order {
    background: #FFF6ED;
    width: 100%;
    border: none;
    outline: none;
    min-height: 80px;
    border-radius: 6px;
    padding: 13px;
    font-size: 14px;
    line-height: 100%;
    color: #222222;
}

.bill-total {
    margin-bottom: 20px;
}

.food-total {
    display: flex;
    margin-bottom: 10px;
}

.food-total .count {
    width: 27px;
    flex-shrink: 0;
    margin-right: 40px;
    font-size: 14px;
    line-height: 140%;
    display: block;
    color: var(--text-color);
    font-weight: 600;
}

.food-total .info-food {
    -webkit-box-flex: 1;
    -ms-flex-positive: 1;
    flex-grow: 1;
}

.food-total .name-food {
    font-size: 14px;
    color: var(--text-color);
    line-height: 140%;
    font-weight: 600;
}

.bill-payment {
    padding-top: 25px;
    border-top: 1px solid #D3D3D3;
    margin-bottom: 25px;
}

.priceFlx {
    display: flex;
    justify-content: space-between;
    margin-bottom: 10px;
    font-size: 15px;
}

.policy-note {
    font-size: 12px;
    color: #666666;
    margin: 15px 0;
}

.policy-note a {
    color: var(--red);
}

.priceFlx .count {
    color: var(--red);
    font-size: 12px;
}

.price-detail span {
    font-weight: 500;
}

.total-checkout {
    display: flex;
    justify-content: space-between;
    padding-top: 20px;
    border-top: 1px solid var(--red)
}

.price-bill {
    display: flex;
    flex-direction: column;
    align-items: flex-end;
}

.price-final {
    color: var(--red);
    font-weight: 600;
}

.price-ori {
    color: #666666;
    font-size: 13px;
    display: inline-block;
    margin-top: 6px;
    text-decoration-line: line-through;
}

.discount {
    color: #629050;
}

.complete-checkout-btn {
    width: 100%;
    height: 40px;
    outline: none;
    border: none;
    cursor: pointer;
    color: var(--white);
    background-color: var(--red);
    text-align: center;
    border-radius: 5px;
    margin-top: 20px;
    font-size: 15px;
    font-weight: 600;
}

.payment-options label {
    display: flex;
    align-items: center;
    border: 1px solid #ced4da;
    height: calc(1.5em + 0.75rem + 2px);
    border-radius: 0.25rem;
    width: 100%;
    padding: 8px;
    background: #fff;
    margin-bottom: 8px; /* khoảng cách giữa các label */
    cursor: pointer;
}

.payment-options input[type="radio"] {
    margin-right: 10px;
    transform: scale(1.2); /* Tăng kích thước radio button */
    cursor: pointer;
}

.payment-options i {
    font-size: 1.2em;
    margin-right: 10px;
}

.payment-options span {
    font-size: 1em;
    color: #333;
}

.checkout-col-content {
    width: 100%;
}

.form-group {
    margin-bottom: 16px; /* Khoảng cách giữa các ô */
}

.form-control {
    width: 100%;
    padding: 12px;
    border: 1px solid #ced4da;
    border-radius: 8px; /* Bo tròn góc */
    font-size: 1em;
    box-sizing: border-box;
    outline: none;
    transition: border-color 0.3s;
}

.form-control:focus {
    border-color: #333; /* Đổi màu viền khi focus */
    box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
}</style>
<body>
<!-- 1. Form thanh toán -->
<form action="${pageContext.request.contextPath}/checkout1" method="post" class="checkout-form" onsubmit="return handlePayment(event)">
    <div class="checkout-page">
        <!-- 2. Header thanh toán -->
        <div class="checkout-header">
            <div class="checkout-return">
                <button onclick="history.back()"><i class="fa-regular fa-chevron-left"></i></button>
            </div>
            <h2 class="checkout-title">Thanh toán</h2>
        </div>

        <main class="checkout-section container">
            <!-- 3. Cột thông tin bên trái -->
            <div class="checkout-col-left">
                <!-- 4. Thông tin đơn hàng -->
                <div class="checkout-row">
                    <div class="checkout-col-title">Thông tin đơn hàng</div>
                    <div class="checkout-col-content">
                        <!-- 4.1 Hình thức giao nhận -->
                        <div class="content-group">
                            <p class="checkout-content-label">Hình thức giao nhận</p>
                            <div class="checkout-type-order">
                                <input type="radio" id="giaotannoi" name="shippingMethod" value="Giao tận nơi">
                                <label for="giaotannoi">
                                    <i class="fa-duotone fa-moped" style="--fa-primary-color: dodgerblue;"></i>
                                    Giao tận nơi
                                </label>
                                <input type="radio" id="tudenlay" name="shippingMethod" value="Tự đến lấy">
                                <label for="tudenlay">
                                    <i class="fa-duotone fa-box-heart" style="--fa-primary-color: pink;"></i>
                                    Tự đến lấy
                                </label>
                            </div>
                        </div>

                        <!-- 4.2 Ngày giao hàng -->
                        <div class="content-group">
                            <p class="checkout-content-label">Ngày giao hàng</p>
                            <div class="date-order">
                                <!-- Nội dung động từ setupDeliveryDates() -->
                            </div>
                            <input type="date" name="deliveryDate" id="deliveryDate" style="margin-top: 10px;" required>
                        </div>

                        <!-- 4.3 Thời gian nhận hàng -->
                        <div class="content-group" id="delivery-time-group">
                            <p class="checkout-content-label">Thời gian nhận hàng</p>
                            <div class="delivery-time">
                                <input type="radio" name="deliveryTime" id="deliverytime" value="specific" class="radio" required>
                                <label for="deliverytime">Nhận vào giờ</label>
                                <select class="choise-time" name="specificDeliveryTime">
                                    <c:forEach var="hour" begin="8" end="21">
                                        <option value="${hour}:00">${hour}:00 - ${hour + 1}:00</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <!-- 4.4 Chi nhánh tự đến lấy -->
                        <div class="content-group" id="tudenlay-group">
                            <p class="checkout-content-label">Lấy hàng tại chi nhánh</p>
                            <div class="delivery-time">
                                <input type="radio" name="recipientAddress" id="chinhanh-1" class="radio" value="Khu phố 6, Phường Linh Trung, TP Thủ Đức, TP HCM">
                                <label for="chinhanh-1">Khu phố 6, Phường Linh Trung, TP Thủ Đức, TP HCM</label>
                            </div>
                            <div class="delivery-time">
                                <input type="radio" name="recipientAddress" id="chinhanh-2" class="radio" value="Nhơn Tân, An Nhơn, Bình Định">
                                <label for="chinhanh-2">Nhơn Tân, An Nhơn, Bình Định</label>
                            </div>
                        </div>

                        <!-- 4.5 Ghi chú đơn hàng -->
                        <div class="content-group">
                            <p class="checkout-content-label">Ghi chú đơn hàng</p>
                            <textarea name="orderNote" class="note-order" placeholder="Nhập ghi chú"></textarea>
                        </div>
                    </div>
                </div>

                <!-- 5. Thông tin người nhận -->
                <div class="checkout-row">
                    <div class="checkout-col-title">Thông tin người nhận</div>
                    <div class="checkout-col-content">
                        <div class="content-group">
                            <!-- 5.1 Tên người nhận -->
                            <div class="form-group">
                                <input id="tennguoinhan" name="recipientName" type="text" placeholder="Tên người nhận" class="form-control" required>
                                <span class="form-message"></span>
                            </div>
                            <!-- 5.2 Số điện thoại -->
                            <div class="form-group">
                                <input id="sdtnhan" name="recipientPhone" type="text" placeholder="Số điện thoại nhận hàng" class="form-control" required>
                                <span class="form-message"></span>
                            </div>
                            <!-- 5.3 Tỉnh/Thành phố -->
                            <div class="form-group">
                                <select id="province" name="province" class="form-control">
                                    <option value="">Chọn tỉnh/thành phố</option>
                                </select>
                            </div>
                            <!-- 5.4 Quận/Huyện -->
                            <div class="form-group">
                                <select id="district" name="district" class="form-control">
                                    <option value="">Chọn quận/huyện</option>
                                </select>
                            </div>
                            <!-- 5.5 Phường/Xã -->
                            <div class="form-group">
                                <select id="ward" name="ward" class="form-control">
                                    <option value="">Chọn phường/xã</option>
                                </select>
                            </div>
                            <!-- 5.6 Địa chỉ chi tiết -->
                            <div class="form-group">
                                <input id="diachinhan" name="recipientAddress" type="text" placeholder="Địa chỉ nhận hàng" class="form-control chk-ship">
                                <span class="form-message"></span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 6. Cột thông tin bên phải -->
            <div class="checkout-col-right">
                <p class="checkout-content-label">Đơn hàng</p>
                <!-- 6.1 Danh sách sản phẩm -->
                <div class="bill-total" id="list-order-checkout">
                    <c:forEach var="item" items="${cart}">
                        <div class="food-total">
                            <div class="count">${item.quantity}x</div>
                            <div class="info-food">
                                <div class="name-food">${item.product.name}</div>
                                <div class="price">${item.product.price}K</div>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <!-- 6.2 Thanh toán -->
                <div class="bill-payment">
                    <div class="total-bill-order">
                        <!-- 6.2.1 Tiền hàng -->
                        <div class="priceFlx">
                            <div class="text">Tiền hàng <span class="count-1">${item.quantity} món</span></div>
                            <div class="price-detail">
                                <span id="checkout-cart-total">
                                    <c:choose>
                                        <c:when test="${not empty sessionScope.discountedTotal}">
                                            <fmt:formatNumber value="${sessionScope.discountedTotal}" type="number" pattern="#,##0" />K
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="total" value="0" />
                                            <c:forEach var="item" items="${cart}">
                                                <c:set var="total" value="${total + (item.quantity * item.product.price)}" />
                                            </c:forEach>
                                            <fmt:formatNumber value="${total * 1000}" type="number" pattern="#,##0" />&nbsp;₫

                                        </c:otherwise>
                                    </c:choose>
                                </span>
                            </div>
                        </div>
                        <!-- 6.2.2 Phí vận chuyển -->
                        <div class="priceFlx chk-ship">
                            <div class="text">Phí vận chuyển</div>
                            <div class="price-detail chk-free-ship">
                                <span id="shippingFeeText">0 ₫</span>
                            </div>
                        </div>
                        <input type="hidden" name="shippingFee" id="shippingFeeInput" value="0">
                    </div>
                </div>

                <!-- 6.3 Chính sách -->
                <div class="policy-note">
                    Bằng việc bấm vào nút "Đặt hàng", tôi đồng ý với
                    <a href="#" target="_blank">chính sách hoạt động</a>
                    của chúng tôi.
                </div>

                <!-- 7. Phương thức thanh toán -->
                <div class="payment-options" bis_skin_checked="1">
                    <div bis_skin_checked="1">
                        <!-- 7.1 COD -->
                        <label style="border: 1px solid #ced4da;height: calc(1.5em + 0.75rem + 2px);border-radius: 0.25rem;width: 100%; padding: 6px; background: #fff">
                            <input type="radio" id="rdPaymentTypeCod" name="paymentType" value="COD" checked="">&nbsp;&nbsp;
                            <i class="fa-solid fa-hand-holding-usd"></i>
                            <span>&nbsp;&nbsp;Thanh toán khi nhận hàng</span>
                            <input type="hidden" name="paymentStatus" value="Chưa thanh toán">
                        </label>

                        <!-- 7.2 Momo -->
                        <label style="border: 1px solid #ced4da;height: calc(1.5em + 0.75rem + 2px);border-radius: 0.25rem;width: 100%; padding: 6px; background: #fff;">
                            <input type="radio" id="rdPaymentTypeMomo" name="paymentType" value="MOMO">&nbsp;&nbsp;
                            <i class="fa-solid fa-wallet"></i>
                            <span>&nbsp;&nbsp;Thanh toán bằng ví điện tử</span>
                            <input type="hidden" name="paymentStatus" value="Đã thanh toán">
                        </label>

                        <!-- 7.3 VNPay -->
                        <label style="border: 1px solid #ced4da;height: calc(1.5em + 0.75rem + 2px);border-radius: 0.25rem;width: 100%; padding: 6px; background: #fff;">
                            <input type="radio" id="rdPaymentTypeVnpay" name="paymentType" value="VNPAY">&nbsp;&nbsp;
                            <i class="fa-solid fa-credit-card"></i>
                            <span>&nbsp;&nbsp;Thanh toán qua VNPay</span>
                            <input type="hidden" name="paymentStatus" value="Chưa thanh toán">
                        </label>
                    </div>

                    <!-- 8. Tổng tiền -->
                    <input type="hidden" name="totalAmount" id="totalAmountInput" value="${totalFinal}">
                    <div class="total-checkout">
                        <div class="text">Tổng tiền</div>
                        <div class="price-bill">
                            <div class="price-final" id="checkout-cart-price-final">${totalFinal}&nbsp;₫</div>
                        </div>
                    </div>
                    <!-- 9. Nút đặt hàng -->
                    <button type="submit" class="complete-checkout-btn">Đặt hàng</button>
                </div>
            </div>
        </main>
    </div>
</form>
<script>
    function handlePayment(event) {
        event.preventDefault();
        let paymentType = document.querySelector('input[name="paymentType"]:checked').value;

        if (paymentType === "VNPAY") {
            // Tạo một hidden form để submit
            let form = document.createElement("form");
            form.method = "POST";
            form.action = "/checkout1"; // Gửi đến CheckoutServlet

            // Thêm tất cả các field từ form gốc
            let originalForm = document.querySelector('.checkout-form');
            let inputs = originalForm.querySelectorAll('input, select, textarea');

            inputs.forEach(input => {
                if (!input.name) return;
                let clone = input.cloneNode();
                clone.value = input.value;
                form.appendChild(clone);
            });

            document.body.appendChild(form);
            form.submit();
        } else {
            // COD hoặc phương thức khác
            document.querySelector('form.checkout-form').submit();
        }
    }


</script>


<script src="<%= request.getContextPath() %>/doanweb/js/checkout.js"></script>
</body>

</html>