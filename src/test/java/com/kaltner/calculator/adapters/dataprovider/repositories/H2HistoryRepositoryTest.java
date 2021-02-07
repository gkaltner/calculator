package com.kaltner.calculator.adapters.dataprovider.repositories;

import com.kaltner.calculator.adapters.dataprovider.entities.HistoryEntity;
import com.kaltner.calculator.core.domain.Action;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class H2HistoryRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private H2HistoryRepository repository;

    @Test
    void findAllByOrderByTimeStampDesc() {

        HistoryEntity entity1 = new HistoryEntity(Action.ADD, 1, 1, 2);
        HistoryEntity entity2 = new HistoryEntity(Action.SUBTRACT, 1, 1, 2);
        entityManager.persist(entity1);
        entityManager.persist(entity2);
        entityManager.flush();

        Iterable<HistoryEntity> entities = repository.findAllByOrderByTimeStampDesc();

        entities.forEach(historyEntity -> {
            Assertions.assertNotNull(historyEntity.getId());
            Assertions.assertEquals(1, historyEntity.getOperator1());
            Assertions.assertEquals(1, historyEntity.getOperator2());
            Assertions.assertEquals(2, historyEntity.getResult());
        });
    }

    @Test
    void save() {

        HistoryEntity entity = new HistoryEntity(Action.ADD, 1, 1, 2);

        repository.save(entity);

        Iterable<HistoryEntity> entityPersisted = repository.findAll();

        Assertions.assertTrue(entityPersisted.iterator().hasNext());
        Assertions.assertNotNull(entityPersisted.iterator().next());

    }
}
