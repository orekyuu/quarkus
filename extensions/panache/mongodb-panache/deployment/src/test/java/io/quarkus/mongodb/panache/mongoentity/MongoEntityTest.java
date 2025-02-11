package io.quarkus.mongodb.panache.mongoentity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.inject.Inject;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.test.QuarkusUnitTest;

public class MongoEntityTest {
    @RegisterExtension
    static QuarkusUnitTest runner = new QuarkusUnitTest()
            .setArchiveProducer(() -> ShrinkWrap.create(JavaArchive.class)
                    .addClasses(
                            MongoEntityEntity.class,
                            MongoEntityRepository.class)
                    .addAsResource("application.properties"));

    @Inject
    MongoEntityRepository mongoEntityRepository;

    @Test
    public void testMongoEntity() {
        assertEquals("mongoEntity", MongoEntityEntity.mongoDatabase().getName());
        assertEquals("mongoEntity", mongoEntityRepository.mongoDatabase().getName());
    }

}
