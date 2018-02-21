package com.backendcodingchallenge.service;

import com.backendcodingchallenge.dto.ExpenseDto;
import com.backendcodingchallenge.entity.Expense;
import com.backendcodingchallenge.repository.ExpenseRepository;
import com.backendcodingchallenge.util.CalculationUtil;
import com.backendcodingchallenge.util.MapperUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

import static com.backendcodingchallenge.util.ConstantUrls.FRONT_END_URL;
import static java.util.stream.Collectors.toList;

/**
 * Main Service providing rest service endpoints
 */
@RestController
@RequestMapping("/api/expenses")
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    private final AmountConversionService euroConversionService;

    private final ModelMapper modelMapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository, AmountConversionService euroConversionService, ModelMapper modelMapper) {
        this.expenseRepository = expenseRepository;
        this.modelMapper = modelMapper;
        this.euroConversionService = euroConversionService;
    }

    @PostConstruct
    public void init() {
        MapperUtil.initMapper(modelMapper, euroConversionService.provideStringToBigDecimalPoundsConverter());
    }

    @CrossOrigin(origins = FRONT_END_URL)
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ExpenseDto> create(@Valid @RequestBody ExpenseDto expenseDto) {
        logger.info("Running 'create' endpoint");
        logger.info("Accepting expenseDto: " + expenseDto);
        Expense expense = modelMapper.map(expenseDto, Expense.class);
        expense = expenseRepository.save(expense);
        return new ResponseEntity<>(mapToDto(expense), HttpStatus.OK);
    }

    @CrossOrigin(origins = FRONT_END_URL)
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ExpenseDto>> getAll() {
        logger.info("Running 'getAll' endpoint");
        List<Expense> expenses = expenseRepository.findAll();
        if (expenses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<ExpenseDto> expenseDtos = expenses.stream().map(this::mapToDto).collect(toList());
        logger.info("Returning nb of expenseDto: " + expenseDtos.size());
        logger.info("Detailed list of expenseDto: " + expenseDtos);
        return new ResponseEntity<>(expenseDtos, HttpStatus.OK);
    }

    private ExpenseDto mapToDto(Expense expense) {
        ExpenseDto expenseDto = modelMapper.map(expense, ExpenseDto.class);
        // shouldn't be null but otherwise test_createExpense_failAmountNull won't pass
        if (expenseDto.getAmount() != null) {
            expenseDto.setVat(CalculationUtil.calculateVat(new BigDecimal(expenseDto.getAmount())));
        }
        return expenseDto;
    }
}
