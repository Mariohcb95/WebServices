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

import br.edu.ifgoias.academico.entities.Aluno;
import br.edu.ifgoias.academico.repositories.AlunoRepository;

@ActiveProfiles(profiles = "integration-test")
@SpringBootTest(classes = br.edu.ifgoias.academico.AcademicoApplication.class)
public class AlunoTest {
	@Autowired
	AlunoRepository alunoRepository;
	
	@Test
    public void testFindallAluno() {
        assertNotNull(alunoRepository.findAll());
    }	

    @Test
    public void testCreteAluno() {
        var aluno = this.criarAlunoTeste();

        var alunoSalvo = alunoRepository.save(aluno);
        assertEquals(aluno.getNome(), alunoSalvo.getNome());
        assertEquals(aluno.getSexo(), alunoSalvo.getSexo());

        alunoRepository.delete(alunoSalvo);
        assertFalse(alunoRepository.findById(alunoSalvo.getIdaluno()).isPresent());
    }

    @Test
    public void testDeleteAlunoById() {
        var aluno = this.criarAlunoTeste();

        var alunoSalvo = alunoRepository.save(aluno);
        assertEquals(aluno.getNome(), alunoSalvo.getNome());
        assertEquals(aluno.getSexo(), alunoSalvo.getSexo());

        alunoRepository.deleteById(alunoSalvo.getIdaluno());

        assertFalse(alunoRepository.findById(alunoSalvo.getIdaluno()).isPresent());      

        alunoRepository.delete(alunoSalvo);
        assertFalse(alunoRepository.findById(alunoSalvo.getIdaluno()).isPresent());
    }


    @Test
    public void testFindAlunoById() {
        var aluno = this.criarAlunoTeste();

        var alunoSalvo = alunoRepository.save(aluno);
        assertEquals(aluno.getNome(), alunoSalvo.getNome());
        assertEquals(aluno.getSexo(), alunoSalvo.getSexo());

        var alunofound = alunoRepository.findById(alunoSalvo.getIdaluno()).get();

        assertEquals(alunoSalvo.getIdaluno(), alunofound.getIdaluno());
        assertEquals(alunoSalvo.getNome(), alunofound.getNome());
        assertEquals(alunoSalvo.getSexo(), alunofound.getSexo());

        alunoRepository.delete(alunofound);
        assertFalse(alunoRepository.findById(alunoSalvo.getIdaluno()).isPresent());
    }

    public Aluno criarAlunoTeste() {
    	var aluno = new Aluno(null, "Mário", "M", Date.valueOf(LocalDate.of(1995, 10, 20)));
        return aluno;
    }
}
