package br.edu.ifgoias.academico.unit;

import br.edu.ifgoias.academico.entities.Curso;
import br.edu.ifgoias.academico.repositories.CursoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest 
class CursoRepositoryTest { 

    @Autowired
    private CursoRepository cursoRepository;

    private Curso curso;

    @BeforeEach
    void setup() { 
        curso = new Curso(null, "Qualidade e Teste de Softwares");
        curso = cursoRepository.save(curso);
    }

    @Test
    void testeSalvarCurso() { 
        Curso novoCurso = new Curso(null, "Pesquisa Operacional");

        Curso curso1 = cursoRepository.save(novoCurso); 

        assertThat(curso1).isNotNull(); 
        assertThat(curso1.getIdcurso()).isNotNull(); 
    }

    @Test
    void testeBuscarCursoPorId() {
        Optional<Curso> curso1 = cursoRepository.findById(curso.getIdcurso()); 

        assertThat(curso1).isPresent(); 
        assertThat(curso1.get().getNomecurso()).isEqualTo("Qualidade e Teste de Softwares");
    }

    @Test
    void testeDeletarCurso() {
        cursoRepository.delete(curso);
        Optional<Curso> curso1 = cursoRepository.findById(curso.getIdcurso());

        assertThat(curso1).isEmpty(); 
    }
}