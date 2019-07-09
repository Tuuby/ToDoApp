package de.thb.fbi.msr.maus.einkaufsliste.model;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;


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
