package ru.d10xa.testio;

import org.junit.Assert;
import org.junit.Test;

import java.util.Scanner;

public class TestioTest {

    static class Redirect {
        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            while (sc.hasNextLine())
                System.out.println(sc.nextLine());
        }
    }

    @Test
    public void testName() throws Exception {

        Invocation invocation = Testio.invocation(new Task() {
            @Override
            public void run() throws Exception {
                Redirect.main(new String[0]);
            }
        });

        InvocationResult invocationResult = invocation
                .setIn("a", "b")
                .invoke();

        Assert.assertEquals(
                Testio.join("a", "b"),
                invocationResult.getOutErrString());
    }
}
