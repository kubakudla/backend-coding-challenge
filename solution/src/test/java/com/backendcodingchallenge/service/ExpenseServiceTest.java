package com.backendcodingchallenge.service;

import com.backendcodingchallenge.dto.ExpenseDto;
import com.backendcodingchallenge.entity.Expense;
import com.backendcodingchallenge.repository.ExpenseRepository;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExpenseServiceTest {

    private static final String REASON = "reason";
    private static final String VALUE = "33.33";

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseService expenseService;

    private static Validator validator;

    private ExpenseDto expenseDto;

    private Expense expense;

    @BeforeClass
    public static void setUpClass() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Before
    public void setUp() {
        initExpense();
        initExpenseDto();

        when(modelMapper.map(expenseDto, Expense.class)).thenReturn(expense);
        when(modelMapper.map(expense, ExpenseDto.class)).thenReturn(expenseDto);
    }

    /*      CREATE  */

    @Test
    public void test_createExpense_success() {
        //when
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);
        ResponseEntity<ExpenseDto> responseEntity = expenseService.create(expenseDto);
        //then

        assertValidationCorrect(expenseDto);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void test_createExpense_failReasonNull() {
        //given
        expenseDto.setReason(null);

        //when
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);
        ResponseEntity<ExpenseDto> responseEntity = expenseService.create(expenseDto);

        //then
        Set<ConstraintViolation<ExpenseDto>> violations = validator.validate(expenseDto);
        ConstraintViolation<ExpenseDto> expenseDtoConstraintViolation = violations.stream().findFirst().get();

        assertEquals("may not be null", expenseDtoConstraintViolation.getMessage());
        assertEquals(1, violations.size());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void test_createExpense_failAmountNull() {
        //given
        expenseDto.setAmount(null);

        //when
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);
        ResponseEntity<ExpenseDto> responseEntity = expenseService.create(expenseDto);

        //then
        Set<ConstraintViolation<ExpenseDto>> violations = validator.validate(expenseDto);
        ConstraintViolation<ExpenseDto> expenseDtoConstraintViolation = violations.stream().findFirst().get();

        assertEquals("may not be null", expenseDtoConstraintViolation.getMessage());
        assertEquals(1, violations.size());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void test_createExpense_failDateNull() {
        //given
        expenseDto.setDate(null);

        //when
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);
        ResponseEntity<ExpenseDto> responseEntity = expenseService.create(expenseDto);

        //then
        Set<ConstraintViolation<ExpenseDto>> violations = validator.validate(expenseDto);
        ConstraintViolation<ExpenseDto> expenseDtoConstraintViolation = violations.stream().findFirst().get();

        assertEquals("may not be null", expenseDtoConstraintViolation.getMessage());
        assertEquals(1, violations.size());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void test_createExpense_failReasonTooLong() {
        //given
        expenseDto.setReason("1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890" +
            "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890" +
            "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");

        //when
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);
        ResponseEntity<ExpenseDto> responseEntity = expenseService.create(expenseDto);

        //then
        Set<ConstraintViolation<ExpenseDto>> violations = validator.validate(expenseDto);
        ConstraintViolation<ExpenseDto> expenseDtoConstraintViolation = violations.stream().findFirst().get();

        assertEquals("size must be between 0 and 255", expenseDtoConstraintViolation.getMessage());
        assertEquals(1, violations.size());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /*      GET ALL  */

    @Test
    public void test_getAll_success() {
        //when
        when(expenseRepository.findAll()).thenReturn(Arrays.asList(expense, expense, expense));
        ResponseEntity<List<ExpenseDto>> responseEntity = expenseService.getAll();

        //then
        assertEquals(3, responseEntity.getBody().size());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void test_getAll_notFound() {
        //when
        when(expenseRepository.findAll()).thenReturn(Arrays.asList());

        //then
        ResponseEntity<List<ExpenseDto>> responseEntity = expenseService.getAll();

        assertEquals(null, responseEntity.getBody());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    private void initExpenseDto() {
        expenseDto = new ExpenseDto();
        expenseDto.setDate(LocalDate.now());
        expenseDto.setReason(REASON);
        expenseDto.setAmount(VALUE);
    }

    private void initExpense() {
        expense = new Expense();
        expense.setDate(LocalDate.now());
        expense.setReason(REASON);
        expense.setAmount(new BigDecimal(VALUE));
    }

    private void assertValidationCorrect(ExpenseDto expenseDto) {
        Set<ConstraintViolation<ExpenseDto>> violations = validator.validate(expenseDto);
        assertTrue(violations.isEmpty());
    }
}