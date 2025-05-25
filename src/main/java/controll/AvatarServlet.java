package controll;

import entity.Users;                  // <-- đổi thành model Users
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

// 7.1: POST /api/avatar/upload (Browser -> AvatarServlet)
@WebServlet("/api/avatar/upload")
@MultipartConfig(
        maxFileSize       = 5 * 1024 * 1024,
        maxRequestSize    = 10 * 1024 * 1024
)
public class AvatarServlet extends HttpServlet {
    private UploadService    uploadService = new UploadService();
    private UserInForServies userService   = new UserInForServies();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            // 7.1.1: AvatarServlet -> Browser: redirect /login
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Users user = (Users) session.getAttribute("user");
        File tempFile = null;

        try {
            Part filePart = request.getPart("avatar");
            if (filePart == null || filePart.getSize() == 0) {
                // 7.1.2: AvatarServlet -> Browser: redirect /profile + message lỗi "Vui lòng chọn file ảnh."
                session.setAttribute("message", "Vui lòng chọn file ảnh.");
                session.setAttribute("messageType", "error");
                response.sendRedirect(request.getContextPath() + "/profile.jsp");
                return;
            }

            String contentType = filePart.getContentType();
            if (!"image/jpeg".equals(contentType) && !"image/png".equals(contentType)) {
                // 7.1.3: AvatarServlet -> Browser: redirect /profile + message lỗi "Chỉ hỗ trợ định dạng ảnh JPG hoặc PNG."
                session.setAttribute("message", "Chỉ hỗ trợ định dạng ảnh JPG hoặc PNG.");
                session.setAttribute("messageType", "error");
                response.sendRedirect(request.getContextPath() + "/profile.jsp");
                return;
            }

            String submitted = filePart.getSubmittedFileName();
            String ext       = submitted.substring(submitted.lastIndexOf('.'));
            String unique    = "avatar_" + user.getId() + "_" + UUID.randomUUID() + ext;

            tempFile = File.createTempFile("upload_", ext);
            filePart.write(tempFile.getAbsolutePath());

            // 7.1.4: AvatarServlet -> UploadService: uploadCloud(uniqueName, tempFile)
            String avatarUrl = uploadService.uploadCloud(unique, tempFile);
            //    7.1.4.1.1.1: UploadService -> AvatarServlet: URL ảnh hoặc null
            if (avatarUrl == null) {
                // 7.1.4.1.1.1.1: AvatarServlet -> Browser: redirect /profile + message lỗi "Upload ảnh thất bại..."
                session.setAttribute("message", "Upload ảnh thất bại, vui lòng thử lại.");
                session.setAttribute("messageType", "error");
                response.sendRedirect(request.getContextPath() + "/profile.jsp");
                return;
            }

            // 7.1.4.1.1.1.2: AvatarServlet -> UserInForServies: updateAvatar(userId, avatarUrl)
            boolean ok = userService.updateAvatar(user.getId(), avatarUrl);
            // 7.1.4.1.1.1.3: UserInForServies -> AvatarServlet: kết quả thành công/không
            if (!ok) {
                session.setAttribute("message", "Cập nhật avatar thất bại.");
                session.setAttribute("messageType", "error");
            } else {
                session.setAttribute("message", "Cập nhật avatar thành công!");
                session.setAttribute("messageType", "success");
                session.setAttribute("avatarUrl", avatarUrl);
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("message", "Có lỗi xảy ra khi tải lên avatar.");
            session.setAttribute("messageType", "error");
        } finally {
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
            // 7.1.4.1.1.1.5: AvatarServlet -> Browser: redirect /profile (sau khi set message)
            response.sendRedirect(request.getContextPath() + "/profile");
        }
    }
}