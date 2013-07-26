package test;

/**
* test/HelloHolder.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 hello.idl
* 2013年7月24日 星期三 下午02时54分12秒 CST
*/

public final class HelloHolder implements org.omg.CORBA.portable.Streamable
{
  public test.Hello value = null;

  public HelloHolder ()
  {
  }

  public HelloHolder (test.Hello initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = test.HelloHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    test.HelloHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return test.HelloHelper.type ();
  }

}
