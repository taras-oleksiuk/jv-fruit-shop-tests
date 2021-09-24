package basesyntax.service;

import basesyntax.model.FruitRecordDto;
import basesyntax.service.operation.DecreaseOperationHandler;
import basesyntax.service.operation.IncreaseOperationHandler;
import basesyntax.service.operation.OperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AmountCalculatorImplTest {
    private static AmountCalculator amountCalculator;

    @BeforeClass
    public static void beforeAll() {
        Map<FruitRecordDto.Type, OperationHandler> operationStrategyMap = new HashMap<>();
        operationStrategyMap.put(FruitRecordDto.Type.BALANCE, new IncreaseOperationHandler());
        operationStrategyMap.put(FruitRecordDto.Type.PURCHASE, new DecreaseOperationHandler());
        operationStrategyMap.put(FruitRecordDto.Type.RETURN, new IncreaseOperationHandler());
        operationStrategyMap.put(FruitRecordDto.Type.SUPPLY, new IncreaseOperationHandler());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationStrategyMap);
        amountCalculator = new AmountCalculatorImpl(operationStrategy);
    }

    @Test
    public void calculateDataForReport_ok() {
        List<FruitRecordDto> fruitRecordDtos = new ArrayList<>();
        fruitRecordDtos.add(new FruitRecordDto(FruitRecordDto.Type.BALANCE, "banana", 15));
        fruitRecordDtos.add(new FruitRecordDto(FruitRecordDto.Type.BALANCE, "apple", 10));
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 15);
        expected.put("apple", 10);
        Map<String, Integer> actual = amountCalculator.calculateDataForReport(fruitRecordDtos);
        Assert.assertEquals("Data wasn't calculated properly", expected, actual);
    }

    @Test
    public void calculateDataForReport_nullOperationType_notOk() {
        List<FruitRecordDto> fruitRecordDtos = new ArrayList<>();
        fruitRecordDtos.add(new FruitRecordDto(null, "banana", 15));
        Assert.assertThrows("You should throw an exception for wrong operation type",
                RuntimeException.class,
                () -> amountCalculator.calculateDataForReport(fruitRecordDtos));
    }

    @Test
    public void calculateDataForReport_negativeAfterDecrease_notOk() {
        List<FruitRecordDto> fruitRecordDtos = new ArrayList<>();
        fruitRecordDtos.add(new FruitRecordDto(FruitRecordDto.Type.BALANCE, "banana", 15));
        fruitRecordDtos.add(new FruitRecordDto(FruitRecordDto.Type.PURCHASE, "banana", 20));
        Assert.assertThrows("You should throw exception for negative "
                        + "amount after operation",
                RuntimeException.class,
                () -> amountCalculator.calculateDataForReport(fruitRecordDtos));
    }
}
