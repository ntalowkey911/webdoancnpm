<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Hóa đơn đơn hàng</title>
  <style>
    :root {
      --primary-color: #B5292F;
      --secondary-color: #f8f9fa;
      --text-color: #333;
      --border-color: #ddd;
    }

    body {
      font-family: 'Arial', sans-serif;
      line-height: 1.6;
      color: var(--text-color);
      background-color: #f5f5f5;
      padding: 20px;
    }

    .invoice-container {
      max-width: 800px;
      margin: 0 auto;
      background: white;
      padding: 30px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      border-radius: 5px;
    }

    .invoice-header {
      text-align: center;
      margin-bottom: 30px;
      padding-bottom: 20px;
      border-bottom: 1px solid var(--border-color);
    }

    .invoice-title {
      color: var(--primary-color);
      margin-bottom: 5px;
    }

    .invoice-number {
      font-size: 18px;
      font-weight: bold;
    }

    .invoice-date {
      color: #666;
    }

    .row {
      display: flex;
      flex-wrap: wrap;
      margin-bottom: 20px;
    }

    .col {
      flex: 1;
      padding: 0 15px;
    }

    .section-title {
      background-color: var(--secondary-color);
      padding: 10px 15px;
      margin-bottom: 15px;
      border-left: 4px solid var(--primary-color);
      font-weight: bold;
    }

    .customer-info p, .order-info p {
      margin: 5px 0;
    }

    table {
      width: 100%;
      border-collapse: collapse;
      margin-bottom: 20px;
    }

    th, td {
      padding: 12px 15px;
      text-align: left;
      border-bottom: 1px solid var(--border-color);
    }

    th {
      background-color: var(--secondary-color);
      font-weight: bold;
    }

    .text-right {
      text-align: right;
    }

    .total-section {
      margin-top: 20px;
      padding-top: 20px;
      border-top: 2px solid var(--border-color);
    }

    .total-row {
      display: flex;
      justify-content: space-between;
      margin-bottom: 10px;
    }

    .total-label {
      font-weight: bold;
    }

    .total-amount {
      font-size: 18px;
      color: var(--primary-color);
      font-weight: bold;
    }

    .thank-you {
      text-align: center;
      margin-top: 30px;
      font-style: italic;
      color: #666;
    }

    .print-btn {
      display: block;
      width: 200px;
      margin: 30px auto 0;
      padding: 10px;
      background-color: var(--primary-color);
      color: white;
      text-align: center;
      border: none;
      border-radius: 5px;
      cursor: pointer;
      font-size: 16px;
    }

    .print-btn:hover {
      background-color: #9c2127;
    }

    @media print {
      body {
        background: none;
        padding: 0;
      }

      .invoice-container {
        box-shadow: none;
        padding: 0;
      }

      .print-btn {
        display: none;
      }
    }
  </style>
</head>
<body>
<div class="invoice-container">
  <div class="invoice-header">
    <h1 class="invoice-title">HÓA ĐƠN THANH TOÁN</h1>
    <div class="invoice-number">Mã đơn hàng: ${order.id}</div>
    <div class="invoice-date">Ngày: <fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy HH:mm" /></div>
  </div>

  <div class="row">
    <div class="col">
      <div class="section-title">Thông tin khách hàng</div>
      <div class="customer-info">
        <p><strong>Họ tên:</strong> ${order.receiverName}</p>
        <p><strong>SĐT:</strong> ${order.receiverPhone}</p>
        <p><strong>Địa chỉ:</strong> ${order.shippingAddress}</p>
      </div>
    </div>

    <div class="col">
      <div class="section-title">Thông tin đơn hàng</div>
      <div class="order-info">
        <p><strong>Hình thức giao hàng:</strong> ${order.shippingMethod}</p>
        <p><strong>Ngày giao:</strong> ${order.deliveryDate}</p>
        <p><strong>Thời gian giao:</strong> ${order.deliveryTime}</p>
        <p><strong>Ghi chú:</strong> ${empty order.orderNote ? 'Không có' : order.orderNote}</p>
      </div>
    </div>
  </div>

  <div class="section-title">Chi tiết đơn hàng</div>
  <table>
    <thead>
    <tr>
      <th>STT</th>
      <th>Sản phẩm</th>
      <th class="text-right">Đơn giá</th>
      <th class="text-right">Số lượng</th>
      <th class="text-right">Thành tiền</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${orderDetails}" varStatus="loop">
      <tr>
        <td>${loop.index + 1}</td>
        <td>${item.product.name}</td>
        <td class="text-right"><fmt:formatNumber value="${item.unitPrice}" type="number" pattern="#,##0" /> ₫</td>
        <td class="text-right">${item.quantity}</td>
        <td class="text-right"><fmt:formatNumber value="${item.unitPrice * item.quantity}" type="number" pattern="#,##0" /> ₫</td>
      </tr>
    </c:forEach>
    </tbody>
  </table>

  <div class="total-section">
    <div class="total-row">
      <span class="total-label">Tạm tính:</span>
      <span><fmt:formatNumber value="${order.totalAmount}" type="number" pattern="#,##0" /> ₫</span>
    </div>
    <div class="total-row">
      <span class="total-label">Phí vận chuyển:</span>
      <span><fmt:formatNumber value="${order.shipping.shippingCost}" type="number" pattern="#,##0" /> ₫</span>
    </div>

    <div class="total-row">
      <span class="total-label">Phương thức thanh toán:</span>
      <span>${order.paymentMethod}</span>
    </div>
    <div class="total-row total-amount-row">
      <span class="total-label">Tổng cộng:</span>
      <span class="total-amount"><fmt:formatNumber value="${order.totalAmount}" type="number" pattern="#,##0" /> ₫</span>
    </div>
  </div>

  <div class="thank-you">
    <p>Cảm ơn quý khách đã sử dụng dịch vụ của chúng tôi!</p>
  </div>

  <button class="print-btn" onclick="window.print()">In hóa đơn</button>
</div>
</body>
</html>