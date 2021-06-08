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
import org.springframework.web.client.HttpStatusCodeException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import rva.jpa.Racun;
import rva.repository.RacunRepository;

@CrossOrigin
@RestController
@Api(tags = {"Račun CRUD operacije"})
public class RacunRestController {
	//Springov kontejner za inverziju kontrole 
	//prilikom pokretanja apl automatski detektuje potrebni bean i injektuje bean tog tipa
	//u to polje kako bi smo mogli razl varijable ili metode da koristimo iz odredjene
	// klase, interfejsa itd.. 
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private RacunRepository racunRepository;
	
	@GetMapping("racun")
	@ApiOperation(value = "Vraća kolekciju svih računa iz baze podataka.")
	public Collection<Racun> getRacuni() {
		return racunRepository.findAll();
	}
	
	@GetMapping("racun/{id}")
	@ApiOperation(value = "Vraća račun u odnosu na posledjenu vrednost path varijable id.")
	public Racun getRacun(@PathVariable("id") Integer id) {
		return racunRepository.getOne(id);
	}
	
	/*@GetMapping("racunNacinPlacanja/{nacinPlacanja}")
	public Collection<Racun> getRacuniByNacinPlacanja(@PathVariable("nacinPlacanja") String nacinPlacanja) {
		return racunRepository.findByNacinPlacanjaContainingIgnoreCase(nacinPlacanja);
	}*/
	
	@PostMapping("racun")
	@ApiOperation(value = "Dodaje novi račun u bazu podataka.")
	public ResponseEntity<Racun> insertRacun(@RequestBody Racun racun) {
		if(!racunRepository.existsById(racun.getId())) {
			racunRepository.save(racun);
			return new ResponseEntity<Racun>(HttpStatus.OK);
		}
		return new ResponseEntity<Racun>(HttpStatus.CONFLICT);
	}
	
	@PutMapping("racun")
	@ApiOperation(value = "Update-uje postojeći račun.")
	public ResponseEntity<Racun> updateRacun(@RequestBody Racun racun) {
		if(!racunRepository.existsById(racun.getId())) {
			return new ResponseEntity<Racun>(HttpStatus.NO_CONTENT);
		}
		racunRepository.save(racun);
		return new ResponseEntity<Racun>(HttpStatus.OK);
	}
	
	@Transactional
	@DeleteMapping("racun/{id}")
	@ApiOperation(value = "Briše račun u odnosu na vrednost posleđene path varijable id.")
	public ResponseEntity<Racun> deleteRacun(@PathVariable("id") Integer id) {
		if(!racunRepository.existsById(id)) {
			return new ResponseEntity<Racun>(HttpStatus.NO_CONTENT);
		}
		jdbcTemplate.execute("delete from stavka_racuna where racun = " + id);
		racunRepository.deleteById(id);
		racunRepository.flush();
		if(id == -100) {
			jdbcTemplate.execute(" INSERT INTO \"racun\" (\"id\", \"datum\", \"nacin_placanja\") "
					+ "VALUES (-100, '1999-09-09', 'TestNacinPlacanja')");
		}
		return new ResponseEntity<Racun>(HttpStatus.OK);
	}
}
