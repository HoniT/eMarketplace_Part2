package ge.mziuri.emarket.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;

@AllArgsConstructor
@Getter
@Setter
public class ItemResponseDto {
    private Long id;

    private String name;

    private BigDecimal price;

    private String description;

    private Date submissionTime;

    private String photoUrl;

    private String username;
}
