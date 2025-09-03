package controle;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import model.Aluno;
import model.Curso;
import service.AlunoService;
import service.CursoService;

@ManagedBean
@ViewScoped
public class RelatorioBean {

	@EJB
	private AlunoService alunoService;
	
	@EJB
	private CursoService cursoService;
	
	private Long cursoId;
	private Integer idadeMin;
	private Integer idadeMax;
	private String cidade;
	
	private List<Aluno> alunosFiltrados;
	private List<Curso> cursos;
	
	
	//metodos
	@PostConstruct
	public void iniciar() {
		cursos = cursoService.listarCursoOrdenado();
	}
	
	public void filtrar() {
		alunosFiltrados = alunoService.filtrarAluno(cursoId, idadeMin, idadeMax, cidade);
	}
	
	public void limparFiltros() {
		cursoId = null;
		idadeMin = null;
		idadeMax = null;
		cidade = "";
		alunosFiltrados = null;
	}

	public AlunoService getAlunoService() {
		return alunoService;
	}

	public void setAlunoService(AlunoService alunoService) {
		this.alunoService = alunoService;
	}

	public CursoService getCursoService() {
		return cursoService;
	}

	public void setCursoService(CursoService cursoService) {
		this.cursoService = cursoService;
	}

	public Long getCursoId() {
		return cursoId;
	}

	public void setCursoId(Long cursoId) {
		this.cursoId = cursoId;
	}

	public Integer getIdadeMin() {
		return idadeMin;
	}

	public void setIdadeMin(Integer idadeMin) {
		this.idadeMin = idadeMin;
	}

	public Integer getIdadeMax() {
		return idadeMax;
	}

	public void setIdadeMax(Integer idadeMax) {
		this.idadeMax = idadeMax;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public List<Aluno> getAlunosFiltrados() {
		return alunosFiltrados;
	}

	public void setAlunosFiltrados(List<Aluno> alunosFiltrados) {
		this.alunosFiltrados = alunosFiltrados;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}
	
	
}
