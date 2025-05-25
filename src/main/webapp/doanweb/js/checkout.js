
let priceFinal = document.getElementById("checkout-cart-price-final");
let phiVanChuyenElement = document.querySelector(".chk-free-ship span");
let cartTotalElement = document.getElementById("checkout-cart-total");
let totalAmountInput = document.getElementById("totalAmountInput");




document.addEventListener("DOMContentLoaded", function () {
    const shippingMethodRadios = document.querySelectorAll('input[name="shippingMethod"]');
    shippingMethodRadios.forEach(radio => {
        radio.addEventListener("change", function () {
            if (this.value === "Giao tận nơi") {
                calculateShippingFee();
            } else {
                updateTotal(0);
            }
        });
    });
});

async function calculateShippingFee() {
    let fromDistrictId = 1442;
    let toDistrictId = parseInt(document.getElementById("district").value) || 0;

    let toWardCode = parseInt(document.getElementById("ward").value);
    let serviceId = 2;
    let insuranceValue = parseInt(document.getElementById("totalAmountInput").value) || 0;
    let weight = calculateTotalWeight();

    if (!toDistrictId || !toWardCode) {
        alert("Vui lòng chọn địa chỉ nhận hàng.");
        return;

    }
    console.log("Dữ liệu gửi đi:", {
        from_district_id: fromDistrictId,
        to_district_id: toDistrictId,
        to_ward_code: toWardCode,
        service_type_id: serviceId,
        insurance_value: insuranceValue,
        weight: weight
    });

    let response = await fetch("/calculate-shipping", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: `from_district_id=${fromDistrictId}&to_district_id=${toDistrictId}&to_ward_code=${toWardCode}&service_type_id=${serviceId}&insurance_value=${insuranceValue}&weight=${weight}`
    });

    try {
        let data = await response.json();
        console.log("Phản hồi từ GHN:", data); // Xem API trả về gì
        if (data.code === 200) {
            let shippingFee = data.data.total || 0;
            updateTotal(shippingFee);
        } else {
            console.error("GHN trả về lỗi:", data);
            updateTotal(0);
        }
    } catch (error) {
        console.error("Lỗi khi phân tích JSON:", error);
        console.error("Phản hồi gốc:", await response.text()); // Log response gốc nếu JSON bị lỗi
        updateTotal(0);
    }
}

function updateTotal(shippingFee) {
    let cartTotal = parseInt(document.getElementById("checkout-cart-total").textContent.replace(/\D/g, "")) || 0;
    let finalTotal = cartTotal + shippingFee;

    document.getElementById("checkout-cart-price-final").textContent = `${finalTotal.toLocaleString()} ₫`;
    document.getElementById("shippingFeeInput").value = shippingFee;
    phiVanChuyenElement.textContent = `${shippingFee.toLocaleString()} ₫`;

    document.getElementById("totalAmountInput").value = finalTotal;

}

function calculateTotalWeight() {
    let items = document.querySelectorAll(".food-total");
    let totalWeight = 0;

    items.forEach(item => {
        let countText = item.querySelector(".count").textContent.trim();
        let quantity = parseInt(countText.replace(/\D/g, "")) || 1;
        let weight = 100; // mỗi sản phẩm 100g
        totalWeight += quantity * weight;
    });

    return totalWeight; // Trả về đơn vị gram
}

