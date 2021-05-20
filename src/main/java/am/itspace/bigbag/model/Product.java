package am.itspace.bigbag.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = " Name can not be null or empty")
    private String name;
    @NotNull(message = " Price can not be null or empty")
    private double price;
    @Enumerated(EnumType.STRING)
    private Currency currency = Currency.USD;
    private String description;
    @NotEmpty(message = " Size can not be null or empty")
    private String size;
    @NotNull(message = " Quantity can not be null or empty")
    private int quantity;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Enumerated(EnumType.STRING)
    private ProductType type;
    private String picUrl;
    @NotEmpty(message = " Product code is required")
    private String productCode;
}
