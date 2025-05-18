package unibuc.SkillLink.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import unibuc.SkillLink.DTOs.ads.CreateAdRequest;
import unibuc.SkillLink.abstractions.Mediator;
import unibuc.SkillLink.annotations.Authorized;
import unibuc.SkillLink.commands.ads.*;
import unibuc.SkillLink.services.AdPictureService;

import java.util.UUID;

@Controller
@RequestMapping("/ads")
public class AdsController {
    @Autowired
    Mediator mediator;

    @Autowired
    private AdPictureService adPictureService;

    @Authorized(authority = "CLIENT")
    @GetMapping
    public String getAdsPage(Model model, @RequestParam int page, @RequestParam int size) {
        var response = mediator.handle(new GetAdsPageCommand(page, size));
        model.addAttribute("ads", response.getAds());
        model.addAttribute("adsPage", response);
        return "ads/list";
    }

    @Authorized(authority = "PROVIDER")
    @GetMapping("/create")
    public String getCreateAdPage(Model model, Authentication authentication) {
        model.addAttribute("authentication", authentication);
        return "ads/create";
    }

    @Authorized(authority = "PROVIDER")
    @PostMapping
    @ResponseBody
    public ResponseEntity<?> addAd(@RequestParam String username, @Validated @RequestBody CreateAdRequest createAdRequest) {
        var response = mediator.handle(new CreateAdCommand(createAdRequest, username));
        return ResponseEntity.ok(response);
    }

    @Authorized
    @GetMapping("/{id}")
    public String getAdDetails(Model model, @PathVariable UUID id) {
        var response = mediator.handle(new GetAdCommand(id));
        model.addAttribute("ad", response);
        return "ads/ad-details";
    }

    @Authorized(authority = "PROVIDER")
    @GetMapping("/my-ads")
    public String getMyAds(Model model, Authentication authentication) {
        var response = mediator.handle(new GetAdsPageCommand(0, 10, authentication.getName()));
        model.addAttribute("ads", response.getAds());
        return "provider/my-ads";
    }

    @Authorized(authority = "PROVIDER")
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteAd(@PathVariable UUID id, Authentication authentication) {
        mediator.handle(new DeleteAdCommand(id, authentication.getName()));
        return ResponseEntity.ok().build();
    }

    @Authorized(authority = "PROVIDER")
    @GetMapping("/{id}/edit")
    public String getEditAdPage(Model model, @PathVariable UUID id, Authentication authentication) {
        var response = mediator.handle(new GetAdCommand(id));
        model.addAttribute("ad", response);
        model.addAttribute("authentication", authentication);
        return "ads/edit";
    }

    @Authorized(authority = "PROVIDER")
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> updateAd(@PathVariable UUID id, @RequestParam String username, 
                                    @Validated @RequestBody CreateAdRequest updateRequest) {
        var response = mediator.handle(new UpdateAdCommand(id, username, updateRequest));
        return ResponseEntity.ok(response);
    }
}
