package structure;

public class Test {

    public static void main(String[] args) {
        testList();
    }

    public static void testList() {

        List list = new ArrayList(new Object[]{"A", "B", "c"});
        System.out.println(list);

        list.add("E");
        System.out.println("add(\"E\")");
        System.out.println(list);

        list.add("D", 3);
        System.out.println("add(\"D\", 3)");
        System.out.println(list);

        System.out.println("remove(4)");
        System.out.println(list.remove(4));
        System.out.println(list);

        System.out.println("set(\"C\", 2)");
        System.out.println(list.set("C", 2));
        System.out.println(list);

        System.out.println("indexOf(\"B\")");
        System.out.println(list.indexOf("B"));
        System.out.println(list);

        System.out.println("lastIndexOf(\"Z\")");
        System.out.println(list.lastIndexOf("Z"));
        System.out.println(list);

    }

    public static void testQueue() {
        System.out.println("Test queue:");

        Queue queue = new ArrayQueue(new Object[]{"A", "B", "C"});

        System.out.println(queue);

        String obj = "D";
        System.out.println(obj + " is being enqueued");
        queue.enqueue(obj);

        System.out.println(queue.peek() + " is ready to be enqueued");
        System.out.println(queue);
        System.out.println(queue.dequeue() + " is enqueued");
        System.out.println(queue);
    }
}
