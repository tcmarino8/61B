package arrays;

/* NOTE: The file Arrays/Utils.java contains some functions that may be useful
 * in testing your answers. */

/** HW #2 */

/** Array utilities.
 *  @author
 */
class Arrays {

    /* C1. */
    /** Returns a new array consisting of the elements of A followed by the
     *  the elements of B. */
    static int[] catenate(int[] A, int[] B) {
        if (A == null){
            return B;
        }
        if (B == null){
            return A;
        }
        int[] C = new int[A.length + B.length];
        int j = 0;
        while (j != A.length) {
            C[j] = A[j];
            j += 1;
        }
        for (int i = 0; i < B.length; i++){
            C[i+j] = B[i];

        }

            return C;
        }
        //FIXME: Replace this body with the solution.


    /* C2. */
    /** Returns the array formed by removing LEN items from A,
     *  beginning with item #START. If the start + len is out of bounds for our array, you
     *  can return null.
     *  Example: if A is [0, 1, 2, 3] and start is 1 and len is 2, the
     *  result should be [0, 3]. */
    static int[] remove(int[] A, int start, int len) {
        if (A.length == 0) {
            return null;
        }
        if (start + len > A.length){
            return null;
        }
        int[] B = new int[(A.length-len)];
        int j = 0;
        for (int i = 0; i < A.length; i++) {
            if (start <= i && i <= start + len - 1) {
                continue;
            }
            else {
                B[j] = A[i];
                j++;
            }
        }
        return B;
        }

        // FIXME: Replace this body with the solution.
    }

