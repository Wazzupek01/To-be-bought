package com.pedrycz.tobebought.model.item;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDataDTO {
    private Long id;
    @NotBlank
    private String name;
    @DecimalMin(value="0.0", inclusive=false)
    private Float quantity;
    @NotBlank
    private String unit;
    private boolean checked = false;
}
