package basesyntax.service.file;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    public static final String CORRECT_PATH = "src/test/resources/test.csv";
    public static final String INCORRECT_PATH = "";
    private static WriterService writerService;

    @BeforeClass
    public static void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void write_incorrectPath_notOk() {
        Assert.assertThrows("Throw exception for the empty path: ",
                RuntimeException.class, () -> writerService.write(INCORRECT_PATH, ""));
    }
}
