import java.util.*;

/**
 * 给定一组区间，对于每一个区间 i，检查是否存在一个区间 j，它的起始点大于或等于区间 i 的终点，
 * 这可以称为 j 在 i 的“右侧”。
 *
 * 对于任何区间，你需要存储的满足条件的区间 j 的最小索引，
 * 这意味着区间 j 有最小的起始点可以使其成为“右侧”区间。
 * 如果区间 j 不存在，则将区间 i 存储为 -1。最后，你需要输出一个值为存储的区间值的数组。
 *
 * 注意:
 *
 * 你可以假设区间的终点总是大于它的起始点。
 * 你可以假定这些区间都不具有相同的起始点。
 */
public class Solution0436 {
    public class Interval {
        int start;
        int end;
        Interval() { start = 0; end = 0; }
        Interval(int s, int e) { start = s; end = e; }
    }

    //桶排序
    public int[] findRightInterval(Interval[] intervals) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        for (int i=0; i < intervals.length; i++){
            max = Math.max(max, intervals[i].end);
            min = Math.min(min, intervals[i].start);
        }

        int len = max - min + 1;
        int[] bucket = new int[len];
        Arrays.fill(bucket, -1);

        for (int i=0; i<intervals.length; i++){
            bucket[intervals[i].start - min] = i;
        }

        for (int j=len-2; j>=0; j--) {
            if (bucket[j] == -1) {
                bucket[j] = bucket[j+1];
            }
        }

        int[] res = new int[intervals.length];
        for (int i=0; i<intervals.length; i++){
            res[i] = bucket[intervals[i].end - min];
        }
        return res;
    }

    //一般使用桶排序记录区间状态的，使用TreeMap也可以
    public int[] findRightInterval0(Interval[] intervals){
        int[] result = new int[intervals.length];
        TreeMap<Integer, Integer> intervalMap = new TreeMap();

        for (int i=0; i<intervals.length; i++){
            intervalMap.put(intervals[i].start, i);
        }

        for (int i=0; i<intervals.length; i++) {
            Map.Entry<Integer, Integer> entry = intervalMap.ceilingEntry(intervals[i].end);
            result[i] = (entry != null) ? entry.getValue() : -1;
        }
        return result;
    }

    //核心思路是，先按照start大小进行排序，然后从前向后记录下来所有出现的end值
    //如果当前的start大于等于已出现的end值，说明具有该end值的都已经找到答案了
    //时间复杂度为排序O(log(2,n))，查找时间复杂度为n*O(log(2,n))（最坏的情况为，最后一个才出现start大于前面的情况，需要递归查找n-1次）
    public int[] findRightInterval1(Interval[] intervals) {
        int[] result = new int[intervals.length];
        Arrays.fill(result, -1);
        Integer[] indexs = new Integer[intervals.length];
        for(int i=0; i<indexs.length; ++i)
            indexs[i] = i;
        Arrays.sort(indexs, (o0, o1) -> {
            if(intervals[o0].start != intervals[o1].start)
                return intervals[o0].start-intervals[o1].start;
            return intervals[o0].end - intervals[o1].end;
        });
        TreeMap<Integer, List<Integer>> map = new TreeMap<>();
        for(int index : indexs){
            int start = intervals[index].start, key;
            while(map.floorKey(start) != null){
                key = map.lowerKey(start);
                List<Integer> temp = map.get(key);
                for(int tempIndex : temp)
                    result[tempIndex] = index;
                map.remove(key);
            }
            map.computeIfAbsent(intervals[index].end, k -> new LinkedList<>()).add(index);
        }
        return result;
    }

    class MyClass{
        int num;
        int index;
        public MyClass(int num, int index){
            this.num = num;
            this.index = index;
        }
    }

    //这个解法的思路是，将序列按照start和end的顺序从小到大进行排列
    //然后以end数组为变量，寻找start大于end的最小值
    public int[] findRightInterval2(Interval[] intervals) {
        if(intervals.length == 1)
            return new int[]{-1};
        List<MyClass> startList = new ArrayList<>();
        List<MyClass> endList = new ArrayList<>();

        for(int i=0; i<intervals.length; ++i){
            startList.add(new MyClass(intervals[i].start,i));
            endList.add(new MyClass(intervals[i].end,i));
        }
        //应该要注意相等的情况，因为是从前向后比，所以相等时，小序号的在前面，刚好符合要求
        Collections.sort(startList,(o1,o2) -> {return o1.num-o2.num;});
        Collections.sort(endList,(o1,o2) -> {return o1.num-o2.num;});
        int[] result = new int[intervals.length];
        int startIndex = 0;
        for(MyClass temp : endList){
            int end = temp.num;
            while(startIndex<intervals.length && end > startList.get(startIndex).num)
                ++startIndex;
            if(startIndex == intervals.length)
                result[temp.index] = -1;
            else
                result[temp.index] = startList.get(startIndex).index;
        }
        return result;
    }
}
