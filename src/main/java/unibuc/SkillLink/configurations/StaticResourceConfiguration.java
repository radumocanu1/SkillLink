package unibuc.SkillLink.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class StaticResourceConfiguration implements WebMvcConfigurer {

    @Value("${profile.pictures.upload.dir:profile-pictures}")
    private String uploadDir;

    @Value("${ad.pictures.upload.dir:ad-pictures}")
    private String adUploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploadPath = Paths.get(uploadDir);
        String uploadAbsolutePath = uploadPath.toFile().getAbsolutePath();
        
        registry.addResourceHandler("/profile-pictures/**")
                .addResourceLocations("file:" + uploadAbsolutePath + "/");

        Path adUploadPath = Paths.get(adUploadDir);
        String adUploadAbsolutePath = adUploadPath.toFile().getAbsolutePath();


        registry.addResourceHandler("/ad-pictures/**")
                .addResourceLocations("file:" + adUploadAbsolutePath + "/");
    }
}