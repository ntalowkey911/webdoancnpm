package controll;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.util.Map;

public class UploadService {
    private final Cloudinary cloudinary;

    public UploadService() {
        // Tạo Cloudinary instance với cấu hình hard-code cho local testing
        this.cloudinary = createCloudinary();
    }

    private Cloudinary createCloudinary() {
        // Lưu ý: Hard-code các key nhạy cảm chỉ dùng cho testing local. Không sử dụng trong production.
        String cloudName = "your_cloud_name";
        String apiKey    = "your_api_key";
        String apiSecret = "your_api_secret";

        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key",    apiKey,
                "api_secret", apiSecret
        ));
    }

    public String uploadCloud(String fileName, File file) {
        if (file == null || !file.exists()) {
            System.err.println("File không tồn tại hoặc null.");
            return null;
        }

        try {
            // 7.1.4: AvatarServlet -> UploadService: uploadCloud(uniqueName, tempFile)
            Map<String, Object> uploadOptions = ObjectUtils.asMap(
                    "upload_preset", "avatar_upload",
                    "public_id",     fileName,
                    "folder",        "users/avatars"
            );

            // 7.1.4.1: UploadService -> Cloudinary: upload file
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file, uploadOptions);
            // 7.1.4.1.1: Cloudinary -> UploadService: trả URL ảnh hoặc lỗi

            if (uploadResult.containsKey("secure_url")) {
                String imageUrl = (String) uploadResult.get("secure_url");
                // 7.1.4.1.1.1: UploadService -> AvatarServlet: URL ảnh hoặc null
                System.out.println("Upload thành công! URL: " + imageUrl);
                return imageUrl;
            } else {
                System.err.println("Upload thất bại! Không tìm thấy secure_url.");
                return null;
            }

        } catch (Exception e) {
            System.err.println("Có lỗi xảy ra khi tải lên Cloudinary:");
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String cloudName = "your_cloud_name";
        System.out.println(cloudName);
    }
}