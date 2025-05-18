package unibuc.SkillLink.mappers.ads;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import unibuc.SkillLink.DTOs.ads.AdResponse;
import unibuc.SkillLink.DTOs.ads.CreateAdRequest;
import unibuc.SkillLink.models.Ad;

@Mapper(componentModel = "spring")
public interface AdsMapper {
    @Mapping(source = "description", target = "description")
    @Mapping(target = "picture", ignore = true)
    @Mapping(source = "title", target = "title")
    @Mapping(source = "rate", target = "rate")
    Ad toEntity(CreateAdRequest createAdRequest);
    @Mapping(source = "description", target = "description")
    @Mapping(source = "picture", target = "picture")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "rate", target = "rate")
    @Mapping(source = "id", target = "id")
    AdResponse fromEntity(Ad createAdRequest);

}
