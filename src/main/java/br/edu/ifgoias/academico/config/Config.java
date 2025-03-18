package br.edu.ifgoias.academico.config;

import java.util.logging.Logger;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import br.edu.ifgoias.academico.entities.Aluno;
import br.edu.ifgoias.academico.repositories.AlunoRepository;
import br.edu.ifgoias.academico.repositories.CursoRepository;

@Configuration
public class Config implements CommandLineRunner {
	private static final Logger logger = Logger.getLogger(Config.class.getName());
	
	private CursoRepository cursoRep;	
	
	private AlunoRepository alunoRep;
	
	@Autowired
	public Config (CursoRepository cursoRep, AlunoRepository alunoRep) {
		this.cursoRep = cursoRep;
		this.alunoRep = alunoRep;
	}
	
	@Override
	public void run(String... args) throws Exception {
		

		logger.info("Qtde Cursos: " + cursoRep.count());

		Aluno a1 = new Aluno(null, "Caroline", "Feminino", Date.valueOf("2000-04-24"));

		Aluno a2 = new Aluno(null, "Isabelle", "Feminino", Date.valueOf("2000-12-28"));

		alunoRep.save(a1);

		alunoRep.save(a2);

		logger.info("Qtde Alunos: " + alunoRep.count());
	}

}
