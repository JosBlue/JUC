package com.lfw;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2022/3/16 下午8:26
 * @description:
 */
public class DepartmentTest {
    public static void main(String[] args) {
        List<Department> allDepartment = new ArrayList<>();
        Department dep1 = new Department(1, 0, "北京总部");
        Department dep3 = new Department(3, 1, "研发中心");
        Department dep4 = new Department(4, 3, "后端研发组");
        Department dep6 = new Department(6, 4, "后端实习生组");
        Department dep7 = new Department(7, 3, "前端研发组");
        Department dep8 = new Department(8, 1, "产品部");
        allDepartment.add(dep6);
        allDepartment.add(dep7);
        allDepartment.add(dep8);
        allDepartment.add(dep1);
        allDepartment.add(dep3);
        allDepartment.add(dep4);

//        System.out.println(JSON.toJSONString(allDepartment));

        int parentId = DepartmentTest.getCommonParent(6, 7, allDepartment); // 父节点
        System.out.println(parentId);
        parentId = DepartmentTest.getCommonParent(6, 8, allDepartment);
        System.out.println(parentId);
    }

    /**
     * 根据a,b 返回最近的父部门
     * 要求：不能新增参数，不能增加static变量
     *
     * @return
     */
    public static int getCommonParent(int a, int b, List<Department> allDepartment) {
        List<Integer> aPid = new ArrayList<>();
        List<Integer> bPid = new ArrayList<>();
        while (true) {
            for (Department department : allDepartment) {
                if (department.getId() == a) {
                    aPid.add(department.getPid());
                    a = department.getPid();
                    break;
                }
            }
            if (a == 0) {
                break;
            }
        }

        while (true) {
            for (Department department : allDepartment) {
                if (department.getId() == b) {
                    bPid.add(department.getPid());
                    b = department.getPid();
                    break;
                }
            }
            if (b == 0) {
                break;
            }
        }

        Integer pid = -1;
        if (aPid.size() > 0 && bPid.size() > 0) {
            for (int i = 0; i < aPid.size(); i++) {
                for (int j = 0; j < bPid.size(); j++) {
                    if (aPid.get(i).equals(bPid.get(j))) {
                        pid = aPid.get(i);
                        break;
                    }
                }
                if (pid != -1) {
                    break;
                }
            }
        }

        return pid;
    }



    static class Department {
        /**
         * id
         */
        private int id;
        /**
         * parent id
         */
        private int pid;
        /**
         * 名称
         */
        private String name;

        public Department(int id, int pid, String name) {
            this.id = id;
            this.pid = pid;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Department{" +
                    "id=" + id +
                    ", pid=" + pid +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

}
