import java.util.Stack;

/**
 * 序列化是将数据结构或对象转换为一系列位的过程，以便它可以存储在文件或内存缓冲区中，
 * 或通过网络连接链路传输，以便稍后在同一个或另一个计算机环境中重建。
 *
 * 设计一个算法来序列化和反序列化二叉搜索树。 对序列化/反序列化算法的工作方式没有限制。
 * 您只需确保二叉搜索树可以序列化为字符串，并且可以将该字符串反序列化为最初的二叉搜索树。
 *
 * 编码的字符串应尽可能紧凑。
 *
 * 注意：不要使用类成员/全局/静态变量来存储状态。 你的序列化和反序列化算法应该是无状态的。
 */
public class Solution0449 {
    static public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    // 这是答案中最简单的方法，太牛逼了！！！
    public String serialize(TreeNode root) {
        if(root == null){
            return "#";
        }
        return String.valueOf(root.val) + "," + serialize(root.left) + "," + serialize(root.right);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[]strs = data.split(",");
        return deserialize(strs, new int[]{0});
    }
    private TreeNode deserialize(String[]arr, int[]idx){
        if(arr[idx[0]].equals("#")){
            idx[0]++;
            return null;
        }
        TreeNode root = new TreeNode(Integer.parseInt(arr[idx[0]++]));
        root.left = deserialize(arr, idx);
        root.right = deserialize(arr, idx);
        return root;
    }

    // 下面的方法利用了二叉搜索树左子树不大于根节点，右子树不小于根节点的性质
    // 使用左右区间的方法确定前序遍历结果中目前符合要求的点
    public String serialize0(TreeNode root) {
        if(root == null) return null;
        StringBuilder sb = new StringBuilder();
        preHelper(root, sb);
        return sb.substring(0, sb.length()-1);
    }

    private void preHelper(TreeNode root, StringBuilder sb){
        if(root == null) return;
        sb.append(root.val + ",");
        preHelper(root.left, sb);
        preHelper(root.right, sb);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize0(String data) {
        if(data==null || data.length()==0) return null;
        int[] indexRecord = {0};
        String[] arrays = data.split(",");
        int[] nums = new int[arrays.length];
        for(int i=0; i<arrays.length; ++i)
            nums[i] = Integer.parseInt(arrays[i]);
        return rebuildTree(nums, indexRecord, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private TreeNode rebuildTree(int[] nums, int[] indexRecord, int min, int max){
        if(indexRecord[0]>=nums.length || nums[indexRecord[0]]<min
                || nums[indexRecord[0]]>max) return null;
        int value = nums[indexRecord[0]++];
        TreeNode root = new TreeNode(value);
        root.left = rebuildTree(nums, indexRecord, min, value);
        root.right = rebuildTree(nums, indexRecord, value, max);
        return root;
    }

    // 以下的方法可以针对于任意的二叉树，但是题目明确了，是二叉搜索树，所以可以更一步利用其性质
    public String serialize1(TreeNode root) {
        if(root == null) return "";
        StringBuilder result = new StringBuilder();
        preOrder(root, result);
        result.setCharAt(result.length()-1, '#');
        inOrder(root, result);
        return result.substring(0, result.length()-1);
    }

    private void preOrder(TreeNode root, StringBuilder result){
        if(root == null) return;
        result.append(root.val + ",");
        preOrder(root.left, result);
        preOrder(root.right, result);
    }

    private void inOrder(TreeNode root, StringBuilder result){
        if(root == null) return;
        Stack<TreeNode> stack = new Stack<>();
        while(!stack.isEmpty() || root!=null){
            while(root != null){
                stack.push(root);
                root = root.left;
            }

            root = stack.pop();
            result.append(root.val + ",");
            root = root.right;
        }
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize1(String data) {
        if(data==null || data.length()==0) return null;
        String[] temp = data.split("#"), preStr = temp[0].split(","),
                inStr = temp[1].split(",");
        return generateTree(preStr, inStr, 0, preStr.length-1,
                0, inStr.length-1);
    }

    private TreeNode generateTree(String[] pre, String[] in, int start0,
                                  int end0, int start1, int end1){
        if(end0 < start0) return null;
        int rootIndex = start1;
        while(rootIndex<=end1 && !in[rootIndex].equals(pre[start0]))
            ++rootIndex;
        TreeNode root = new TreeNode(Integer.parseInt(pre[start0]));
        int leftSize = rootIndex-start1;
        root.left = generateTree(pre, in, start0+1,
                start0+leftSize, start1, rootIndex-1);
        root.right = generateTree(pre, in, start0+leftSize+1,
                end0, rootIndex+1, end1);
        return root;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(1);
        root.right = new TreeNode(3);
        String result = new Solution0449().serialize(root);
        TreeNode temp = new Solution0449().deserialize(result);
    }
}
