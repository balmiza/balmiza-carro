package com.carros2.domain;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.carros2.domain.dto.CarroDTO;
import com.carros2.exceptions.ObjectNotFoundException;


@Service
public class CarroService {
	
	@Autowired
	private CarroRepository rep;
	
	public List<CarroDTO> getCarros() {
		return rep.findAll().stream().map(CarroDTO::create).collect(Collectors.toList());
	}

	public CarroDTO getCarrosById(Long id) {
		Optional<Carro> carro = rep.findById(id);
		return carro.map(CarroDTO::create).orElseThrow(() -> new ObjectNotFoundException("Carro não encontrado"));
		//return rep.findById(id).map(CarroDTO::create);
	}

	public List<CarroDTO> getCarrosByTipo(String tipo) {
		return rep.findByTipo(tipo).stream().map(CarroDTO::create).collect(Collectors.toList());
	}

	public Carro save(Carro carro) {
		return rep.save(carro);
		
	}

	public CarroDTO update(Carro carro, Long id) {
		Assert.notNull(id, "Objeto veio nulo, não foi possível atualizar o registro");
		
		// Busca o carro no banco de dados
		Optional<Carro> optional = rep.findById(id);
		if(optional.isPresent()) {
			Carro db = optional.get();
			// Copiar as propriedades
			db.setNome(carro.getNome());
			db.setTipo(carro.getTipo());
			System.out.println("Carro id " + db.getId());
			
			// atualiza o carro
			rep.save(db);
			return CarroDTO.create(db);
		} else {
			return null;
			//throw new RuntimeException("Não foi possível atualizar o registro");
		}
	}

	public void delete(Long id) {
		rep.deleteById(id);
	}

	public CarroDTO insert(Carro carro) {
		Assert.isNull(carro.getId(),"Não foi possível inserir o registro");
		return CarroDTO.create(rep.save(carro));
	}
}