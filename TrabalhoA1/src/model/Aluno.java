package model;

import javax.persistence.*;

@Entity
public class Aluno {
	
		@Id @GeneratedValue
		private Long id;

		private String nome;
		private Integer idade;
		private String cpf;
		private String email;
		
		@ManyToOne 
		@JoinColumn(name = "curso_id")
		private Curso curso;
		
		@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
		private Endereco endereco;
		
		
		public Aluno() {
			
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Curso getCurso() {
			return curso;
		}

		public void setCurso(Curso curso) {
			this.curso = curso;
		}
		
		public Endereco getEndereco() {
	        return endereco;
	    }

	    public void setEndereco(Endereco endereco) {
	        this.endereco = endereco;
	    }
	    
		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public Integer getIdade() {
			return idade;
		}

		public void setIdade(Integer idade) {
			this.idade = idade;
		}

		public String getCpf() {
			return cpf;
		}

		public void setCpf(String cpf) {
			this.cpf = cpf;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}
			
}
