import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import lombok.Data;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.precompiled.crud.TableCRUDService;
import org.fisco.bcos.sdk.contract.precompiled.crud.common.Entry;
import org.fisco.bcos.sdk.model.RetCode;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CRUDTest extends BaseTest {

    private final String privateKey = "80407410d609df74fd97be77ff1a10b8b80821a7fa3bd1eb773d5d8067f9cb8a";

    @Autowired
    private Client client;

    @Autowired
    private TableCRUDService crudService;

    @Test
    public void create() throws Exception {
        RetCode retCode = crudService.createTable(Item.DB_NAME, Item.KEY_FIELD, Item.VALUE_FIELDS);
        System.out.println(retCode);
    }

    @Test
    public void desc() throws Exception {
        List<Map<String, String>> desc = crudService.desc(Item.DB_NAME);
        System.out.println(desc);
    }

    @Test
    public void select() throws Exception {
        List<Map<String, String>> list = crudService.select(Item.DB_NAME, String.valueOf(1L), null);
        System.out.println(list);
    }

    @Test
    public void insert() throws Exception {
        TableCRUDService service = new TableCRUDService(client, client.getCryptoSuite().createKeyPair(privateKey));
        Item item = new Item();
        item.setName("roroishere");
        item.setDate(new Date());
        item.setList(Arrays.asList(1L, 2L, 3L));
        RetCode retCode = service.insert(Item.DB_NAME, String.valueOf(1L), new Entry(item.toMap()));
        System.out.println(retCode);
    }

    @Test
    public void remove() throws Exception {
        TableCRUDService service = new TableCRUDService(client, client.getCryptoSuite().createKeyPair(privateKey));
        RetCode retCode = service.remove(Item.DB_NAME, String.valueOf(1L), null);
        System.out.println(retCode);
    }

    @Data
    public static class Item {
        public static final String DB_NAME = "test_item";
        public static final String KEY_FIELD = "id";
        public static final List<String> VALUE_FIELDS = Arrays.asList("name", "list", "date");
        private Long id;
        private String name;
        private List<Long> list;
        private Date date;

        public Map<String, String> toMap() {
            return Convert.convert(new TypeReference<Map<String, String>>() {
            }, this);
        }
    }
}
