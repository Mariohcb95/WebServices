package br.edu.ifgoias.academico.unit;

iimport static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.edu.ifgoias.academico.entities.Aluno;
import br.edu.ifgoias.academico.repositories.AlunoRepository;
import br.edu.ifgoias.academico.services.AlunoService;

@ExtendWith(MockitoExtension.class)
class AlunoServiceTest {

    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private AlunoService alunoService;

    private Aluno aluno;

    @BeforeEach
    void setUp() {
        aluno = new Aluno(4, "Felipe", "M", new Date());
    }

    @Test
    void testFindAllAluno() {
        Aluno aluno2 = new Aluno(5, "Mario", "M", new Date());
        when(alunoRepository.findAll()).thenReturn(Arrays.asList(aluno, aluno2)); 
        
        assertEquals(2, alunoService.findAll().size()); 
    }
 
    @Test
    void testFindByIdAluno() {
        when(alunoRepository.findById(4)).thenReturn(Optional.of(aluno));
        
        Aluno foundAluno = alunoService.findById(4); 
        assertNotNull(foundAluno);
        assertEquals(4, foundAluno.getIdaluno().intValue());
    }

    @Test
    void testFindByIdAlunoNaoEncontrado() {
        when(alunoRepository.findById(1)).thenReturn(Optional.empty()); 
        
        assertThrows(RuntimeException.class, () -> alunoService.findById(1)); 
    }

    @Test
    void testInsertAluno() {
        when(alunoRepository.save(aluno)).thenReturn(aluno); 
        
        Aluno savedAluno = alunoService.insert(aluno);
        assertNotNull(savedAluno);
        assertEquals(aluno, savedAluno);
    }

    @Test
    void testDeleteAluno() {
        doNothing().when(alunoRepository).deleteById(4); 
        
        assertDoesNotThrow(() -> alunoService.delete(4)); 
    }

    @Test
    void testUpdateAluno() {
        Aluno alunoAlterado = new Aluno(1, "Paloma", "F", new Date());

        when(alunoRepository.findById(1)).thenReturn(Optional.of(aluno)); 
        when(alunoRepository.save(any(Aluno.class))).thenReturn(alunoAlterado); 

        Aluno updatedAluno = alunoService.update(1, alunoAlterado);

        assertNotNull(updatedAluno);
        assertEquals("Paloma", updatedAluno.getNome());
        assertEquals("F", updatedAluno.getSexo());
    }

    @Test
    void testUpdateAlunoNaoEncontrado() {
        Aluno alunoAlterado = new Aluno(1, "Paloma", "F", new Date());

        when(alunoRepository.findById(1)).thenReturn(Optional.empty()); 

        assertThrows(RuntimeException.class, () -> alunoService.update(1, alunoAlterado));
    }

    @Test
    void testEqualsAndHashCodeAluno() {
        Aluno alunoTeste = new Aluno(4, "Felipe", "M", new Date()); 
        
        assertEquals(aluno, alunoTeste);
        assertEquals(aluno.hashCode(), alunoTeste.hashCode());

        Aluno alunoTeste1 = new Aluno(5, "Mario", "M", new Date()); 
        assertNotEquals(aluno, alunoTeste1);
    }

    @Test
    void testToStringAluno() {
        String expected = "Aluno [idaluno=4, nome=Felipe, sexo=M, dt_nasc=" + aluno.getDtNasc() + "]";
        String teste = aluno.toString();
        
        assertEquals(expected, teste);  
    }

    @Test
    void testAlunoNullable() {
        Aluno alunoNulo = new Aluno(null, null, null, null);

        assertNull(alunoNulo.getIdaluno());
        assertNull(alunoNulo.getNome());
        assertNull(alunoNulo.getSexo());
        assertNull(alunoNulo.getDtNasc());
    }
}
