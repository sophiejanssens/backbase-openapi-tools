package com.backbase.oss.boat;

import java.io.File;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.plugin.MojoExecutionException;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
@SuppressWarnings("java:S2699")
public class DiffMojoTests {

    @SneakyThrows
    @Test
    public void testNonBreakingChange() {
        DiffMojo diffMojo = new DiffMojo();
        diffMojo.setOldFile(getFile("/oas-examples/petstore.yaml"));
        diffMojo.setNewFile(getFile("/oas-examples/petstore-new-non-breaking.yaml"));
        diffMojo.setWriteChangelog(true);
        diffMojo.setChangelogOutput(new File("target"));
        diffMojo.setChangelogRenderer("markdown");
        diffMojo.execute();

        Assert.assertTrue(new File(diffMojo.getChangelogOutput(), "changelog.md").exists());
    }

    @SneakyThrows
    @Test
    public void testBreakingChange() {
        DiffMojo diffMojo = new DiffMojo();
        diffMojo.setOldFile(getFile("/oas-examples/petstore.yaml"));
        diffMojo.setNewFile(getFile("/oas-examples/petstore-new-breaking.yaml"));
        diffMojo.setWriteChangelog(true);
        diffMojo.setChangelogOutput(new File("target"));
        diffMojo.setChangelogRenderer("html");
        diffMojo.execute();
        Assert.assertTrue(new File(diffMojo.getChangelogOutput(), "changelog.html").exists());
    }

    @Test(expected = MojoExecutionException.class)
    public void testBreakingChangeWithBreaking() throws MojoExecutionException {
        DiffMojo diffMojo = new DiffMojo();
        diffMojo.setOldFile(getFile("/oas-examples/petstore.yaml"));
        diffMojo.setNewFile(getFile("/oas-examples/petstore-new-breaking.yaml"));
        diffMojo.setBreakOnBreakingChanges(true);
        diffMojo.execute();
    }


    private File getFile(String fileName) {
        return new File(getClass().getResource(fileName).getFile());
    }
}
