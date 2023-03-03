package com.pedrycz.tobebought.model.item;

import com.pedrycz.tobebought.entities.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ItemItemDataDTOMapper {

    ItemDataDTO itemToItemDataDTO(Item item);

    @Named("itemListToItemDtoList")
    static List<ItemDataDTO> itemListToItemDtoList(List<Item> list){
        List<ItemDataDTO> result = new ArrayList<>();
        ItemItemDataDTOMapper mapper = Mappers.getMapper(ItemItemDataDTOMapper.class);
        for(Item i: list){
            result.add(mapper.itemToItemDataDTO(i));
        }
        return result;
    }
}
