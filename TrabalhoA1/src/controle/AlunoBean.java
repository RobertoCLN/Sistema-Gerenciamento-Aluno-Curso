package controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import model.Aluno;
import model.Curso;
import model.Endereco;
import service.AlunoService;
import service.CursoService;

@ViewScoped
@ManagedBean
public class AlunoBean {

	@EJB
	private AlunoService alunoService;
	
	@EJB
    private CursoService cursoService; 
	
	private Aluno aluno = new Aluno();
	private List<Aluno> alunos = new ArrayList<Aluno>();
	private Boolean edicao = false;
	private Long cursoId;
	
	private String nome;
	
	//metodos
	public void pesquisarPorNome() {
		alunos = alunoService.buscarAlunoPorNome(nome);
	}
	
	private void atualizarLista() {
		alunos = alunoService.ordernarAlunoPorNome();
	}
	
	//metodo executado ao abrir pagina
	@PostConstruct
	public void iniciar() {
		aluno.setEndereco(new Endereco());
		atualizarLista();
	}
	
	public void cancelar() {
		atualizarLista();
		aluno = new Aluno();
		aluno.setEndereco(new Endereco());
		cursoId = null;
		edicao = false;
	}
	
	public void carregar(Aluno a) {
		aluno = a;
		edicao = true;
		
		if (aluno.getCurso() != null) {
            this.cursoId = aluno.getCurso().getId();
        }
	}
	
	public void atualizarAluno() {
		alunoService.merge(aluno);
		atualizarLista();
		aluno = new Aluno();
		aluno.setEndereco(new Endereco());
		cursoId = null;
		edicao = false;
		FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage("Aluno editado!"));
	}
	
	public void gravarAluno() {
		if (cursoId != null && cursoId > 0) {
            Curso cursoSelecionado = cursoService.obtemPorId(cursoId);
            aluno.setCurso(cursoSelecionado);
        }
		
		alunoService.create(aluno);
		atualizarLista();
		
		aluno = new Aluno();
		aluno.setEndereco(new Endereco());
		cursoId = null;
		FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage("Aluno Cadastrado!"));
	}
	
	public void excluir(Aluno aluno) {
		alunoService.remove(aluno);
		FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage("Aluno apagado!"));
		atualizarLista();
	}
	
	public static String formatarCPF(String cpf){
		cpf = cpf.replaceAll("[^0-9]","");
		return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");	
	}
	
	// getters and setters
	public Aluno getAluno() {
		return aluno;
	}
	
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	
	public Long getCursoId() {
		return cursoId;
	}

	public void setCursoId(Long cursoId) {
		this.cursoId = cursoId;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
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
