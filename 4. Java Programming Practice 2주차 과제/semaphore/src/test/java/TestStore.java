import org.junit.Test;

import com.nhnacademy.Store;

public class TestStore {
    //TODO 왜 테스트가 안 될까
    @Test
    public static void testStore() throws Exception {
        Store store = new Store(3);

        store.enter();

        store.sell();

        store.buy();

        store.exit();
    }
}