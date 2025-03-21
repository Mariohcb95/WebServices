package br.edu.ifgoias.academico.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.ifgoias.academico.entities.Curso;
import br.edu.ifgoias.academico.repositories.CursoRepository;

@Service
public class CursoService {
	
	@Autowired
	private CursoRepository cursoRep;
	
	public List<Curso> findAll(){
		return cursoRep.findAll();
	}
	
	public Curso findById(Integer id) {
		return cursoRep.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND) );	
	}
	
	public Curso insert(Curso obj) {
		return cursoRep.save(obj);
	}
	
	public void delete(Integer id) {
		Curso existe = findById(id);
		cursoRep.delete(existe);
	}
	
	public Curso update (Integer id, Curso objAlterado) {
		return cursoRep.findById(id).map(
											cursoDB -> {
													cursoDB.setNomecurso( objAlterado.getNomecurso() );
													return cursoRep.save(cursoDB);
											}
									)
									.orElseThrow( 
											() -> new ResponseStatusException(HttpStatus.NOT_FOUND) 
									);
	}
	

}
