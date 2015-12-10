package ru.d10xa.testio;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Scanner;

public class FileSystemInTest {

    @Test
    public void system_in_from_file() throws Exception {
        InvocationResult result = Testio.invocation(new Task() {
            @Override
            public void run() throws Exception {
                FileSystemInTest.print();
            }
        }).setIn(new File("src/test/resources/testfile.txt"))
            .invoke();

        Assert.assertEquals(Testio.join("1", "2 3", "abc"), result.getOutString());
    }

    private static void print() {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            System.out.println(sc.nextLine());
        }
    }

}
