import java.util.Arrays;

/**
 * 给定一个区间的集合，找到需要移除区间的最小数量，使剩余区间互不重叠。
 *
 * 注意:
 *
 * 可以认为区间的终点总是大于它的起点。
 * 区间 [1,2] 和 [2,3] 的边界相互“接触”，但没有相互重叠。
 */
public class Solution0435 {
    public class Interval {
        int start;
        int end;
        Interval() { start = 0; end = 0; }
        Interval(int s, int e) { start = s; end = e; }
    }

    //如果按照end排序的话，其实更好，因为可以很清楚的知道，我该留下来哪些
    //如果按照start排序的话，我要想知道留下哪些，可能还需要一个辅助状态变量来记录
    //而且从代码上来看，按end进行排序的代码精简太多！
    public int eraseOverlapIntervals(Interval[] intervals) {
        if(intervals==null || intervals.length==0) return 0;
        Arrays.sort(intervals, (o1, o2) -> o1.end-o2.end);
        int count = 0, end = Integer.MIN_VALUE;
        for(Interval in : intervals){
            if(in.start >= end) {
                end = in.end;
                ++count;
            }
        }
        return intervals.length-count;
    }

    //核心思路是，按照start从小到大进行排列，然后记录目前最小的右边界
    //如果下一个值与右边界有交叉，说明需要删除一个区间，至于删除哪个区间，可以使用贪婪，删除右区间较大的
    public int eraseOverlapIntervals0(Interval[] intervals) {
        if(intervals==null || intervals.length==0) return 0;
        Arrays.sort(intervals, (o0,o1) -> o0.start-o1.start);
        int result = 0, pre = Integer.MIN_VALUE;
        for(Interval in : intervals){
            if(pre <= in.start) pre = in.end;
            else{
                ++result;       //我只知道要删除的区间右边界，具体是哪个区间我不关心
                pre = Math.min(pre, in.end);    //更新右边界
            }
        }
        return result;
    }
}
