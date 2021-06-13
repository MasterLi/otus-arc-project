package ru.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.user.entity.OrderProduct;
import ru.user.repository.OrderProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NsiServiceImpl implements NsiService {
    final private OrderProductRepository orderProductRepository;

    @Override
    public List<OrderProduct> getAllOrderProducts() {
        return orderProductRepository.findAll();
    }
}
