package ge.mziuri.emarket.mapper;

import ge.mziuri.emarket.model.dto.ItemDto;
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
}
