/** Multidimensional array
 *  @author Zoe Plaxco
 */

public class MultiArr {

    /**
    {{"hello","you","world"} ,{"how","are","you"}} prints:
    Rows: 2
    Columns: 3

    {{1,3,4},{1},{5,6,7,8},{7,9}} prints:
    Rows: 4
    Columns: 4
    */
    /*public static void rowAndColValues(int[][] arr){
        int numRows = arr.length;
        int[] columnLen = null;
        while (arr.length != null){
            if (arr[0].length == null) {
                arr = Arrays.copyOfRange(arr, 1, arr.length);
            }
            else {
                columnLen + [arr[0].length];
                arr = Arrays.copyOfRange(arr, 1, arr.length);


            }

        }
        return ([numRows, Arrays.stream(columnLen).max().getAsInt()])
    }
    public static void printRowAndCol(int[][] arr) {
        int numRows = arr.length;
        int[] columnLen = [];
        while (arr.length != null){
            if (arr[0].length == null) {
                 arr = Arrays.copyOfRange(arr, 1, arr.length)
            }
            else {
                columnLen + [arr[0].length];
                arr = Arrays.copyOfRange(arr, 1, arr.length)


            }

        }
        String rowsColumns =
                String.format("Row: %f Columns: %f", numRows, Arrays.stream(columnLen).max().getAsInt()
                )
        System.out.println();
    }

        //TODO: Your code here!


    /**
    @param arr: 2d array
    @return maximal value present anywhere in the 2d array
    */
    public static int maxValue(int[][] arr) {
        if (arr.length == 0){
            return 0;
        }
        int max = arr[0][0];
        for (int i = 0; i < arr.length; i++){
            for(int j = 0; j <arr[i].length; j++){
                if (arr[i][j] > max){
                    max = arr[i][j];
                }
            }
        }
        return max;
    }

    /**Return an array where each element is the sum of the
    corresponding row of the 2d array*/
    public static int[] allRowSums(int[][] arr) {
        int[] rowSums = new int[arr.length];
        for (int i = 0; i <= arr.length-1; i++) {
            int sum = 0;
            for (int j = 0; j < arr[i].length; j++) {
                sum += arr[i][j];
            }
            rowSums[i] = sum;
                }
        return rowSums;
            }






}
