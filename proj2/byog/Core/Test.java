package byog.Core;

public class Test {
    public final String s;

    public Test() {
        s = "This is a test.";
    }

    public static void main(String[] args) {
        Test t = new Test();
        System.out.println(t.s);
    }
}
