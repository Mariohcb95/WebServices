package br.edu.ifgoias.academico.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.edu.ifgoias.academico.entities.Curso;
import br.edu.ifgoias.academico.resources.CursoResource;
import br.edu.ifgoias.academico.services.CursoService;


@ExtendWith(MockitoExtension.class) 
class CursoResourceTest {

    @InjectMocks
    private CursoResource cursoResource;

    @Mock
    private CursoService cursoService;

    @Test
    void deveRetornarListaDeCursos() {
        List<Curso> cursos = Arrays.asList(new Curso(2, "QTS"), new Curso(4, "PO"));
        
        when(cursoService.findAll()).thenReturn(cursos);

        ResponseEntity<List<Curso>> result = cursoResource.findAll();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).hasSize(2);
        assertThat(result.getBody().get(0).getNomecurso()).isEqualTo("QTS");
    }

    @Test
    void testRetornarCursoPorId() {       
        Curso curso = new Curso(2, "QTS");

        when(cursoService.findById(2)).thenReturn(curso);

        ResponseEntity<Curso> result = cursoResource.findById(2);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getNomecurso()).isEqualTo("QTS");
    }

    @Test
    void testCriarCurso() {
        Curso curso = new Curso(3, "Engenharia de Requisitos");
        when(cursoService.insert(curso)).thenReturn(curso);

        ResponseEntity<Curso> result = cursoResource.insert(curso);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getNomecurso()).isEqualTo("Engenharia de Requisitos");
    }

    @Test
    void testAtualizarCurso() {
        Curso curso = new Curso(2, "PO");
        when(cursoService.update(2, curso)).thenReturn(curso);

        ResponseEntity<Curso> result = cursoResource.update(2, curso);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getNomecurso()).isEqualTo("PO");
    }

    @Test
    void testDeletarCurso() {
        doNothing().when(cursoService).delete(2);

        boolean result = cursoResource.delete(2);

        assertThat(result).isEqualTo(true);
    }
}
