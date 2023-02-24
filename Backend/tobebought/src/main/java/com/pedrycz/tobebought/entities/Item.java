package com.pedrycz.tobebought.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "items")
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
