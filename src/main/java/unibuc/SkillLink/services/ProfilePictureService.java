package unibuc.SkillLink.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ProfilePictureService {

    @Value("${profile.pictures.upload.dir:profile-pictures}")
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
            throw new RuntimeException("Could not initialize profile pictures directory", e);
        }
    }

    /**
     * Save a profile picture for a user
     * @param file The uploaded file
     * @param username The username to use as the filename
     * @return The filename of the saved picture
     */
    public String saveProfilePicture(MultipartFile file, String username) {
        try {
            init();
            
            String filename = username + getFileExtension(file.getOriginalFilename());
            Path destinationFile = Paths.get(uploadDir).resolve(Paths.get(filename)).normalize().toAbsolutePath();
            
            Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
            
            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store profile picture", e);
        }
    }

    /**
     * Delete a profile picture
     * @param filename The filename to delete
     * @return true if deleted successfully, false otherwise
     */
    public boolean deleteProfilePicture(String filename) {
        try {
            Path file = Paths.get(uploadDir).resolve(filename).normalize().toAbsolutePath();
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete profile picture", e);
        }
    }

    /**
     * Get the file extension from a filename
     * @param filename The filename
     * @return The file extension (including the dot)
     */
    private String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf(".") == -1) {
            return ".jpg"; // Default extension
        }
        return filename.substring(filename.lastIndexOf("."));
    }
}