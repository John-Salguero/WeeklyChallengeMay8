import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Vector;

public class main {

    private static int aToI (String aNum){
        int num = 0;
        int multiplier = 1;
        for(byte c : aNum.getBytes()) {
            num *= 10;
            if(num == 0 && c == '-' && multiplier == 1)
                multiplier *= -1;
            else if(c >= '0' && c <= '9')
                num += (c - '0') * multiplier;
            else throw new IllegalArgumentException("String is not purely a number");
        }

        return num;
    }

    private static Vector<List<Integer>> getLists(String arg) {
        Vector<List<Integer>> lists = new Vector<>();

        int i = 0;
        if(arg.charAt(i++) == '[') {
            while(i < arg.length() && arg.charAt(i++) == '[') {
                List<Integer> list = new ArrayList<>();
                while(arg.charAt(i) != ']' && arg.charAt(i) != ',') {
                    String num = "";
                    while (arg.charAt(i) != ',' && arg.charAt(i) != ']') {
                        num += arg.charAt(i++);
                    }
                    ++i;
                    list.add(aToI(num));
                }
                ++i;
                if(list.isEmpty())
                    ++i;
                lists.add(list);
            }
        }

        if(arg.charAt(i-1) == ']')
            return lists;
        else throw new IllegalArgumentException("Argument is not a well-formed definition of lists");

    }

    private static void printList(List<Integer> list) {
        if(list.isEmpty())
            System.out.println("List:\n[]");
        else {
            Iterator<Integer> it = list.iterator();
            System.out.format("List:\n[%d", it.next());
            while (it.hasNext())
                System.out.format(",%d", it.next());
            System.out.println("]");
        }
    }

    private static List<Integer> getCombinedList(Vector<List<Integer>> lists) {
        List<Integer> combined = new ArrayList<>();
        PriorityQueue<Node> minQueue = new PriorityQueue<>();

        // Add the first element of each list to the queue
        // we will keep track of the elements' indices as we crawl through the lists
        for (int i = 0; i < lists.size(); i++) {
            List<Integer> list = lists.get(i);
            if (!list.isEmpty()) {
                int val = list.get(0);
                minQueue.offer(new Node(val, i, 0));
            }
        }

        // Continuously extract the minimum element from the heap
        // and add the next element of the corresponding list to the heap
        while (!minQueue.isEmpty()) {
            Node node = minQueue.poll();
            combined.add(node.val);

            int listIndex = node.listIndex;
            int nextIndex = node.index + 1;
            List<Integer> list = lists.get(listIndex);
            if (nextIndex < list.size()) {
                int nextVal = list.get(nextIndex);
                minQueue.offer(new Node(nextVal, listIndex, nextIndex));
            }
        }

        return combined;
    }

    public static void main(String[] args) {

        // Get The Lists
        Vector<List<Integer>> lists = getLists(args[0]);
        // Print the Lists
        for(List<Integer> list : lists){
            printList(list);
        }
        // Get the Combined List
        List<Integer> answer = getCombinedList(lists);

        // Print The Combined List
        System.out.println("---Answer---");
        printList(answer);
    }

    private static class Node implements Comparable<Node> {
        int val; // the value from the list
        int listIndex; // the list index from the vector
        int index; // the value index from the list

        public Node(int val, int listIndex, int index) {
            this.val = val;
            this.listIndex = listIndex;
            this.index = index;
        }

        // Used for the priority queue to sort
        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.val, other.val);
        }
    }
}
