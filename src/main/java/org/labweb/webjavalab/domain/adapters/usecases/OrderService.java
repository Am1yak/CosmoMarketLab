package org.labweb.webjavalab.domain.adapters.usecases;

import lombok.RequiredArgsConstructor;
import org.labweb.webjavalab.repositories.OrderRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    
}