// Hàm chuyển đổi giao diện giữa các tùy chọn giao hàng
function toggleDeliveryOptions() {
    const btnGiaoTanNoi = document.getElementById("giaotannoi");
    const btnTuDenLay = document.getElementById("tudenlay");
    const giaoTanNoiGroup = document.getElementById("giaotannoi-group");
    const tuDenLayGroup = document.getElementById("tudenlay-group");
    const diaChiNhan = document.getElementById("diachinhan");

    // Khi chọn "Giao tận nơi"
    btnGiaoTanNoi.addEventListener("click", () => {
        btnGiaoTanNoi.classList.add("active");
        btnTuDenLay.classList.remove("active");
        giaoTanNoiGroup.style.display = "block";
        tuDenLayGroup.style.display = "none";
        diaChiNhan.style.display = "block";
        updateTotal(true); // Thêm phí vận chuyển


    });

    // Khi chọn "Giao tận nơi"
    btnGiaoTanNoi.addEventListener("click", () => {
        btnGiaoTanNoi.classList.add("active");
        btnTuDenLay.classList.remove("active");
        tuDenLayGroup.style.display = "none";
        diaChiNhan.style.display = "block";
        updateTotal(true);
    });

    // Khi chọn "Tự đến lấy"
    btnTuDenLay.addEventListener("click", () => {
        btnTuDenLay.classList.add("active");
        btnGiaoTanNoi.classList.remove("active");
        tuDenLayGroup.style.display = "block";
        diaChiNhan.style.display = "none";
        ward.style.display = "none";
        district.style.display = "none";
        province.style.display = "none";
        updateTotal(false);
    });
}
function getCurrentDate() {
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0');
    const day = String(today.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}

// Thiết lập thuộc tính min cho input date
const dateInput = document.getElementById('deliveryDate');
dateInput.min = getCurrentDate();
// Khởi tạo trang thanh toán khi DOM sẵn sàng
document.addEventListener("DOMContentLoaded", function () {
    getCurrentDate();
    toggleDeliveryOptions();

    // Tính toán totalFinal ban đầu
    let cartTotal = parseInt(cartTotalElement.textContent.replace(/\D/g, ""));
    let totalFinal = cartTotal + PHIVANCHUYEN; // Ban đầu là "Giao tận nơi"

    // Cập nhật giá trị cho input hidden sau khi totalFinal đã được tính toán
    totalAmountInput.value = totalFinal;


});
document.addEventListener("DOMContentLoaded", function () {
    updateTotal(true);

    const token = "b07a48aa-0c5c-11f0-bdfc-6a0eda42fc14";

    async function fetchData(url, method, body = null) {
        try {
            const response = await fetch(url, {
                method: method,
                headers: { "Content-Type": "application/json", "Token": token },
                body: body ? JSON.stringify(body) : null
            });
            return await response.json();
        } catch (error) {
            console.error("Lỗi khi lấy dữ liệu:", error);
        }
    }

    async function fetchProvinces() {
        let data = await fetchData("https://online-gateway.ghn.vn/shiip/public-api/master-data/province", "GET");
        if (data?.code === 200) populateOptions("province", data.data, "ProvinceID", "ProvinceName");
    }

    async function fetchDistricts(provinceId) {
        let data = await fetchData("https://online-gateway.ghn.vn/shiip/public-api/master-data/district", "POST", { province_id: parseInt(provinceId) });
        if (data?.code === 200) populateOptions("district", data.data, "DistrictID", "DistrictName");
    }

    async function fetchWards(districtId) {
        let data = await fetchData("https://online-gateway.ghn.vn/shiip/public-api/master-data/ward", "POST", { district_id: parseInt(districtId) });
        if (data?.code === 200) populateOptions("ward", data.data, "WardCode", "WardName");
    }

    function populateOptions(elementId, data, valueField, textField) {
        const select = document.getElementById(elementId);

        data.forEach(item => {
            let option = document.createElement("option");
            option.value = item[valueField];
            option.textContent = item[textField];
            select.appendChild(option);
        });
    }

    function updateAddressInput() {
        const provinceText = document.getElementById("province").selectedOptions[0]?.text || "";
        const districtText = document.getElementById("district").selectedOptions[0]?.text || "";
        const wardText = document.getElementById("ward").selectedOptions[0]?.text || "";
        document.getElementById("diachinhan").value = `${wardText}, ${districtText}, ${provinceText}`;
    }

    document.getElementById("province").addEventListener("change", function () {
        let provinceId = this.value;
        if (provinceId) fetchDistricts(provinceId);
    });

    document.getElementById("district").addEventListener("change", function () {
        let districtId = this.value;
        if (districtId) fetchWards(districtId);
    });

    document.getElementById("ward").addEventListener("change", updateAddressInput);

    fetchProvinces();
});