package com.backendcodingchallenge.util;

import com.backendcodingchallenge.dto.ExpenseDto;
import com.backendcodingchallenge.entity.Expense;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

/**
 * Class with util methods for Mapper
 */
public class MapperUtil {

    public static void initMapper(ModelMapper modelMapper, Converter<String, BigDecimal> poundsToEuroConverter) {
        modelMapper.createTypeMap(ExpenseDto.class, Expense.class)
            .addMappings(
                mapper -> mapper.using(poundsToEuroConverter).<BigDecimal>map(src -> src.getAmount(), (dest, v) -> dest.setAmount(v)));
    }
}
