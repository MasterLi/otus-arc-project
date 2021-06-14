package ru.user.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.user.dto.OrderInfoDto;
import ru.user.dto.RequestParamDto;
import ru.user.entity.AdminOrdersResultRowMapper;
import ru.user.enums.OrderStateEnum;
import ru.user.repository.OrderRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderAdminServiceImpl implements OrderAdminService {
    private final OrderRepository orderRepository;

    private final DataSource dataSource;
    private final int prepareThreshold = 3;

    @Override
    public List<OrderInfoDto> getAllActiveOrders(RequestParamDto requestParamDto) {

        List<OrderInfoDto> orderInfo = getAllActiveOrdersQuery(requestParamDto.getOrderStateFilter(),
                requestParamDto.getSortField(),
                requestParamDto.getSortDirection());

        return orderInfo;
    }

    @SneakyThrows
    public List<OrderInfoDto> getAllActiveOrdersQuery(OrderStateEnum orderStateFilter,
                                                      String sortField,
                                                      Sort.Direction sortDirection) {

        StringBuilder sql = new StringBuilder()
                .append("select ord.id as id, usr.user_name as user_name, ord.date_created as date_created, ord.state as state,\n" +
                        "            (select count(odl.id) from public.order_detail_log odl where odl.order_id=ord.id and odl.is_deleted=false) as item_count,\n" +
                        "            (select sum(odl.product_count * product.price) from public.order_detail_log odl LEFT JOIN public.products product ON product.id=odl.product_id\n" +
                        "            where odl.order_id=ord.id and odl.is_deleted=false)  as price\n" +
                        "            from public.orders ord LEFT JOIN public.users usr ON usr.id=ord.user_id  where ord.state NOT IN ('COMPLETED', 'REJECTED') ");
        if (Objects.nonNull(orderStateFilter)) {
            //костыль, надо параметром
            sql.append("and ord.state LIKE '" + orderStateFilter + "'");
        }
        if (Objects.nonNull(sortField)) {
            String sortFieldTable;
            switch (sortField) {
                case "createDate":
                    sortFieldTable = "ord.date_created";
                    break;
                case "price":
                    sortFieldTable = "price";
                    break;
                default:
                    sortFieldTable = "ord.date_created";
            }
            sql.append("ORDER BY " + sortFieldTable);
            if (Objects.nonNull(sortDirection)) {
                sql.append(" " + sortDirection.name());
            }
        }

        sql.append(";");

        try (Connection connection = dataSource.getConnection();
             PreparedStatement proc = connection.prepareStatement(sql.toString())) {
            org.postgresql.PGStatement pgstmt = proc.unwrap(org.postgresql.PGStatement.class);
            pgstmt.setPrepareThreshold(prepareThreshold);

            ResultSet resultSet = proc.executeQuery();
            AdminOrdersResultRowMapper mapper = new AdminOrdersResultRowMapper();
            List<OrderInfoDto> resultDtoList = new LinkedList<>();
            while (resultSet.next()) {
                resultDtoList.add(mapper.mapRow(resultSet, resultSet.getRow()));
            }
            return resultDtoList;
        } catch (SQLException e) {
            throw e;
        }
    }
}
