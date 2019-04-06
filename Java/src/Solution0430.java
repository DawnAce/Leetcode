/**
 * 您将获得一个双向链表，除了下一个和前一个指针之外，它还有一个子指针，可能指向单独的双向链表。
 * 这些子列表可能有一个或多个自己的子项，依此类推，生成多级数据结构，如下面的示例所示。
 *
 * 扁平化列表，使所有结点出现在单级双链表中。您将获得列表第一级的头部。
 */
public class Solution0430 {
    class Node {
        public int val;
        public Node prev;
        public Node next;
        public Node child;

        public Node() {}

        public Node(int _val,Node _prev,Node _next,Node _child) {
            val = _val;
            prev = _prev;
            next = _next;
            child = _child;
        }
    }

    Node next = null;
    //这道题只要把next看作right，child看作left，就跟将二叉树变为链表很相似
    //核心思路是，如果按照前序遍历串起来，就是最终结果
    //如果正向思考的话，有一个难点，就是如果我用循环，显然我需要返回新链表的头节点，那么我怎么获得左的最后一个节点?
    //相反的，如果我从最后一个节点，倒叙进行遍历，那么我只需要记住上一次遍历过谁，下个节点直接串上记录值即可
    public Node flatten(Node head) {
        helper(head);
        return head;
    }

    private void helper(Node head){
        if(head == null) return;
        helper(head.next);
        helper(head.child);
        head.next = next;
        if(next != null)
            next.prev = head;
        head.child = null;
        next = head;
    }
}
