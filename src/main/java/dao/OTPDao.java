package dao;

import entity.OTP;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static dao.MySQLConnection.getConnection;

public class OTPDao {
    public boolean insertOTP(OTP otp) {
        String query = "INSERT INTO user_otp (user_id, otp, expiration_time, verified) VALUES (?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, otp.getUserId());
            ps.setString(2, otp.getOtp());
            ps.setTimestamp(3, new java.sql.Timestamp(otp.getExpirationTime().getTime()));
            ps.setBoolean(4, otp.isVerified());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public OTP getOTPByUserId(int userId) {
        String query = "SELECT * FROM user_otp WHERE user_id = ? AND verified = 0 ORDER BY id DESC LIMIT 1";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()){
                    OTP otp = new OTP();
                    otp.setId(rs.getInt("id"));
                    otp.setUserId(rs.getInt("user_id"));
                    otp.setOtp(rs.getString("otp"));
                    otp.setExpirationTime(rs.getTimestamp("expiration_time"));
                    otp.setVerified(rs.getBoolean("verified"));
                    return otp;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean markOTPAsVerified(int otpId) {
        String query = "UPDATE user_otp SET verified = 1 WHERE id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, otpId);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
//                 cartDao.addToCart(userId, productId, quantity);