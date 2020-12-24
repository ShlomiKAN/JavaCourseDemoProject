package com.example.demo.dto;

import com.example.demo.beans.ItemType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDto {
    @Null
    private Long id;

    @Null
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime createdDate;

    @Null
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime lastModifiedDate;

    @NotBlank
    private String itemName; //==> Different name than hibernate entity

    @NotNull
    private ItemType itemType;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Positive
    @NotNull
    private BigDecimal price;
}
