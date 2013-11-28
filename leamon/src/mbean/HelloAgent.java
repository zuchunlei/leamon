package mbean;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

public class HelloAgent {

	public static void main(String[] args) throws Exception {
		int rmiPort = 8080;
		String jmxServerName = "TestJMXServer";

		// jdkfolder/bin/rmiregistry.exe 9999
		// Registry registry = LocateRegistry.createRegistry(rmiPort);

		MBeanServer mbs = MBeanServerFactory.createMBeanServer(jmxServerName);
		// MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

		// HtmlAdaptorServer adapter = new HtmlAdaptorServer();
		// ObjectName adapterName;
		// adapterName = new ObjectName(jmxServerName + ":name=" +
		// "htmladapter");
		// adapter.setPort(8082);
		// adapter.start();
		// mbs.registerMBean(adapter, adapterName);

		ObjectName objName = new ObjectName(jmxServerName + ":name="
				+ "HelloWorld");
		mbs.registerMBean(new HelloMBean(), objName);

		JMXServiceURL url = new JMXServiceURL(
				"service:jmx:rmi:///jndi/rmi://localhost:" + rmiPort + "/"
						+ jmxServerName);

		System.out.println("JMXServiceURL: " + url.toString());
		JMXConnectorServer jmxConnServer = JMXConnectorServerFactory
				.newJMXConnectorServer(url, null, mbs);
		jmxConnServer.start();
	}

}
