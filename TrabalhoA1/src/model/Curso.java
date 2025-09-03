package model;

import java.util.List;
import javax.persistence.*;

@Entity
public class Curso {
	
	@Id @GeneratedValue
	private Long id;
	
	private String nome;
	private String area;
	private Integer duracao;
	
	@OneToMany
	private List<Aluno> alunos;
	
	public Curso() {
		
	}
	
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Integer getDuracao() {
		return duracao;
	}

	public void setDuracao(Integer duracao) {
		this.duracao = duracao;
	}
	
}
