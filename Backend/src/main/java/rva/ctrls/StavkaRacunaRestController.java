package rva.ctrls;

import java.math.BigDecimal;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import rva.jpa.Racun;
import rva.jpa.StavkaRacuna;
import rva.repository.RacunRepository;
import rva.repository.StavkaRacunaRepository;

@CrossOrigin
@RestController
@Api(tags = {"Stavka računa CRUD operacije"})
public class StavkaRacunaRestController {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private StavkaRacunaRepository stavkaRacunaRepository;
	
	@Autowired 
	private RacunRepository racunRepository;
	
	@GetMapping("stavkaRacuna")
	@ApiOperation(value = "Vraća kolekciju svih stavki računa iz baze podataka.")
	public Collection<StavkaRacuna> getStavkeRacuna() {
		return stavkaRacunaRepository.findAll();
	}
	
	@GetMapping("stavkaRacuna/{id}")
	@ApiOperation(value = "Vraća stavku računa u odnosu na posledjenu vrednost path varijable id")
	public StavkaRacuna getStavkaRacuna(@PathVariable("id") Integer id) {
		return stavkaRacunaRepository.getOne(id);
	}
	
	@GetMapping("stavkeZaRacunId/{id}")
	@ApiOperation(value = "Vraća kolekciju svih stavki računa u odnosu na posledjenu vrednost path varijable id za račun.")
	public Collection<StavkaRacuna> stavkePoRacunuId(@PathVariable("id") Integer id) {
		Racun r =  racunRepository.getOne(id);
		return stavkaRacunaRepository.findByRacun(r);
	}
	
	@GetMapping("stavkaRacunaCena/{cena}")
	@ApiOperation(value = "Vraća kolekciju svih stavki računa u odnosu na prosleđenu vrednost path varijable cena.")
	public Collection<StavkaRacuna> stavkeRacunaCena(@PathVariable("cena") BigDecimal cena) {
		return stavkaRacunaRepository.findByCenaLessThanOrderById(cena);
	}
	
	@PostMapping("stavkaRacuna")
	@ApiOperation(value = "Dodaje novu stavku računa u bazu podataka.")
	public ResponseEntity<StavkaRacuna> insertStavkaRacuna(@RequestBody StavkaRacuna stavkaRacuna) {
		if(!stavkaRacunaRepository.existsById(stavkaRacuna.getId())) {
			stavkaRacuna.setRedniBroj(stavkaRacunaRepository.nextRBr(stavkaRacuna.getRacun().getId()));
			stavkaRacunaRepository.save(stavkaRacuna);
			return new ResponseEntity<StavkaRacuna>(HttpStatus.OK);
		}
		return new ResponseEntity<StavkaRacuna>(HttpStatus.CONFLICT);
	}
	
	@PutMapping("stavkaRacuna")
	@ApiOperation(value = "Update-uje postojeću stavku računa.")
	public ResponseEntity<StavkaRacuna> updateStavkaRacuna(@RequestBody StavkaRacuna stavkaRacuna) {
		if(!stavkaRacunaRepository.existsById(stavkaRacuna.getId())) {
			return new ResponseEntity<StavkaRacuna>(HttpStatus.NO_CONTENT);
		}
		stavkaRacunaRepository.save(stavkaRacuna);
		return new ResponseEntity<StavkaRacuna>(HttpStatus.OK);
	}
	
	@DeleteMapping("stavkaRacuna/{id}")
	@ApiOperation(value = "Briše stavku računa u odnosu na vrednost posleđene path varijable id.")
	public ResponseEntity<StavkaRacuna> deleteStavkaRacuna(@PathVariable("id") Integer id) {
		if(!stavkaRacunaRepository.existsById(id)) {
			return new ResponseEntity<StavkaRacuna>(HttpStatus.NO_CONTENT);
		}
		stavkaRacunaRepository.deleteById(id);
		stavkaRacunaRepository.flush();
		if(id == -100) {
			jdbcTemplate.execute(
					"INSERT INTO stavka_racuna(\"id\", \"redni_broj\", \"kolicina\", \"jedinica_mere\", \"cena\", \"racun\", \"proizvod\")"
					+ "VALUES(-100, 30, 15, 'g', 100, 1, 1)"
			);
		}
		return new ResponseEntity<StavkaRacuna>(HttpStatus.OK);
	}
}
