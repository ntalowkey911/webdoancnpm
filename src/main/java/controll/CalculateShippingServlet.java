package fit.hcmuaf.edu.vn.foodmart.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.lang.Double.isNaN;


@WebServlet(name = "CalculateShippingServlet", value = "/calculate-shipping")
public class CalculateShippingServlet extends HttpServlet {
    private static final String GHN_API_URL = "https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee";
    private static final String TOKEN = "b07a48aa-0c5c-11f0-bdfc-6a0eda42fc14";
    private static final int SHOP_ID = 	5710115;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Lấy dữ liệu từ request
        int fromDistrictId = Integer.parseInt(request.getParameter("from_district_id"));
        int toDistrictId = Integer.parseInt(request.getParameter("to_district_id"));
        String toWardCode = request.getParameter("to_ward_code");
        int serviceId = Integer.parseInt(request.getParameter("service_type_id"));
        int insuranceValue = Integer.parseInt(request.getParameter("insurance_value"));
        int weight = Integer.parseInt(request.getParameter("weight"));

        // Gửi request đến API GHN
        try {
            URL url = new URL(GHN_API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Token", TOKEN);
            conn.setRequestProperty("ShopId", String.valueOf(SHOP_ID));
            conn.setDoOutput(true);

            // Tạo JSON body request
            String jsonInput = String.format(
                    "{\"service_type_id\": %d, \"insurance_value\": %d, \"to_ward_code\": \"%s\", \"to_district_id\": %d, \"from_district_id\": %d, \"weight\": %d}",
                    serviceId, insuranceValue, toWardCode, toDistrictId, fromDistrictId, weight
            );

            // Gửi request
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Nhận phản hồi từ API GHN
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                StringBuilder responseText = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    responseText.append(responseLine.trim());
                }
                br.close();
                response.getWriter().write(responseText.toString());
            } else {
                response.getWriter().write("{\"error\": \"Failed to calculate shipping fee\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"error\": \"Exception occurred: " + e.getMessage() + "\"}");
        }
    }


}