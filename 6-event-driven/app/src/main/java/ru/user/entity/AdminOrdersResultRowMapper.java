package ru.user.entity;

import org.springframework.jdbc.core.RowMapper;
import ru.user.dto.OrderInfoDto;
import ru.user.enums.OrderStateEnum;

import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;


public class AdminOrdersResultRowMapper implements RowMapper<OrderInfoDto> {

    @Override
    public OrderInfoDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return OrderInfoDto.builder()
                .id(rs.getLong("id"))
                .createDate(toZonedDateTime(rs.getTimestamp("date_created")))
                .itemCount(rs.getInt("item_count"))
                .price(rs.getInt("price"))
                .username(rs.getString("user_name"))
                .state(OrderStateEnum.valueOf(rs.getString("state")))
                .build();
    }

    public static ZonedDateTime toZonedDateTime(Timestamp timestamp) {
        if (null == timestamp) {
            return null;
        } else {
            return timestamp.toLocalDateTime().atZone(ZoneId.systemDefault());
        }
    }


}
