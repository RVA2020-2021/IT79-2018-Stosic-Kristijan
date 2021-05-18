package rva.ctrls;

import java.util.Collection;

import javax.transaction.Transactional;

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
import rva.jpa.Proizvodjac;
import rva.repository.ProizvodjacRepository;

@CrossOrigin
@RestController
@Api(tags = {"Proizvođač CRUD operacije"})
public class ProizvodjacRestController {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired 
	private ProizvodjacRepository proizvodjacRepository;
	
	@GetMapping("proizvodjac")
	@ApiOperation(value = "Vraća kolekciju svih proizvođača iz baze podataka.")
	public Collection<Proizvodjac> getProizvodjaci() {
		return proizvodjacRepository.findAll();
	}
	
	@GetMapping("proizvodjac/{id}")
	@ApiOperation(value = "Vraća proizvođača u odnosu na prosleđenu vrednost path varijable id")
	public Proizvodjac getProizvodjac(@PathVariable("id") Integer id) {
		return proizvodjacRepository.getOne(id);
	}
	
	@GetMapping("proizvodjacNaziv/{naziv}") 
	@ApiOperation(value = "Vraća kolekciju proizvođača koji imaju naziv koji sadrži vrednost prosleđenu u okviru path varijable naziv")
	public Collection<Proizvodjac> getProizvodjacByNaziv(@PathVariable("naziv") String naziv) {
		return proizvodjacRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	@PostMapping("proizvodjac")
	@ApiOperation(value = "Dodaje novog proizvođača u bazu podataka.")
	public ResponseEntity<Proizvodjac> insertProizvodjac(@RequestBody Proizvodjac proizvodjac) {
		if(!proizvodjacRepository.existsById(proizvodjac.getId())) {
			proizvodjacRepository.save(proizvodjac);
			return new ResponseEntity<Proizvodjac>(HttpStatus.OK);
		}
		return new ResponseEntity<Proizvodjac>(HttpStatus.CONFLICT);
	}
	
	@PutMapping("proizvodjac")
	@ApiOperation(value = "Update-uje postojećeg proizvođača.")
	public ResponseEntity<Proizvodjac> updateProizvodjac(@RequestBody Proizvodjac proizvodjac) {
		if(!proizvodjacRepository.existsById(proizvodjac.getId())) {
			return new ResponseEntity<Proizvodjac>(HttpStatus.NO_CONTENT);
		}
		proizvodjacRepository.save(proizvodjac);
		return new ResponseEntity<Proizvodjac>(HttpStatus.OK);
	}
	
	@Transactional
	@DeleteMapping("proizvodjac/{id}")
	@ApiOperation(value = "Briše proizvođača u odnosu na vrednost posleđene path varijable id.")
	public ResponseEntity<Proizvodjac> deleteProizvodjac(@PathVariable("id") Integer id) {
		if(!proizvodjacRepository.existsById(id)) {
			return new ResponseEntity<Proizvodjac>(HttpStatus.NO_CONTENT);
		}
		jdbcTemplate.execute("delete from proizvod where proizvodjac = " + id);
		//  jdbcTemplate.execute("delete from stavka_racuna where proizvod = " + id);
		proizvodjacRepository.deleteById(id); 
		proizvodjacRepository.flush();
		if(id == -100) {
			jdbcTemplate.execute(" INSERT INTO \"proizvodjac\" (\"id\", \"naziv\", \"adresa\", \"kontakt\") "
					+ "VALUES (-100, 'Test Naziv', 'Test Adresa', '+3816666666')");
		}
		return new ResponseEntity<Proizvodjac>(HttpStatus.OK);
	}
}
