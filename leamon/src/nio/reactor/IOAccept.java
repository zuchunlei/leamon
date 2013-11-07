package nio.reactor;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

public class IOAccept {

	private Selector selector;

	public IOAccept() {
		try {
			selector = Selector.open();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void accept() {
		while (true) {
			try {
				int selected = selector.select();
				if (selected != 0) {
					continue;
				}
				handle(selector.selectedKeys());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void handle(Set<SelectionKey> set) throws Exception {
		Iterator<SelectionKey> iter = set.iterator();
		while (iter.hasNext()) {
			SelectionKey key = iter.next();
		}

	}
}
