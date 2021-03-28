package com.carros2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.carros2.domain.Carro;
import com.carros2.domain.CarroService;
import com.carros2.domain.dto.CarroDTO;
import com.carros2.exceptions.ObjectNotFoundException;


@SpringBootTest
class CarroServiceTest {

	@Autowired
	private CarroService service;
	
	@Test
	void testSave() {
		Carro carro = new Carro();
		carro.setNome("santos");
		carro.setTipo("esportivos");
		
		CarroDTO c = service.insert(carro);
		
		assertNotNull(c);
		
		Long id = c.getId();
		assertNotNull(id);
		
		// Buscar o objeto
		c = service.getCarrosById(id);
		assertNotNull(c);
		
		assertEquals("santos", c.getNome());
		assertEquals("esportivos", c.getTipo());
		
		// deletar o objeto
		service.delete(id);
		
		// verificar se deletou
		
		try {
			assertNull(service.getCarrosById(id));
			fail("O carro não foi excluido");
		} catch (ObjectNotFoundException e) {
			//ok
		}
		
	}
	
	@Test
	public void testLista() {
		List<CarroDTO> carros = service.getCarros();
		assertEquals(30, carros.size());
	}
	
	@Test
	public void testGet() throws ObjectNotFoundException {
		CarroDTO c = service.getCarrosById(11L);
		assertNotNull(c);
		assertEquals("Ferrari FF", c.getNome());
	}
	
	@Test
	public void testListaPorTipo() {
		assertEquals(10, service.getCarrosByTipo("classicos").size());
		assertEquals(10, service.getCarrosByTipo("esportivos").size());
		assertEquals(10, service.getCarrosByTipo("luxo").size());
		
		assertEquals(0, service.getCarrosByTipo("xpto").size());
		
		
	}

}
