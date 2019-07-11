package com.example.netlab.todotest.Accessors.remote;

import com.example.netlab.todotest.ToDoItem;

import javax.ws.rs.*;
import java.util.List;


@Path("/todoitems")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public interface ToDoItemCRUDAccessor {
	
	@GET
	List<ToDoItem> readAllItems();
	
	@POST
	ToDoItem createItem(ToDoItem item);

	@DELETE
	@Path("/{itemId}")
	boolean deleteItem(@PathParam("itemId") long itemId);

	@PUT
	ToDoItem updateItem(ToDoItem item);
}
