package ru.user.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.user.dto.OrderDetailDto;
import ru.user.entity.OrderDetailLog;
import ru.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface OrderDetailLogRepository extends CrudRepository<OrderDetailLog, Long> {
    Optional<OrderDetailLog> findById(Long id);

    @Query(value = "select new ru.user.dto.OrderDetailDto(odl.id, odl.order.id, odl.orderProduct.id, odl.itemCount, odl.orderProduct.name, odl.orderProduct.price, " +
            "odl.itemCount * odl.orderProduct.price)" +
            " from OrderDetailLog odl where odl.order.id=:orderId and odl.isDeleted=false")
    List<OrderDetailDto> getUserOrderDetailInfo(@Param(value = "orderId") Long orderId);
}
