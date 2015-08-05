package com.xiaomi.count.dao;

import org.junit.Test;


public class PythonTest {


    @Test
    public void testPython() throws Exception {


//        PySystemState sys = Py.getSystemState();
//
//        // 将 Jython 库加入系统的 classpath 中或直接通过这种方式动态引入
//        sys.path.add("E:\\jython2.7.0\\Lib");
//
//        sys.path.add("E:\\jython2.7.0\\Lib\\site-packages");
//
//        System.out.println(sys.path.toString());
//
//        PythonInterpreter interpreter = new PythonInterpreter();
//
//        // 执行算法所在的 python 文件
//        interpreter.execfile("E:\\mypython\\test\\test.py");
//
//        PyFunction pyFunction =interpreter.get(Constant.FUNCTION, PyFunction.class);
//
//        PyObject pyObject = pyFunction.__call__(new PyString("1_233"), new PyString(Constant.add));
//
//        System.out.println("result: " + pyObject.toString());

//        String url = "%25E6%25B5%258B%25E8%25AF%2595python";
//
//        url = new String(url.getBytes(),"utf-8");
//
//        System.out.println(url);

        String sql = "select * from table where 1==2 limit 0,1";

        System.out.println(sql.substring(sql.indexOf("from") ,sql.indexOf("limit")));

    }
}