package ge.mziuri.emarket.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class ItemDto {
    @NotBlank(message = "Item name cannot be blank")
    private String name;

    @NotNull(message = "Item price is required")
    @Min(value = 0, message = "Item price cannot be negative")
    @Max(value = 99999999L, message = "Item price is too large")
    private BigDecimal price;

    private String description;

    private MultipartFile photo;
}
