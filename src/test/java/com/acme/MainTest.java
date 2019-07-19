package com.acme;

import org.eclipse.jetty.server.Server;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.apache.commons.io.FileUtils.copyURLToFile;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MainTest {

    private static final String FILE_URL = "http://localhost:8080/file10G.txt";
    private static final int CONNECTION_TIMEOUT = 1000 * 60 * 60;
    private static final int READ_TIMEOUT = 1000 * 60 * 60;
    private static Main main;
    private static Server server;

    @BeforeClass
    public static void setUp() throws Exception {
        main = new Main();
        Runnable runnable = new Runnable()
        {
            public void run()
            {
                try {
                    server = main.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(runnable).start();
        Thread.sleep(3000);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        if(server!=null) server.stop();
    }

    @Test
    public void shouldDownloadTenGigsFile() throws Exception {
        File tempFile = createTempFile();
        copyURLToFile(
                new URL(FILE_URL),
                tempFile,
                CONNECTION_TIMEOUT,
                READ_TIMEOUT);

        long expectedLength = 10485760000L;
        assertThat(tempFile.length(), is(equalTo(expectedLength)));
    }

    private File createTempFile() throws IOException {
        return File.createTempFile("test-file", null);
    }
}