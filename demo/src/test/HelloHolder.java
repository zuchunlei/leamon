package test;

/**
* test/HelloHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� hello.idl
* 2013��7��24�� ������ ����02ʱ54��12�� CST
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
