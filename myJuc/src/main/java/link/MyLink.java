package link;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2020/12/17 下午5:46
 * @description: 链表操作类（TODO 约瑟夫问题的实现）
 */
public class MyLink {

    /**
     * 新增链表节点信息
     *
     * @param data 新增节点信息
     * @param node 当前链表信息
     * @return 是否新增成功
     */
    public Boolean addNode(int data, Node node) {

        // 若传入节点信息为空，则当前节点为首节点,收尾指向均为空，为单节点信息
        if (Objects.isNull(node)) {
            node = new Node();
            node.setData(data);
            return true;
        }

        // 若传入节点信息不为空，需循环获取到当前链表的尾节点，然后新增节点信息
        while (true) {
            if (Objects.nonNull(node.getNext())) {
                node = node.getNext();
            } else {
                Node nextNode = new Node();
                nextNode.setData(data);
                nextNode.setHead(node);
                node.setNext(nextNode);
                break;
            }
        }

        return true;
    }

    /**
     * 删除链表节点信息
     *
     * @param node 移除节点信息
     * @param data 待移除信息
     * @return 是否移除成功
     */
    public boolean removeNode(Node node, int data) {

        Node newNode = new Node();

        while (true) {

            // 单节点信息,且不存在当前待移除节点信息
            if (Objects.isNull(node.getNext()) && !Objects.equals(node.getData(), data)) {
                newNode.setData(node.getData());
                break;
            }

            // 单节点信息,且存在当前待移除节点信息
            if (Objects.isNull(node.getNext()) && Objects.equals(node.getData(), data)) {
                node = null;
                break;
            }

            if (!Objects.equals(node.getData(), data)) {
                newNode.setData(node.getData());
                newNode.setHead(node.getHead());
                newNode.setNext(node.getNext());
            } else {

                // 为首节点信息
                if (Objects.isNull(node.getHead())) {
                    node.setNext(null);
                    break;
                }

                // 为尾节点信息
                if (Objects.isNull(node.getNext())) {
                    node.getNext().setHead(null);
                    break;
                }

                // 前面的节点
                node.getHead().setNext(node.getNext());

                // 后面节点
                node.getNext().setHead(node.getHead());
            }

        }

        return true;

    }

    /**
     * 获取链表全部节点信息(无需使用该方式输出)
     *
     * @param node 节点信息
     * @return 节点转换成List并输出
     */
    public static List<Node> getAllNodeInfo(Node node) {
        List<Node> nodeList = new ArrayList<>();

        // 单节点链表
        if (Objects.isNull(node.getNext())) {
            nodeList.add(node);
            return nodeList;
        }

        // 多节点链表
        while (true) {
            nodeList.add(node);
            if (Objects.nonNull(node.getNext())) {
                node = node.getNext();
            } else {
                break;
            }
        }

        return nodeList;

    }

    /**
     * 获取链表长度
     *
     * @param node 节点信息
     * @return 节点长度
     */
    public static int getNodeLength(Node node) {
        int length = 0;

        while (true) {
            if (Objects.nonNull(node.getNext())) {
                ++length;
                node = node.getNext();
            } else {
                ++length;
                break;
            }
        }

        return length;
    }


//    public static void main(String[] args) {
//        Node node = new Node();
//        Node node1 = new Node();
//        Node node2 = new Node();
//        Node node3 = new Node();
//        Node node4 = new Node();
//
//        node.setData(1);
////        node.setNext(node1);
//
////        node1.setData(2);
////        node1.setNext(node2);
////        node1.setHead(node);
////
////        node2.setData(3);
////        node2.setNext(node3);
////        node2.setHead(node1);
////
////        node3.setData(4);
////        node3.setNext(node4);
////        node3.setHead(node2);
////
////        node4.setData(5);
////        node4.setHead(node3);
//
//        Node newNode = node;
//
//        Node finalNode;
//
////        while (true) {
////            if (Objects.nonNull(newNode.getNext())) {
////                newNode = newNode.getNext();
////            } else {
////                System.out.println(newNode.getData());
////                finalNode = newNode;
////                break;
////            }
////        }
//
//        System.out.println(MyLink.getNodeLength(node));
//
//        System.out.println(JSON.toJSONString(node));
//
//    }
}
