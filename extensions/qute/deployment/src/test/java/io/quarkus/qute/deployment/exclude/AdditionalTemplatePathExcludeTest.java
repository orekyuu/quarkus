package io.quarkus.qute.deployment.exclude;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import javax.inject.Inject;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.qute.Engine;
import io.quarkus.test.QuarkusUnitTest;

public class AdditionalTemplatePathExcludeTest {

    @RegisterExtension
    static final QuarkusUnitTest config = new QuarkusUnitTest()
            .setArchiveProducer(() -> ShrinkWrap.create(JavaArchive.class)
                    // excluded
                    .addAsResource(new StringAsset("{@java.util.List myList}{myList.bar}"), "templates/foo.txt")
                    // not excluded
                    .addAsResource(new StringAsset("{@java.util.List myList}{myList.size}"), "templates/_foo.txt"))
            .overrideConfigKey("quarkus.qute.template-path-exclude", "^\\..*|.*\\/\\..*$|foo.txt");

    @Inject
    Engine engine;

    @Test
    public void testDefaultExclude() {
        assertNull(engine.getTemplate("foo.txt"));
        assertEquals(engine.getTemplate("_foo.txt").data("myList", List.of(1)).render(), "1");
    }

}
