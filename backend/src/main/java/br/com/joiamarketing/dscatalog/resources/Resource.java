package br.com.joiamarketing.dscatalog.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.joiamarketing.dscatalog.entities.Category;
import br.com.joiamarketing.dscatalog.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class Resource {
	
	@Autowired
	private CategoryService service;
	
	@GetMapping
	public ResponseEntity<List<Category>> findall() {
		List<Category> list = service.findAll();
		//List<Category> list = new ArrayList<>();
		//list.add(new Category(1L, "Book"));
		//list.add(new Category(2L, "Electonics"));
		return ResponseEntity.ok().body(list);
	}
}
