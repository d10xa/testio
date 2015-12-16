package ru.d10xa.testio;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

public class OutputRuleTest {

    @Rule
    public Output out = new Output();

    @Test
    public void output_toString() {
        System.out.println("hello");
        System.err.println("world");
        Assert.assertEquals(String.format("hello%nworld%n"), out.toString());
    }

    @Test
    public void out_only() {
        System.out.println("hello");
        System.err.println("world");
        Assert.assertEquals(String.format("hello%n"), out.outToString());
    }

    @Test
    public void err_only() {
        System.out.println("hello");
        System.err.println("world");
        Assert.assertEquals(String.format("world%n"), out.errToString());
    }

    @Test
    public void multiple_asserts(){
        System.out.println("abc");
        Assert.assertEquals(String.format("abc%n"), out.toString());
        Assert.assertEquals(String.format("abc%n"), out.toString());
    }

}
