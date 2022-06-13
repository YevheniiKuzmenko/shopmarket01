package com.gmail.kuzmenk.yevhenii.shop.models.productsInOrder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@Getter
@Setter
public class ProductsInOrderKey implements Serializable {

    @Column(name="customer_id")
    private Long customerId;

    @Column(name="product_id")
    private Long productId;
}
