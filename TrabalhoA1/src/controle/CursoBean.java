package controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import model.Aluno;
import model.Curso;
import service.CursoService;
import service.AlunoService;

@ViewScoped
@ManagedBean
public class CursoBean {


@EJB
private CursoService cursoService;

@EJB
private AlunoService alunoService;

private Curso curso = new Curso();
private List<Curso> cursos = new ArrayList<Curso>();
private List<Aluno> alunos = new ArrayList<Aluno>();

private Long idAux=0L;
private Boolean edicao = false;
private String nome;


private void atualizarLista() {
	cursos = cursoService.listarCursoOrdenado();
}

@PostConstruct
public void iniciar() {
	atualizarLista();
}

public void carregarCurso(Curso c) {
	curso = c;
	edicao = true;
}

public void cancelar() {
	edicao = false;
	curso = new Curso();
	//idAux = 0L;
}

public void atualizar() {
	
	cursoService.merge(curso);
	curso = new Curso();
	atualizarLista();
	idAux = 0L;
	edicao = false;
	FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage("Curso atualizado!"));
}

public void gravar() {
	Curso c = cursoService.obtemPorId(idAux);
	
	cursoService.create(curso);
	curso = new Curso();
	atualizarLista();
	idAux = 0L;
	FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage("Curso criado!"));
}

public void excluir(Curso c) {
	 try{
		 cursoService.remove(c);
		 atualizarLista();
		 FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage("Curso excluído!"));
	 } catch (EJBException e) {
		 FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage("Há aluno(os) cadastrados neste curso!"));
	 }
	 
}

public CursoService getCursoService() {
	return cursoService;
}

public void setCursoService(CursoService cursoService) {
	this.cursoService = cursoService;
}

public AlunoService getAlunoService() {
	return alunoService;
}

public void setAlunoService(AlunoService alunoService) {
	this.alunoService = alunoService;
}

public Curso getCurso() {
	return curso;
}

public void setCurso(Curso curso) {
	this.curso = curso;
}

public List<Curso> getCursos() {
	return cursos;
}

public void setCursos(List<Curso> cursos) {
	this.cursos = cursos;
}

public List<Aluno> getAlunos() {
	return alunos;
}

public void setAlunos(List<Aluno> alunos) {
	this.alunos = alunos;
}

public Long getIdAux() {
	return idAux;
}

public void setIdAux(Long idAux) {
	this.idAux = idAux;
}

public Boolean getEdicao() {
	return edicao;
}

public void setEdicao(Boolean edicao) {
	this.edicao = edicao;
}

public String getNome() {
	return nome;
}

public void setNome(String nome) {
	this.nome = nome;
}

}