package com.bohemian.app;

import com.bohemian.app.entity.ItemDAO;
import com.bohemian.app.utils.SearchParameters;
import com.bohemian.app.service.IItemService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BohemianAppApplicationTests {

    @Autowired
    protected IItemService itemService;

    @Test
    public void testConcurrentAccess() throws InterruptedException, ExecutionException {
        SearchParameters parameters = new SearchParameters(Integer.MIN_VALUE, Integer.MAX_VALUE, 10, 0, List.of());

        ExecutorService executorService = Executors.newFixedThreadPool(100);
        List<Callable<Void>> tasks = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            tasks.add(() -> {
                ItemDAO item = new ItemDAO();
                item.setUserValue(123456789);
                item.setTags(List.of("First", "Second"));
                Long id = itemService.createItem(item);

                itemService.getItem(id);
                item.setUserValue(987654321);
                itemService.updateItem(id, item);
                itemService.findItems(parameters);
                itemService.deleteItem(id);
                return null;
            });
        }
        List<Future<Void>> futures = executorService.invokeAll(tasks);
        for (Future<Void> future : futures) {
            future.get();
        }
        executorService.shutdown();
    }

    @Test
    public void testConcurrentUpdate() throws Exception {
        final int numThreads = 100;
        final int numIterations = 10;
        ItemDAO itemDAO = new ItemDAO();
        itemDAO.setUserValue(123);
        final Long itemId = itemService.createItem(itemDAO);

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future> futures = new ArrayList<>();

        for (int i = 0; i < numThreads; i++) {
            Callable<Void> task = () -> {
                for (int j = 0; j < numIterations; j++) {
                    ItemDAO item = itemService.getItem(itemId);
                    item.setUserValue(456);
                    itemService.updateItem(itemId, item);
                }
                return null;
            };
            futures.add(executor.submit(task));
        }

        for (Future future : futures) {
            future.get();
        }

        ItemDAO updatedItem = itemService.getItem(itemId);
        Assert.assertEquals(456L, updatedItem.getUserValue().longValue());
    }

}