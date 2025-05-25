package controll;

import dao.MySQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserInForServies {

    /** Cập nhật username, email, phone */
    public boolean updateInfo(long userId, String username, String email, String phone) {
        String sql = "UPDATE `user` "
                + "SET `Username` = ?, `Email` = ?, `phone` = ? "
                + "WHERE `User_ID` = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setLong(4, userId);
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Cập nhật address */
    public boolean updateAddress(long userId, String address) {
        String sql = "UPDATE `user` "
                + "SET `address` = ? "
                + "WHERE `User_ID` = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, address);
            stmt.setLong(2, userId);
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Cập nhật avatar URL vào trường picture */
    // 7.1.4.1.1.1.2: AvatarServlet -> UserInForServies: updateAvatar(userId, avatarUrl)
    public boolean updateAvatar(long userId, String avatarUrl) {
        String sql = "UPDATE `user` "
                + "SET `picture` = ? "
                + "WHERE `User_ID` = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, avatarUrl);
            stmt.setLong(2, userId);
            // 7.1.4.1.1.1.3: UserInForServies -> AvatarServlet: kết quả thành công/không
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Cập nhật đồng thời thông tin cơ bản và địa chỉ */
    public boolean updateUser(long userId,
                              String username, String email, String phone,
                              String address) {
        boolean ok1 = updateInfo(userId, username, email, phone);
        boolean ok2 = updateAddress(userId, address);
        return ok1 && ok2;
    }
}