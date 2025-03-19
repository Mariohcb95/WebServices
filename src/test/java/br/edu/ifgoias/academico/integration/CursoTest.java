package br.edu.ifgoias.academico.integration;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.edu.ifgoias.academico.entities.Curso;
import br.edu.ifgoias.academico.repositories.CursoRepository;

@ActiveProfiles(profiles = "integration-test")
@SpringBootTest(classes = br.edu.ifgoias.academico.AcademicoApplication.class)
public class CursoTest {
	@Autowired
	CursoRepository cursoRepository;
	
	@Test
    public void testFindallCurso() {
        assertNotNull(cursoRepository.findAll());
    }	

    @Test
    public void testCreteCurso() {
        var curso = this.criarCursoTeste();

        var cursoSalvo = cursoRepository.save(curso);
        assertEquals(curso.getNomecurso(), cursoSalvo.getNomecurso());

        cursoRepository.delete(cursoSalvo);
        assertFalse(cursoRepository.findById(cursoSalvo.getIdcurso()).isPresent());
    }

    @Test
    public void testDeleteCursoById() {
        var curso = this.criarCursoTeste();

        var cursoSalvo = cursoRepository.save(curso);
        assertEquals(curso.getNomecurso(), cursoSalvo.getNomecurso());

        cursoRepository.deleteById(cursoSalvo.getIdcurso());

        assertFalse(cursoRepository.findById(cursoSalvo.getIdcurso()).isPresent());      

        cursoRepository.delete(cursoSalvo);
        assertFalse(cursoRepository.findById(cursoSalvo.getIdcurso()).isPresent());
    }


    @Test
    public void testFindCursoById() {
        var curso = this.criarCursoTeste();

        var cursoSalvo = cursoRepository.save(curso);
        assertEquals(curso.getNomecurso(), cursoSalvo.getNomecurso());

        var cursofound = cursoRepository.findById(cursoSalvo.getIdcurso()).get();

        assertEquals(cursoSalvo.getIdcurso(), cursofound.getIdcurso());
        assertEquals(cursoSalvo.getNomecurso(), cursofound.getNomecurso());        

        cursoRepository.delete(cursofound);
        assertFalse(cursoRepository.findById(cursoSalvo.getIdcurso()).isPresent());
    }

    public Curso criarCursoTeste() {
    	var curso = new Curso(null, "QTS");
        return curso;
    }
}
