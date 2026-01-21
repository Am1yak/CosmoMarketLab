package org.labweb.webjavalab.repositories;

import org.labweb.webjavalab.domain.adapters.usecases.entities.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
}
