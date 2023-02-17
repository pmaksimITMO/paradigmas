package search;

public class BinarySearch {


    //Pre: a.length > 0 && (a[a.length-1] < x) && (forall i=1..a.length-1: a[i - 1] >= a[i])
    //Post: exists i=0..a.length-1: a[i] <= a[R] && (forall j=0..i-1: a[j] > a[R])
    //Inv: a[r] <= x < a[l]
    public static int binarySearchRecursive(int x, int[] a, int l, int r) {
        // a[r] <= x < a[l] && a.length > 0 && (a[a.length-1] < x) && (forall i=1..a.length-1: a[i - 1] >= a[i])
        if (r - l <= 1) {
            // a[r] <= x < a[l] && a.length > 0 && (a[a.length-1] < x) && (forall i=1..a.length-1: a[i - 1] >= a[i]) && r - l <= 1
            // r - l == 1 -> a[r] <= x < a[r - 1] -> R = r
            // r - l < 1 -> a[r] <= x < a[r + i], i = 0,1,..n-r && (forall i=1..a.length-1: a[i - 1] >= a[i]) -> противоречие
            return r;
        }
        // a[r] <= x < a[l] && r - l > 1 && (forall i=1..a.length-1: a[i - 1] >= a[i])
        int middle = l + (r - l) / 2;
        // a[r] <= x < a[l] && r - l > 1 && l < middle < r && (forall i=1..a.length-1: a[i - 1] >= a[i])
        if (a[middle] > x) {
            // a[r] <= x < a[middle] <= a[l] && r - l > 1 && l < middle < r && (forall i=1..a.length-1: a[i - 1] >= a[i])
            // l` = middle, r` = r -> r` - l` = r - middle < r - l && a[r`] <= x < a[l`]
            return binarySearchRecursive(x, a, middle, r);
            // forall i=0..R-1: a[i] > R && forall i=R..a.length - 1: a[i] <= R
        } else {
            // a[r] <= a[middle] <= x < a[l] && r - l > 1 && l < middle < r && (forall i=1..a.length-1: a[i - 1] >= a[i])
            // l` = l, r` = middle -> r` - l` = middle - l < r - l && a[r`] <= x < a[l`]
            return binarySearchRecursive(x, a, l, middle);
            // forall i=0..R-1: a[i] > R && forall i=R..a.length - 1: a[i] <= R
        }
    }

    //Pre: a.length > 0 && (a[a.length-1] <= x < a[0]) && (forall i=1..a.length-1: a[i - 1] >= a[i])
    //Post: exists i=0..a.length-1: a[i] <= a[R] && (forall j=0..i-1: a[j] > a[R])
    public static int binarySearchIterative(int x, int[] a) {
        // a.length > 0 && (a[a.length-1] <= x < a[0]) && (forall i=1..a.length-1: a[i - 1] >= a[i])
        int l = 0, r = a.length - 1;
        // a.length > 0 && (a[a.length-1] <= x < a[0]) && (forall i=1..a.length-1: a[i - 1] >= a[i]) && l = 0 && r = a.length - 1
        // a[r] <= x < a[l] && r - l > r`- l`
        while (r - l > 1) {
            //a[r] <= x < a[l] && r - l > 1 && (forall i=1..a.length-1: a[i - 1] >= a[i])
            int middle = l + (r - l) / 2;
            //a[r] <= x && l < middle < r && (forall i=1..a.length-1: a[i - 1] >= a[i])
            //a[r] <= a[middle] <= a[l]
            if (a[middle] > x) {
                //a[r] <= x < a[middle] <= a[l] && l < middle < r
                l = middle;
                // l` = middle, r` = r -> a[r`] <= x < a[l`] && r` - l` = r - middle < r - l
            } else {
                //a[r] <= x < a[middle] <= a[l] && l < middle < r
                r = middle;
                // l` = l, r` = middle -> a[r`] <= x < a[l`] && r` - l` = middle - l < r - l
            }
            //a[r`] <= x < a[l`] && r` - l` < r - l
        }
        // a[r] <= x < a[l] && r - l <= 1
        // r - l == 1 -> a[r] <= x < a[r - 1] -> R = r
        // r - l < 1 -> a[r] <= x < a[r + i], i = 0,1,..n-r && (forall i=1..a.length-1: a[i - 1] >= a[i]) -> противоречие
        return r;
    }

    // Pre: (forall i=1..a.length-1: a[i - 1] >= a[i])
    // Post: (a.length == 0) || (a[a.length-1] > x) || (exists i=0..a.length-1: a[i] <= a[R] && (forall j=0..i-1: a[j] > a[R]))
    public static void main(String[] args) {
        int[] a = new int[args.length - 1];
        int x = Integer.parseInt(args[0]);
        int sum = 0;
        for (int i = 0; i < a.length; i++) {
            a[i] = Integer.parseInt(args[i + 1]);
            sum += a[i];
        }
        // (forall i=1..a.length-1: a[i - 1] >= a[i])
        if (a.length == 0) {
            //a.length = 0
            System.out.println(0);
        } else if (a[0] <= x) {
            // a.length > 0 && a[0] <= x && (forall i=1..a.length-1: a[i - 1] >= a[i]) -> (forall i=0..a.length-1: a[i] <= x) -> R = 0
            System.out.println(0);
        } else if (a[a.length - 1] > x) {
            // a.length > 0 && a[a.length - 1] > x && (forall i=1..a.length-1: a[i - 1] > a[i]) -> (forall i=0..a.length-1: a[i] > x) -> R = a.length
            System.out.println(a.length);
        } else {
            // a.length > 0 && a[a.length - 1] <= x < a[0] && (forall i=1..a.length-1: a[i - 1] >= a[i])
            if (sum % 2 == 0) {
                // a.length > 0 && a[a.length - 1] <= x < a[0] && (forall i=1..a.length-1: a[i - 1] >= a[i]) && sum % 2 == 0
                System.out.println(binarySearchRecursive(x, a, 0, a.length - 1));
                // (exists i=0..a.length-1: a[i] <= a[R] && (forall j=0..i-1: a[j] > a[R]))
            } else {
                // a.length > 0 && a[a.length - 1] <= x < a[0] && (forall i=1..a.length-1: a[i - 1] >= a[i]) && sum % 2 == 1
                System.out.println(binarySearchIterative(x, a));
                // (exists i=0..a.length-1: a[i] <= a[R] && (forall j=0..i-1: a[j] > a[R]))
            }
        }
        //(a.length == 0) || (a[a.length-1] > x) || (exists i=0..a.length-1: a[i] <= a[R] && (forall j=0..i-1: a[j] > a[R]))
    }
}
