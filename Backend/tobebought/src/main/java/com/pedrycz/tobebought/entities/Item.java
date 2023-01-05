package com.pedrycz.tobebought.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "items")

// TODO: MAKE DTO

public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotBlank(message = "Item must have a name")
    private String name;

    @NonNull
    @DecimalMin(value="0.0", inclusive = false)
    private Float quantity;

    @NonNull
    @NotBlank(message = "Item must have unit")
    private String unit;

    private boolean checked = false;

    @ManyToOne(optional = false)
    @JoinColumn(name = "shopping_list_id", referencedColumnName = "id")
    private ShoppingList shoppingList;


}
