package unibuc.SkillLink.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class AdPictureService {

    @Value("${ad.pictures.upload.dir:ad-pictures}")
    private String uploadDir;

    /**
     * Initialize the upload directory if it doesn't exist
     */
    public void init() {
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize ad pictures directory", e);
        }
    }

    /**
     * Save an ad picture
     * @param base64Image The base64 encoded image
     * @param adId The ad ID to use as part of the filename
     * @return The filename of the saved picture
     */
    public String saveAdPicture(String base64Image, UUID adId) {
        try {
            init();
            
            // Extract the image format from the base64 string
            String imageFormat = getImageFormatFromBase64(base64Image);
            String filename = adId.toString() + "." + imageFormat;
            Path destinationFile = Paths.get(uploadDir).resolve(Paths.get(filename)).normalize().toAbsolutePath();
            
            // Remove the data URL prefix if present (e.g., "data:image/jpeg;base64,")
            String base64Data = base64Image;
            if (base64Image.contains(",")) {
                base64Data = base64Image.split(",")[1];
            }
            
            byte[] imageBytes = java.util.Base64.getDecoder().decode(base64Data);
            Files.write(destinationFile, imageBytes);
            
            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store ad picture", e);
        }
    }

    /**
     * Delete an ad picture
     * @param filename The filename to delete
     * @return true if deleted successfully, false otherwise
     */
    public boolean deleteAdPicture(String filename) {
        try {
            Path file = Paths.get(uploadDir).resolve(filename).normalize().toAbsolutePath();
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete ad picture", e);
        }
    }

    /**
     * Extract the image format from a base64 string
     * @param base64Image The base64 encoded image
     * @return The image format (e.g., "jpg", "png", etc.)
     */
    private String getImageFormatFromBase64(String base64Image) {
        if (base64Image.startsWith("data:image/")) {
            Pattern pattern = Pattern.compile("data:image/([a-zA-Z]+);base64,.*");
            var matcher = pattern.matcher(base64Image);
            if (matcher.matches()) {
                String format = matcher.group(1).toLowerCase();
                return format.equals("jpeg") ? "jpg" : format;
            }
        }
        
        // Default to jpg if format cannot be determined
        return "jpg";
    }
} 