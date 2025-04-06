package com.example.wms.basicfunction;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;
	
	public List<Item> getAllItems(){
		return itemRepository.findAll();
	}
	
	public Optional<Item> getItemById(Long id) {
		return itemRepository.findById(id);
	}
	
	public int createItem(Item item) {
		return itemRepository.save(item);
	}
	
	public void deleteItem(Long id) {
		itemRepository.delete(id);
	}
	
	public int updateItem(Item item) {
		return itemRepository.update(item);
	}
}
