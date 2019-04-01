public class Solution0404 {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    //其实变量就两个，左和右，而这个状态父节点是由足够信息的，所以可以由父节点传递下去，由子节点接受并作回应
    public int sumOfLeftLeaves(TreeNode root) {
        return sum(root, false);
    }

    private int sum(TreeNode root, boolean isLeft){
        if (root == null) return 0;
        if (root.left == null && root.right == null && isLeft){
            return root.val;
        }

        return sum(root.left, true) + sum(root.right, false);
    }

    //核心思想是，判断叶子节点是左节点还是右节点(代码太烂！)
    public int sumOfLeftLeaves0(TreeNode root) {
        if(root==null || root.right==null&&root.left==null)
            return 0;
        return helper(root);
    }

    private int helper(TreeNode root){
        if(root == null) return 0;
        //叶子节点
        if(root.left==null && root.right==null) return root.val;
        //如果右孩子为叶子节点，则只返回左侧结果
        if(root.right!=null && root.right.left==null
                && root.right.right==null)
            return helper(root.left);
        else return helper(root.left) + helper(root.right);
    }
}
