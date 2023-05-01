package com.item.app.service;

import com.item.app.AbstractSpringTesting;
import com.item.app.entity.ItemDAO;
import com.item.app.utils.SearchParameters;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ConcurrentItemsServiceTest extends AbstractSpringTesting {

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
        final int threadsCount = 100;
        final int iterationsCount = 10;

        ItemDAO itemDAO = new ItemDAO();
        itemDAO.setUserValue(123);
        itemDAO.setTags(List.of("test", "tag"));
        final Long itemId = itemService.createItem(itemDAO);

        ExecutorService executor = Executors.newFixedThreadPool(threadsCount);
        List<Future> futures = new ArrayList<>();

        for (int i = 0; i < threadsCount; i++) {
            Callable<Void> task = () -> {
                for (int j = 0; j < iterationsCount; j++) {
                    ItemDAO item = itemService.getItem(itemId);
                    item.setUserValue(456);
                    item.setTags(List.of("updated","test","tag"));
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