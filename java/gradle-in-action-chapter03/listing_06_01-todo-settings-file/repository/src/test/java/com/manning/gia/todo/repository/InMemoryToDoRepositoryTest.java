package com.manning.gia.todo.repository;

import com.manning.gia.todo.model.ToDoItem;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class InMemoryToDoRepositoryTest {

	private ToDoRepository inMemoryToDoRepository;

	@Before
	public void setUp() {
		inMemoryToDoRepository = new InMemoryToDoRepository();
	}

	@Test
	public void insertToDoItem() {
		ToDoItem newToDoItem = new ToDoItem();
		newToDoItem.setName("Write unit tests");
		Long newId = inMemoryToDoRepository.insert(newToDoItem);
		//assertNotNull(newId);
		assertNull(newId); //introduce error to test Jenkins post bild email notification functionality

		ToDoItem persistedToDoItem = inMemoryToDoRepository.findById(newId);
		assertNotNull(persistedToDoItem);
		assertEquals(newToDoItem, persistedToDoItem);
	}
	
	@Test
	public void insertToDoItems() {
		int items = System.getProperty("items") != null ? Integer.parseInt(System.getProperty("items")) : 1;
		createAndInsertToDoItems(items);
		List<ToDoItem> toDoItems = inMemoryToDoRepository.findAll();
		assertEquals(items, toDoItems.size());
	}

	private void createAndInsertToDoItems(int items) {
		System.out.println("Creating " + items + " To Do items.");
		for(int i = 1; i <= items; i++) {
			ToDoItem toDoItem = new ToDoItem();
			toDoItem.setName("To Do task " + i);
			inMemoryToDoRepository.insert(toDoItem);
		}
	}
}