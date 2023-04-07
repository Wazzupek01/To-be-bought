package com.pedrycz.tobebought.model.item;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ItemDataDTO {

    private UUID id;

    @NotBlank
    private String name;

    @DecimalMin(value="0.0", inclusive=false)
    private Float quantity;

    @NotBlank
    private String unit;

    private boolean checked = false;


}
