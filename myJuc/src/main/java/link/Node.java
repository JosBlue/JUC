package link;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2020/12/17 下午5:46
 * @description: 节点信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Node {

    /**
     * 上一节点信息，默认为null，首节点该值为null
     */
    Node head = null;

    /**
     * 节点对应数据信息
     */
    int data;

    /**
     * 下一节点信息，默认为null()，尾节点该值为null
     */
    Node next = null;

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                '}';
    }
}
