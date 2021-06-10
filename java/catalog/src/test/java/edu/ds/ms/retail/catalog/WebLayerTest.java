package edu.ds.ms.retail.catalog;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import edu.ds.ms.retail.catalog.controller.CategoryController;
import edu.ds.ms.retail.catalog.entity.Category;
import edu.ds.ms.retail.catalog.service.CategoryService;

//@SpringBootTest
//@ActiveProfiles("test")
//@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
//@Sql(scripts = {"/test_data.sql"})
//@ExtendWith(SpringExtension.class)
@WebMvcTest(CategoryController.class)
//put snippets in this folder
@AutoConfigureRestDocs(outputDir = "build/snippets")
public class WebLayerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CategoryService service;
	
	@Test
	public void shouldReturnCategories() throws Exception {
		//mock category service
		Category cat1 = new Category();
		cat1.setName("Books");
		Category cat2 = new Category();
		cat2.setName("Electronics");
		List<Category> cats = new ArrayList<Category>();
		cats.add(cat1);
		cats.add(cat2);
		when(service.getAllCategories()).thenReturn(cats);
		
		ResponseFieldsSnippet snippet = PayloadDocumentation.responseFields(PayloadDocumentation.fieldWithPath("").description("The name of the category."));
		
		this.mockMvc.perform(get("/categories"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Books")))
		.andDo(document("get-all-categories", snippet));
	}
}
