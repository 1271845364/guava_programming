apache common-io源代码写的很优雅，一天可以读完，用了很多设计模式
二进制文件用字符流搞不定
尽量用字节流，字符流有可能出现编码问题，字符流必须读文本文件
guava定义的工具类是以s结尾，apache common的工具类是以Utils结尾
IO分为：磁盘io、网络io
反序列化就是创建对象：
1、new
2、反射
3、clone
4、ObjectInputStream

Treexxx = 基于红黑树实现的，有序
Linkedxxx = 基于双向链表实现的，有序，Node（item、pre、next）
Hashxxx = 基于hash实现的，无序 = 效率高
链表是动态申请内存的，内存空间不连续
hashtable是线程安全的
vector和ArrayList类似，但是vector是线程安全的
  
双向队列既可以实现队列结构，也可以实现栈结构
optional是一个防止空指针的类，or(xx),如果这个对象是空，然后or(xx)，调用get()返回的就是xx，xx就相当于是默认值

递归方法调用可能会出现stackOverFlowError溢出
Iterable里面的方法会产生Iterator
Collection<E> extends Iterable<E>