<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>CheckOut page</title>
    <style>
        body {
            font-family: "Roboto", sans-serif !important;
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        h1 {
            color: coral;
        }

        h2 {
            color: #343a40;
        }

        .mainscreen {
            min-height: 100vh;
            width: 100%;
            display: flex;
            flex-direction: column;
            background-color: white;
            color: coral;
        }

        .card {
            width: 60rem;
            margin: auto;
            background: white;
            align-self: center;
            top: 50rem;
            border-radius: 1.5rem;
            box-shadow: 4px 3px 20px #3535358c;
            display: flex;
            flex-direction: row;
        }

        .leftside {
            background: #343a40;
            width: 25rem;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            border-top-left-radius: 1.5rem;
            border-bottom-left-radius: 1.5rem;
        }

        .product {
            object-fit: cover;
            width: 100%;
            height: 100%;
            /* border-radius: 100%; */
            border-top-left-radius: 28px;
            border-bottom-left-radius: 28px;
        }

        .rightside {
            background-color: white;
            width: 35rem;
            border-bottom-right-radius: 1.5rem;
            border-top-right-radius: 1.5rem;
            padding: 1rem 2rem 3rem 3rem;
        }

        p {
            display: block;
            font-size: 1.1rem;
            font-weight: 400;
            margin: 0.8rem 0;
        }

        .inputbox {
            color: #343a40;
            width: 100%;
            padding: 0.5rem;
            border: none;
            border-bottom: 1.5px solid #ccc;
            margin-bottom: 1rem;
            border-radius: 0.3rem;
            font-family: "Roboto", sans-serif;
            color: #343a40;
            font-size: 1.1rem;
            font-weight: 500;
            outline: none;
        }

        .expcvv {
            display: flex;
            justify-content: space-between;
            padding-top: 0.6rem;
        }

        .expcvv_text {
            padding-right: 1rem;
        }
        .expcvv_text2 {
            padding: 0 1rem;
        }

        .button {
            background: #343a40;
            padding: 15px;
            border: none;
            border-radius: 50px;
            color: white;
            font-weight: 400;
            font-size: 1.2rem;
            margin-top: 10px;
            width: 100%;
            letter-spacing: 0.11rem;
            outline: none;
        }

        .button:hover {
            transform: scale(1.05) translateY(-3px);
            box-shadow: 3px 3px 6px #38373785;
        }

        @media only screen and (max-width: 1000px) {
            .card {
                flex-direction: column;
                width: auto;
            }

            .leftside {
                width: 100%;
                border-top-right-radius: 0;
                border-bottom-left-radius: 0;
                border-top-right-radius: 0;
                border-radius: 0;
            }

            .rightside {
                width: auto;
                border-bottom-left-radius: 1.5rem;
                padding: 0.5rem 3rem 3rem 2rem;
                border-radius: 0;
            }

            .product {
                object-fit: cover;
                width: 100%;
                height: 400px;
                /* border-radius: 100%; */
            }
        }

        @media only screen and (max-width: 600px) {
            .product {
                object-fit: cover;
                width: 100%;
                height: 400px;
                /* border-radius: 100%; */
            }
        }
    </style>
</head>
<body>
<!-- partial:index.partial.html -->
<div class="mainscreen">
    <div class="card">
        <div class="leftside">
            <img
                    src="https://img.tripi.vn/cdn-cgi/image/width=700,height=700/https://gcs.tripi.vn/public-tripi/tripi-feed/img/474119sWg/hinh-anh-ga-ran-sieu-dep_024421256.jpg"
                    class="product"
                    alt="product image"
            />
        </div>
        <div class="rightside">
            <form action="" id="checkoutForm">
                <h1>Thanh toán</h1>
                <h2>Thông tin thanh toán</h2>
                <p>Tên chủ thẻ</p>
                <input type="text" class="inputbox" name="name" required />
                <p>Số Thẻ</p>
                <input
                        type="number"
                        class="inputbox"
                        name="card_number"
                        id="card_number"
                        required
                />

                <p>Loại thẻ</p>
                <select class="inputbox" name="card_type" id="card_type" required>
                    <option value="">--Các loại thẻ--</option>
                    <option value="Visa">Visa</option>
                    <option value="RuPay">RuPay</option>
                    <option value="MasterCard">MasterCard</option>
                </select>
                <div class="expcvv">
                    <p class="expcvv_text">Hết hạn</p>
                    <input
                            type="date"
                            class="inputbox"
                            name="exp_date"
                            id="exp_date"
                            required
                    />

                    <p class="expcvv_text2">CVV</p>
                    <input
                            type="password"
                            class="inputbox"
                            name="cvv"
                            id="cvv"
                            required
                    />
                </div>
                <p></p>
                <button type="submit" class="button">
                    Thanh toán
                </button>
            </form>
        </div>
    </div>
</div>
<!-- partial -->
<script>
    // Giữ nguyên thông báo thành công sau khi thanh toán
    document.getElementById('checkoutForm').addEventListener('submit', function(event) {
        event.preventDefault(); // Ngừng gửi form mặc định

        // Gửi thông tin thanh toán đến backend (API hoặc xử lý thanh toán tại server)
        // Bạn cần thay đổi URL và cách gửi form theo cách bạn xử lý thanh toán

        // Thực hiện thanh toán
        alert("Thanh toán thành công!");
        window.location.href = "/home"; // Chuyển hướng về trang chủ sau khi thanh toán thành công
    });
</script>
</body>
</html>
