package test;


/**
* test/_HelloImplBase.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� hello.idl
* 2013��7��24�� ������ ����02ʱ54��12�� CST
*/

public abstract class _HelloImplBase extends org.omg.CORBA.portable.ObjectImpl
                implements test.Hello, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors
  public _HelloImplBase ()
  {
  }

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("sayHello", new java.lang.Integer (0));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // test/Hello/sayHello
       {
         String message = in.read_string ();
         String $result = null;
         $result = this.sayHello (message);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:test/Hello:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }


} // class _HelloImplBase
