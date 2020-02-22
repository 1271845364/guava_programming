package com.yejinhui.guava.cache;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/16 13:55
 */
public class Employee {

    private final String name;

    private final String dept;

    private final String empID;

    private final byte[] data = new byte[1024 * 1024];

    public Employee(String name, String dept, String empID) {
        this.name = name;
        this.dept = dept;
        this.empID = empID;
    }

    public String getName() {
        return name;
    }

    public String getDept() {
        return dept;
    }

    public String getEmpID() {
        return empID;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", dept='" + dept + '\'' +
                ", empID='" + empID + '\'' +
                '}';
    }

    /**
     * 打标记的时候会调用，只会标记一次，真正会回收的时候并不会被调用
     *
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        System.out.println("The name " + name + " will be GC.");
    }
}
