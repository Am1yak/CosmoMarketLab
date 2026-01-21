package org.labweb.webjavalab.domain.adapters.usecases.entities.order;

import jakarta.persistence.*;
import lombok.*;
import org.labweb.webjavalab.domain.adapters.usecases.entities.product.Product;

import java.util.UUID;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private int quantity;

    @Column(name = "current_price")
    private double currentPrice;
}
