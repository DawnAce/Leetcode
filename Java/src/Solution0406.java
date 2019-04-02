import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 假设有打乱顺序的一群人站成一个队列。 每个人由一个整数对(h, k)表示，
 * 其中h是这个人的身高，k是排在这个人前面且身高大于或等于h的人数。 编写一个算法来重建这个队列。
 *
 * 注意：
 * 总人数少于1100人。
 */
public class Solution0406 {
    //核心思路是，高个肯定不会被小个影响排位，所以如果从高个开始排列，小个只需要插到应该插到的地方即可，后面的更小个不会影响到前面的
    //这里需要注意的一点是，个子相同时，次序更小的应该排在前面
    //时间复杂度为O(n*n)，因为排序需要O(log(2,n)),插入到合适位置时可能需要移动n个元素(每次插在最前或最后，根据使用Linked还是Array不同而不同)
    public int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people, (o0, o1) -> {
            if(o0[0] == o1[0])
                return o0[1]-o1[1];
            return o1[0] - o0[0];
        });

        List<int[]> temp = new LinkedList<>();
        for(int[] man : people)
            temp.add(man[1], man);
        return temp.toArray(new int[people.length][]);
//        int[][] result = new int[people.length][2];
//        int index = 0;
//        for(int[] man : temp)
//            result[index++] = man;
//        return result;
    }

    public static void main(String[] args) {
        int[][] people =
                {{7,0}, {4,4}, {7,1}, {5,0}, {6,1}, {5,2}};
        int[][] result = new Solution0406().reconstructQueue(people);
        Arrays.stream(result).forEach(x -> System.out.println(Arrays.toString(x)));
    }
}
