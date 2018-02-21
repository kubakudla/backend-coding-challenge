package com.backendcodingchallenge.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Dto for Expense class
 */
@Data
public class ExpenseDto {

    private static final int NAME_MAX_LENGTH = 255;

    @NotNull
    private String amount;

    private BigDecimal vat;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate date;

    @Size(max = NAME_MAX_LENGTH)
    @NotNull
    private String reason;
}
