package ru.user.repository;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.user.dto.OrderInfoDto;
import ru.user.entity.Order;
import ru.user.entity.User;
import ru.user.enums.OrderStateEnum;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long> {

    Optional<Order> findById(Long id);

    List<Order> findAllByUserAndIdempotentMarker(User user, Long idempotentMarker);

    @Query(nativeQuery = true, value = "select ord.id as id, usr.user_name as username, ord.date_created as dateCreated, ord.state as state,\n" +
            "            (select count(odl.id) from public.order_detail_log odl where odl.order_id=ord.id and odl.is_deleted=false) as itemCount,\n" +
            "            (select sum(odl.product_count * product.price) from public.order_detail_log odl LEFT JOIN public.products product ON product.id=odl.product_id\n" +
            "            where odl.order_id=ord.id and odl.is_deleted=false)  as price\n" +
            "\n" +
            "            from public.orders ord LEFT JOIN public.users usr ON usr.id=ord.user_id  where ord.state NOT IN ('COMPLETED', 'REJECTED') and usr.id=:userId")
    List<OrderInfo> getAllUserOrders(@Param(value = "userId") Long userId);

    public static interface OrderInfo {
        Long getId();

        String getUsername();

        Instant getDateCreated();

        String getState();

        Integer getItemCount();

        Integer getPrice();
    }
}
