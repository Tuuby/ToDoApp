package com.example.netlab.todotest.Accessors.remote;

import com.example.netlab.todotest.ToDoItem;

import javax.ws.rs.*;
import java.util.List;


@Path("/todoitems")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public interface ToDoItemCRUDAccessor {
	
	@GET
	public List<ToDoItem> readAllItems();
	
	@POST
	public ToDoItem createItem(ToDoItem item);

	@DELETE
	@Path("/{itemId}")
	public boolean deleteItem(@PathParam("itemId") long itemId);

	@PUT
	public ToDoItem updateItem(ToDoItem item);
}
