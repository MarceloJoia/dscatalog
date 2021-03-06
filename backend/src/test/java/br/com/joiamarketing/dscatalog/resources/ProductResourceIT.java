package br.com.joiamarketing.dscatalog.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.joiamarketing.dscatalog.dto.ProductDTO;
import br.com.joiamarketing.dscatalog.tests.Factory;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductResourceIT {
	
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	private long existingId;
	private long nonExistingId;
	private long countTotalProducts;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		countTotalProducts = 25L;

	}

	@Test
	public void findAllShouldReturnSortedPageWhenByName() throws Exception {
		ResultActions result = 
				mockMvc.perform(get("/products?page=0&size=5&sort=name,asc")
						.accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.totalElements").value(countTotalProducts));
		result.andExpect(jsonPath("$.content").exists());
		result.andExpect(jsonPath("$.content[0].name").value("Macbook Pro"));
		result.andExpect(jsonPath("$.content[1].name").value("PC Gamer"));
		result.andExpect(jsonPath("$.content[2].name").value("PC Gamer Alfa"));
	}
	
	@Test
	public void updateShouldReturnProductDTOWhenIdExists() throws Exception {
		ProductDTO productDTO = Factory.createProductDTO();
		//Convert to String "JSon"
		String jsonBody = objectMapper.writeValueAsString(productDTO);
		
		//Save the name in: ???expectedName???, to test if it has changed.
		String expectedName = productDTO.getName();
		//Save the description in: ???expectedDescription???, to test if it has changed.
		String expectedDescription = productDTO.getDescription();

		ResultActions result = 
				mockMvc.perform(put("/products/{id}", existingId)
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").value(existingId));//check if the id exists
		result.andExpect(jsonPath("$.name").value(expectedName));//check if the name has been changed
		result.andExpect(jsonPath("$.description").value(expectedDescription));//check if the description has been changed
	}

	@Test
	public void updateShouldReturnNotFoundWhenIdDoesNotExist () throws Exception {
		ProductDTO productDTO = Factory.createProductDTO();
		String jsonBody = objectMapper.writeValueAsString(productDTO);
	
		ResultActions result = 
				mockMvc.perform(put("/products/{id}", nonExistingId)
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	}
}
