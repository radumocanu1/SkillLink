package unibuc.SkillLink.DTOs.ads;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AdsPage {
    private List<AdResponse> ads;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

}
