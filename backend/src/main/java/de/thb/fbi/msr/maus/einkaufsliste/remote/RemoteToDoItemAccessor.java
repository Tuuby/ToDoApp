package de.thb.fbi.msr.maus.einkaufsliste.remote;


import de.thb.fbi.msr.maus.einkaufsliste.model.ToDoItem;
import org.apache.log4j.Logger;
import de.thb.fbi.msr.maus.einkaufsliste.model.ToDoItemCRUDAccessor;

import java.util.ArrayList;
import java.util.List;

public class RemoteToDoItemAccessor implements ToDoItemCRUDAccessor {

	protected static Logger logger = Logger
			.getLogger(RemoteToDoItemAccessor.class);

	/**
	 * the list of data items, note that the list is *static* as for each client
	 * request a new instance of this class will be created!
	 */
	private static List<ToDoItem> itemlist = new ArrayList<>();

	
	@Override
	public List<ToDoItem> readAllItems() {
		logger.info("readAllItems(): " + itemlist);

		return itemlist;
	}

	@Override
	public ToDoItem createItem(ToDoItem item) {
		logger.info("createItem(): " + item);

		itemlist.add(item);
		return item;
	}

	@Override
	public boolean deleteItem(final long itemId) {
		logger.info("deleteItem(): " + itemId);

		boolean removed = itemlist.remove(new ToDoItem() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 71193783355593985L;

			@Override
			public long getId() {
				return itemId;
			}
		});

		return removed;
	}

	@Override
	public ToDoItem updateItem(ToDoItem item) {
		logger.info("updateItem(): " + item);

		return itemlist.get(itemlist.indexOf(item)).updateFrom(item);
	}
}
