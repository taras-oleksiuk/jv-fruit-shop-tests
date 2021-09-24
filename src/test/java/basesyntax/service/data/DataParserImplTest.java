package basesyntax.service.data;

import basesyntax.model.FruitRecordDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataParserImplTest {
    private static DataParserImpl dataParserImpl;

    @BeforeClass
    public static void beforeAll() {
        dataParserImpl = new DataParserImpl();
    }

    @Test
    public void formatData_correctData_ok() {
        List<FruitRecordDto> data = new ArrayList<>();
        data.add(new FruitRecordDto(FruitRecordDto.Type.BALANCE, "banana", 10));
        data.add(new FruitRecordDto(FruitRecordDto.Type.BALANCE, "apple", 15));
        List<FruitRecordDto> expected = new ArrayList<>();
        expected.add(new FruitRecordDto(FruitRecordDto.Type.BALANCE, "banana", 10));
        expected.add(new FruitRecordDto(FruitRecordDto.Type.BALANCE, "apple", 15));
        List<FruitRecordDto> actual = data;
        Assert.assertEquals("Data was not parsed correctly!", expected, actual);
        data.add(new FruitRecordDto(FruitRecordDto.Type.PURCHASE, "apple", 12));
        data.add(new FruitRecordDto(FruitRecordDto.Type.RETURN, "banana", 14));
        expected.add(new FruitRecordDto(FruitRecordDto.Type.PURCHASE, "apple", 12));
        expected.add(new FruitRecordDto(FruitRecordDto.Type.RETURN, "banana", 14));
        actual = data;
        Assert.assertEquals("Data was not parsed correctly!", expected, actual);
    }
}
