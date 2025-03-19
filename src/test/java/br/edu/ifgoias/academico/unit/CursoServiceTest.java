package br.edu.ifgoias.academico.unit;
import br.edu.ifgoias.academico.entities.Curso;
import br.edu.ifgoias.academico.repositories.CursoRepository;
import br.edu.ifgoias.academico.services.CursoService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class CursoServiceTest {

    @InjectMocks
    private CursoService cursoService;

    @Mock
    private CursoRepository cursoRepository;

    Curso curso;
    
    @BeforeEach
    void setUp() {
    	//curso = new Curso(21, "Jav");
    	MockitoAnnotations.openMocks(this);
    }
    

    @Test
    void testInsert() {
        Curso curso = new Curso(null, "QTS");
        when(cursoRepository.save(curso)).thenReturn(new Curso(1, "QTS"));

        Curso result = cursoService.insert(curso);
        assertEquals("QTS", result.getNomecurso());
    }


    @Test
    void testFindByIdCurso() {

    	curso = new Curso(20, "QTS");
        when(cursoRepository.findById(20)).thenReturn(Optional.of(curso)); 

        Curso result = cursoService.findById(20);
        assertEquals("QTS", result.getNomecurso());
    	
    }
    
    @Test
    void testFindByIdCursoNaoEncontrado() { 
        when(cursoRepository.findById(50)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> cursoService.findById(50));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }


    @Test 
    void testFindAll() {
        Curso curso1 = new Curso(1, "QTS");
        Curso curso2 = new Curso(2, "Engenharia de software");
        Curso curso3 = new Curso(3, "Banco de dados");
        when(cursoRepository.findAll()).thenReturn(Arrays.asList(curso1, curso2, curso3));

        List<Curso> cursos = cursoService.findAll();
        assertEquals(3, cursos.size());
        assertEquals("QTS", cursos.get(0).getNomecurso());
        assertEquals("Engenharia de software", cursos.get(1).getNomecurso());
        assertEquals("Banco de dados", cursos.get(2).getNomecurso());
    }

    @Test
    void testUpdate_Success() { 
        Curso existingCurso = new Curso(1, "QTS");
        Curso updatedCurso = new Curso(1, "QTS");

        when(cursoRepository.findById(1)).thenReturn(Optional.of(existingCurso));
        when(cursoRepository.save(existingCurso)).thenReturn(updatedCurso);

        Curso result = cursoService.update(1, updatedCurso);
        assertEquals("QTS", result.getNomecurso());
    }

    @Test
    void testUpdate_NotFound() { 
        when(cursoRepository.findById(50)).thenReturn(Optional.empty());

        // Cria o objeto Curso fora da lambda
        Curso curso = new Curso(50, "Curso Inexistente");

        
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> { cursoService.update(50, curso); });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void testDelete_Success() {
        Curso curso = new Curso(1, "QTS");
        when(cursoRepository.findById(1)).thenReturn(Optional.of(curso));
        doNothing().when(cursoRepository).delete(curso);
        cursoService.insert(curso);
        
        cursoService.delete(1);
        verify(cursoRepository, times(1)).findById(1);
        verify(cursoRepository, times(1)).delete(curso);
    }

    @Test
    void testDelete_NotFound() { 
        when(cursoRepository.findById(50)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> { cursoService.delete(50); });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}
