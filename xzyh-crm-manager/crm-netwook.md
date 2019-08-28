vo对象来接收从数据库查询到的结果然后返回到前端,接收也同样使用

查询，usert
106.13.141.29:3306/Crm
insert into sys_role (role_name, role_desc, create_time, 
      update_time, role_status, sys_user_id, 
      is_leader, pid)
    values ('dev','开发组员',NOW(),NOW(),1, null,0,3) 

查询全部或者模糊查询一个人
select * from sys_role where role_name like 'test' and

role_status = 1 order by create_time desc

Java为每个原始类型提供了封装类，Integer是java为int提供的封装类。
int的默认值为0，而Integer的默认值为null，即Integer可以区分出未赋值和值为0的区别，int则无法表达出未赋值的情况

比较两个Integer的值是否相同，方法比较多:

1、推荐用equals()，这个还可以避免一些空指针问题的出现。

2、或者使用Integer.intValue();这样出来的就是int值，就可以直接比较了（可能会抛出空指针异常）；

在for循环中，continue的作用是从continue语句那一行结束，跳到下一次循环中，从循环头开始执行，洗面举例说明：

for(int i=0;i<10;i++){
    int a = i+1;
    int b = a+i;
    if(b == 3){
        continue;//此处的意思是，当b=3的时候，下面的代码不执行了
        //也就是说当i=1的时候，下面的代码不执行了，从i=3开始运行
    }
    b = a*i;
}
Long.valueOf(这里有参数)，是将参数转换成long的包装类——Long。
longValue()是Long类的一个方法，用来得到Long类中的数值。
前者是将基本数据类型转换成包装类
后者是将包装类中的数据拆箱成基本数据类型

alt + enter 万能快捷

