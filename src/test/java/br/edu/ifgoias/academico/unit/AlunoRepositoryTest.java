package br.edu.ifgoias.academico.unit;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.edu.ifgoias.academico.entities.Aluno;
import br.edu.ifgoias.academico.repositories.AlunoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;


@ExtendWith(SpringExtension.class)
@DataJpaTest 
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) 
class AlunoRepositoryTest {  

    @Autowired
    private AlunoRepository alunoRepository;
    
    @PersistenceContext
    private EntityManager entityManager;

    private Aluno aluno;

    @BeforeEach
    	void setup() { 
        alunoRepository.deleteAll();
        aluno = new Aluno(null, "Mário Henrique", "M", Date.valueOf(LocalDate.of(2001, 4, 02))); 
    }

    @Test
    void testeSalvarAluno() {
        Aluno novoAluno = new Aluno(null, "Felipe", "M", Date.valueOf(LocalDate.of(1995, 10, 20)));

        Aluno aluno1 = alunoRepository.save(novoAluno);
        
        assertThat(aluno1).isNotNull(); 
        assertThat(aluno1.getIdaluno()).isNotNull(); 
        assertThat(aluno1.getNome()).isEqualTo("Felipe"); 
    }

    @Test
    void testeBuscarAlunoPorId() {
        Aluno aluno1 = alunoRepository.save(aluno);
        Aluno aluno2 = alunoRepository.findById(aluno1.getIdaluno()).orElse(null);

        
        
        assertThat(aluno2).isNotNull(); 
        assertThat(aluno2.getNome()).isEqualTo(aluno.getNome());
    }

    @Test
    @Transactional
    void testeApagarAluno() {
        Aluno aluno1 = alunoRepository.save(aluno);
        alunoRepository.deleteById(aluno1.getIdaluno());
        entityManager.flush();

        boolean existe = alunoRepository.existsById(aluno1.getIdaluno());
        assertThat(existe).isFalse();
    }
}
