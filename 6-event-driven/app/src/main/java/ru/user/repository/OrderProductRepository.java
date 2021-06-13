package ru.user.repository;

import org.springframework.data.repository.CrudRepository;
import ru.user.entity.OrderProduct;

import java.util.List;
import java.util.Optional;

public interface OrderProductRepository extends CrudRepository<OrderProduct, Long> {
    Optional<OrderProduct> findById(Long id);

    List<OrderProduct> findAll();
}
