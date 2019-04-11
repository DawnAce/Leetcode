/**
 * 给定一个二叉搜索树的根节点 root 和一个值 key，删除二叉搜索树中的 key 对应的节点，并保证二叉搜索树的性质不变。返回二叉搜索树（有可能被更新）的根节点的引用。
 *
 * 一般来说，删除节点可分为两个步骤：
 *
 * 首先找到需要删除的节点；
 * 如果找到了，删除它。
 * 说明： 要求算法时间复杂度为 O(h)，h 为树的高度。
 */
public class Solution0450 {
    static public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    //如果二叉搜索树中可以存在相同元素，那么这个方法能够解决
    public TreeNode deleteNode(TreeNode root, int key) {
        if(root == null) return null;
        if(root.val < key)
            root.right = deleteNode(root.right, key);
        else if(root.val > key)
            root.left = deleteNode(root.left, key);
        else{
            if(root.left == null)
                return root.right;
            else if(root.right == null)
                return root.left;
            TreeNode maxNode = findMax(root.left);
            if(maxNode == null) {
                //目前左孩子是左子树中最大的，说明左孩子右子树为空，那么只需要将右子树接到左孩子上就可以了
                //然后由于二叉搜索树的性质，有可能根节点的左右子树中有与根节点相同的值，所以需要递归删除
                root.left.right = root.right;
                TreeNode holder = root.left;
                root.left = null;
                root.right = null;
                return deleteNode(holder, key);
            }else {
                root.val = maxNode.val;
                return deleteNode(root, key);
            }
        }
        return root;
    }

    private TreeNode findMax(TreeNode node){
        TreeNode pre = null;
        while(node.right != null){
            pre = node;
            node = node.right;
        }
        //pre为null的话，说明根节点就是最大的,需要告诉调用者
        if(pre == null)
            return null;
        //如果左子树不为空，那么需要将左子树链接到父节点上
        if(node.left != null) {
            pre.right = node.left;
            node.left = null;
        }else pre.right = null;
        return node;
    }

    //如果不允许有相同元素，那么这个方法非常好
    public TreeNode deleteNode0(TreeNode root, int key) {
        if(root == null) return null;

        if(root.val > key) {
            root.left = deleteNode0(root.left, key);
        } else if(root.val < key) {
            root.right = deleteNode0(root.right, key);
        } else {
            if(root.left == null) {
                return root.right;
            } else if(root.right == null) {
                return root.left;
            }

            TreeNode minNode = findMinInRightSubTree(root.right);
            root.val = minNode.val;
            root.right = deleteNode0(root.right, minNode.val);
        }
        return root;
    }

    //如果用替换的方式解决，那么时间上会多遍历部分数据，但是代码上简单很多
    private TreeNode findMinInRightSubTree(TreeNode root) {
        while(root.left != null) {
            root = root.left;
        }
        return root;
    }

    public static void main(String[] args) {
//        [5,5,5,4,null,null,7]
//        5
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(5);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(4);
//        root.left.right = new TreeNode(4);
        root.right.right = new TreeNode(7);
        int target = 5;
        root = new Solution0450().deleteNode(root, target);
    }
}
