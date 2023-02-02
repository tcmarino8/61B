import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/** A set of String values.
 *  @author tyler Marino
 */
class ECHashStringSet implements StringSet {

    private LinkedList<String>[] buckets;
    private LinkedList<String>[] resized;
    private int default_num_buckets = 4;

    public ECHashStringSet() {
        buckets = (LinkedList<String>[]) new LinkedList[default_num_buckets];
        lngth = buckets.length;
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new LinkedList<>();
        }
        reSize = 0;
        loadFactorMax = 1;
        loadFactorMin = .2;
    }

    @Override
    public void put(String s) {
        int code = s.hashCode() & 0x7fffffff;
        int bucketNum = code % buckets.length;
        if (reSize > buckets.length*loadFactorMax) {
            resize();
            int reCode = s.hashCode() & 0x7fffffff;
            int reBucketNum = reCode % buckets.length;
            LinkedList toAppend = buckets[reBucketNum];
            toAppend.add(s);
        } else {
            LinkedList toAppend = buckets[bucketNum];
            toAppend.add(s);
        }
        reSize++;

        // FIXME
    }
    private void resize() {

        ArrayList<String> holder = (ArrayList) asList();
        //ECHashStringSet resized = new ECHashStringSet(buckets.length*2);
        resized = (LinkedList<String>[]) new LinkedList[buckets.length*2];
        for (int i = 0; i < resized.length; i++) {
            resized[i] = new LinkedList<>();
        }
        for (int i = 0; i < holder.size(); i++) {
            //resized.put(holder.get(i));
            int reCode = holder.get(i).hashCode() & 0x7fffffff;
            int reBucketNum = reCode % resized.length;
            LinkedList toAppend = resized[reBucketNum];
            toAppend.add(holder.get(i));
        }
        buckets = resized;

    }

    @Override
    public boolean contains(String s) {
        int code = s.hashCode() & 0x7fffffff;
        int bucketNum = code % buckets.length;
        LinkedList toCheck = buckets[bucketNum];
        return toCheck.contains(s);
        // FIXME
    }

    @Override
    public List<String> asList() {
        ArrayList list = new ArrayList<>();
        for (int i = 0; i < buckets.length; i++) {
            LinkedList curBucket = buckets[i];
            while (!curBucket.isEmpty()) {
                list.add(curBucket.getFirst());
                curBucket.removeFirst();
            }
        }
        return list; // FIXME
    }
    private int lngth;
    private int reSize;
    private int loadFactorMax;
    private double loadFactorMin;
}
