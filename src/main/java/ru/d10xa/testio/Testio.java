package ru.d10xa.testio;

public final class Testio {

    private Testio() {
    }

    public static Invocation invocation(Task task){
        return new Invocation(task);
    }

    public static String join(String... strings) {
        if(strings == null){
            throw new NullPointerException("join(String...)");
        }
        if (strings.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String s : strings) {
            sb.append(s);
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

}
