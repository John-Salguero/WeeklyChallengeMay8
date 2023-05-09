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

    private static void printArray(int[] array) {
        System.out.format("Array Size: %d\n", array.length);
        if(array.length > 0) {
            System.out.format("Contents: [%d", array[0]);
            for (int i = 1; i < array.length; ++i) {
                System.out.format(",%d", array[i]);
            }
            System.out.format("]\n");
        } else
            System.out.println("Contents: []");
    }

    private static int[] combineSorted(int[] arr0, int[] arr1){
        int [] arr2 = new int[arr0.length + arr1.length];
        int i = 0, j = 0;
        while(i + j < arr2.length) {
            if(j == arr1.length)
                arr2[i + j] = arr0[i++];
            else if(i == arr0.length)
                arr2[i + j] = arr1[j++];
            else if(arr0[i] < arr1[j])
                arr2[i + j] = arr0[i++];
            else
                arr2[i + j] = arr1[j++];
        }

        return arr2;
    }

    private static float getMedian(int[] array) {
        int halfSize = array.length/2;
        float median;
        if((array.length & 1) == 0) {
            median = (array[halfSize] + array[halfSize-1]) * .5f;
        } else {
            median = array[halfSize];
        }

        return median;
    }

    private static void printAnswer(int[] arr0, int[] arr1, int[] arr2, float median){
        printArray(arr0);
        printArray(arr1);
        System.out.println("---Combined---");
        printArray(arr2);
        System.out.format("Median: %f", median);
    }

    public static void main(String[] args) {

        // get first array
        int size0 = aToI(args[0]);
        int[] arr0 = new int[size0];
        int i;
        for(i = 0; i < size0; ){
            arr0[i] = aToI(args[++i]);
        }

        // get second array
        int size1 = aToI(args[++i]);
        int[] arr1 = new int[size1];
        ++i;
        for(int j = 0; j < size1; ++j){
            arr1[j] = aToI(args[i+j]);
        }

        // Combine them with O(n+m)
        int[] arr2 = combineSorted(arr0, arr1);

        // get median
        float median = getMedian(arr2);

        // Print answer
        printAnswer(arr0, arr1, arr2, median);
    }
}
