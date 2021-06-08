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
import rva.jpa.Proizvod;
import rva.repository.ProizvodRepository;

@CrossOrigin
@RestController
@Api(tags = {"Proizvod CRUD operacije"})
public class ProizvodRestController {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ProizvodRepository proizvodRepository;
	
	
	@GetMapping("proizvod")
	@ApiOperation(value = "Vraća kolekciju svih proizvoda iz baze podataka.")
	public Collection<Proizvod> getProizvodi() {
		return proizvodRepository.findAll();
	}
	
	@GetMapping("proizvod/{id}")
	@ApiOperation(value = "Vraća proizvod u odnosu na prosledjenu vrednost path varijable id.")
	public Proizvod getProizvod(@PathVariable("id") Integer id) {
		return proizvodRepository.getOne(id);
	}
	
	@GetMapping("proizvodNaziv/{naziv}")
	@ApiOperation(value = "Vraća kolekciju proizvoda koji imaju naziv koji sadrži vrednost prosleđenu u okviru path varijable naziv.")
	public Collection<Proizvod> getProizvodByNaziv(@PathVariable("naziv") String naziv) {
		return proizvodRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
			/* ResponseEntity insertProizvod vraca ResponseEntity
			 * Mozemo vratiti i statuscode i da li je zahtev uspesno izvrsen
			 * 
			 *  *  */
	@PostMapping("proizvod")	
	@ApiOperation(value = "Dodaje novi proizvod u bazu podataka.")
	public ResponseEntity<Proizvod> insertProizvod(@RequestBody Proizvod proizvod) {
		if(!proizvodRepository.existsById(proizvod.getId())) {
			proizvodRepository.save(proizvod);
			return new ResponseEntity<Proizvod>(HttpStatus.OK);
		}
		return new ResponseEntity<Proizvod>(HttpStatus.CONFLICT);
	}
	
	
	@PutMapping("proizvod")
	@ApiOperation(value = "Update-uje postojeći proizvod.")
	public ResponseEntity<Proizvod> updateProizvod(@RequestBody Proizvod proizvod) {
		if(!proizvodRepository.existsById(proizvod.getId())) {
			return new ResponseEntity<Proizvod>(HttpStatus.NO_CONTENT);
		}
		proizvodRepository.save(proizvod);
		return new ResponseEntity<Proizvod>(HttpStatus.OK);
	}
	
	@Transactional
	@DeleteMapping("proizvod/{id}")
	@ApiOperation(value = "Briše proizvod u odnosu na vrednost posleđene path varijable id.")
	public ResponseEntity<Proizvod> deleteProizvod(@PathVariable("id") Integer id) {
		if(!proizvodRepository.existsById(id)) {
			return new ResponseEntity<Proizvod>(HttpStatus.NO_CONTENT);
		}
		jdbcTemplate.execute("delete from stavka_racuna where proizvod = " + id);
		proizvodRepository.deleteById(id);
		proizvodRepository.flush();
		if (id == -100) {
			jdbcTemplate.execute("insert into \"proizvod\" (\"id\", \"naziv\", \"proizvodjac\") "
					+ "values (-100, 'proizvod test podatak', 1)");
		}
		return new ResponseEntity<Proizvod>(HttpStatus.OK);
	}
}
