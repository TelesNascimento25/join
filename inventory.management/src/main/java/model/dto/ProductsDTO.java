package model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductsDTO {
    private String name;
    private String description;
    private Double price;
    private Long categoryId;
}
