package com.pedrycz.tobebought.model.item;

import com.pedrycz.tobebought.entities.Item;
import org.mapstruct.Mapper;

@Mapper
public interface ItemItemDataDTOMapper {
    ItemDataDTO itemToItemDataDTO(Item item);

}
