package search;

public class BinarySearchSpan {

    //Pre: a.length > 0 && (a[0] <= x <= a[a.length - 1]) && (forall i=1..a.length-1: a[i - 1] <= a[i])
    //Post: R: forall i=R+1..a.length-1: x < a[i] && forall j=0..R: a[j] <= x
    public static int binarySearch(int x, int[] a) {
        // a.length > 0 && (a[0] <= x <= a[a.length-1]) && (forall i=1..a.length-1: a[i - 1] <= a[i])
        int l = 0, r = a.length;
        // a.length > 0 && (a[0] <= x <= a[a.length-1]) && (forall i=1..a.length-1: a[i - 1] <= a[i]) && l = 0 && r = a.length
        // a[l] <= x < a[r] && r - l > r`- l`
        while (r - l > 1) {
            //a[l] <= x < a[r] && r - l > 1 && (forall i=1..a.length-1: a[i - 1] <= a[i])
            int middle = l + (r - l) / 2;
            //a[l] <= x && l < middle < r && (forall i=1..a.length-1: a[i - 1] <= a[i])
            //a[l] <= a[middle] <= a[r]
            if (a[middle] > x) {
                //a[l] <= x < a[middle] <= a[r] && l < middle < r
                r = middle;
                // r` = middle, l` = l -> a[l`] <= x < a[r`] && r` - l` = middle - l < r - l
            } else {
                //a[l] <= a[middle] <= x <= a[l] && l < middle < r
                l = middle;
                // l` = middle, r` = r -> a[l`] <= x < a[r`] && r` - l` = r - middle < r - l
            }
            //a[l`] <= x < a[r`] && r` - l` < r - l
        }
        // a[l] <= x < a[r] && r - l <= 1
        // r - l == 1 -> a[l] <= x < a[l + 1] -> R = l
        // r - l < 1 -> a[l] <= x < a[l - i], i = 0,1,..l && (forall i=1..a.length-1: a[i - 1] <= a[i]) -> противоречие
        return l;
    }

    // Pre: a.length > 0 && (forall i=1..a.length-1: a[i - 1] <= a[i]) && x < a[a.length-1]
    // Post: R1, R2: (forall j=0..R1-1: a[j] < a[R1]) && (forall j=R1..R2-1: a[j] = a[R1]) && (forall j=R2..a.length-1: a[j] > a[R1])
    public static void binarySpan(int x, int[] a) {
        // a.length > 0 && (forall i=1..a.length-1: a[i - 1] <= a[i]) && x < a[a.length-1] -> exists R1, R2: a[i]<a[j]=a[R1]<a[k], i=0..R1-1, j=R1..R2-1, k=R2..a.length-1
        int l, len;
        if (x < a[0]) {
            //a.length > 0 && (forall i=1..a.length-1: a[i - 1] <= a[i]) && x <= a[a.length-1] && x < a[0] -> forall i=0..a.length-1: x<a[i] -> R1 = 0, R2 = 0
            l = 0;
            //R1 = l
            len = 0;
            //R2 = len
        } else {
            //a.length > 0 && (forall i=1..a.length-1: a[i - 1] <= a[i]) && x <= a[a.length-1] && a[0] <= x
            if (x - 1 >= a[0]) {
                //a.length > 0 && (forall i=1..a.length-1: a[i - 1] <= a[i]) && a[0] <= x <= a[a.length-1] && (x - 1) >= a[0]
                l = binarySearch(x - 1, a);
                // forall i=l+1..a.length-1: (x - 1) < a[i] && forall j=0..l: a[j] <= (x - 1)
                l++;
                // forall i=l+1..a.length-1: x <= a[i] && forall j=0..l: a[j] < x
            } else {
                //a.length > 0 && (forall i=1..a.length-1: a[i - 1] <= a[i]) && a[0] <= x <= a[a.length-1] && (x - 1) < a[0] -> x = a[0]
                l = 0;
                // forall i=l+1..a.length-1: x <= a[i] && forall j=0..l: a[j] < x
            }
            // a.length > 0 && (forall i=1..a.length-1: a[i - 1] <= a[i]) && x <= a[a.length-1] && a[0] <= x
            // && forall i=l+1..a.length-1: x <= a[i] && forall j=0..l: a[j] < x
            len = binarySearch(x, a) - l + 1;
            // let r = binarySearch(x, a). Then:
            // forall i=r+1..a.length-1: x < a[i] && forall j=0..r: a[j] <= x
            // Then: forall i = l..r x == a[i] -> len == r - l + 1
        }
        // (forall j=0..R1-1: a[j] < a[R1]) && (forall j=R1..R2-1: a[j] = a[R1]) && (forall j=R2..a.length-1: a[j] > a[R1])
        System.out.println(l + " " + len);
    }


    // Pre: (forall i=1..a.length-1: a[i - 1] <= a[i])
    // Post: (a.length == 0) || (exists R1, R2: (forall j=0..R1-1: a[j] < a[R1]) && (forall j=R1..R2-1: a[j] = a[R1]) && (forall j=R2..a.length-1: a[j] > a[R1]))

    public static void main(String[] args) {
        int[] a = new int[args.length - 1];
        int x = Integer.parseInt(args[0]);
        for (int i = 0; i < a.length; i++) {
            a[i] = Integer.parseInt(args[i + 1]);
        }
        if (a.length == 0) {
            System.out.println("0 0");
        } else if (a[a.length - 1] < x) {
            // a.length > 0 && (forall i=1..a.length-1: a[i - 1] <= a[i]) && a[a.length-1] < x -> (forall i=0..a.length-1: a[i] < x)
            System.out.println(a.length + " 0");
        } else {
            binarySpan(x, a);
        }
    }
}
