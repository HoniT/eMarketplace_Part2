package ge.mziuri.emarket.mapper;

import ge.mziuri.emarket.model.dto.ItemDto;
import ge.mziuri.emarket.model.dto.ItemResponseDto;
import ge.mziuri.emarket.model.entity.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {
    public Item addDtoToEntity(ItemDto dto) {
        Item i = new Item();
        i.setName(dto.getName());
        i.setPrice(dto.getPrice());
        i.setDescription(dto.getDescription());
        return i;
    }

    public ItemResponseDto entityToResponseDto(Item item) {
        return new ItemResponseDto(item.getId(), item.getName(), item.getPrice(), item.getDescription(),
                item.getSubmissionTime(), item.getPhotoUrl(), (item.getUser() != null) ? item.getUser().getUsername() : "null");
    }
}
