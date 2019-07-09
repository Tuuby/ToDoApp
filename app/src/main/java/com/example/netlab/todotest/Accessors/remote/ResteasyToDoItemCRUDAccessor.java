package com.example.netlab.todotest.Accessors.remote;

import android.util.Log;
import com.example.netlab.todotest.ToDoItem;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;

import java.util.List;

public class ResteasyToDoItemCRUDAccessor implements ToDoItemCRUDAccessor {
	
	protected static String logger = ResteasyToDoItemCRUDAccessor.class.getSimpleName();

	/**
	 * the client via which we access the rest web interface provided by the server
	 */
	private ToDoItemCRUDAccessor restClient;
	
	public ResteasyToDoItemCRUDAccessor(String baseUrl) {

		Log.i(logger,"initialising restClient for baseUrl: " + baseUrl);
		
		// create a client for the server-side implementation of the interface
		this.restClient = ProxyFactory.create(ToDoItemCRUDAccessor.class,
				baseUrl,
				new ApacheHttpClient4Executor());
		
		Log.i(logger,"initialised restClient: " + restClient + " of class " + restClient.getClass());
	}

	@Override
	public List<ToDoItem> readAllItems() {
		Log.i(logger, "readAllItems()");

		List<ToDoItem> itemlist = restClient.readAllItems();
		
		Log.i(logger, "readAllItems(): got: " + itemlist);
	
		return itemlist;
	}

	@Override
	public ToDoItem createItem(ToDoItem item) {
		Log.i(logger, "createItem(): send: " + item);

		item = restClient.createItem(item);
		
		Log.i(logger, "createItem(): got: " + item);
	
		return item;
	}

	@Override
	public boolean deleteItem(long itemId) {
		Log.i(logger, "deleteItem(): send: " + itemId);

		boolean deleted = restClient.deleteItem(itemId);
		
		Log.i(logger, "deleteItem(): got: " + deleted);
	
		return deleted;
	}

	@Override
	public ToDoItem updateItem(ToDoItem item) {
		Log.i(logger, "updateItem(): send: " + item);

		item = restClient.updateItem(item);
		
		Log.i(logger, "updateItem(): got: " + item);
	
		return item;
	}

}
