package uz.pdp.product;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {

    private Integer id;

    private String name;

    private Double price;

    private Integer quantity;

    private boolean status;
}
