package service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;

import model.Aluno;

@Stateless
public class AlunoService extends GenericService<Aluno>{

	public AlunoService() {
		super(Aluno.class);
	}
	
	public List<Aluno> buscarAlunoPorNome(String nome) {
		
		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Aluno> cQuery = cBuilder.createQuery(Aluno.class);
		final Root<Aluno> rootAluno = cQuery.from(Aluno.class);
		
		cQuery.select(rootAluno);
		cQuery.where(cBuilder.like(rootAluno.get("nome"), "%"+nome+"%"));
		cQuery.orderBy(cBuilder.asc(rootAluno.get("nome")));
		
		List<Aluno> resultado = getEntityManager().createQuery(cQuery).getResultList();
		
		return resultado;
	}

	public List<Aluno> ordernarAlunoPorNome() {

		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Aluno> cQuery = cBuilder.createQuery(Aluno.class);
		final Root<Aluno> rootAluno = cQuery.from(Aluno.class);

		cQuery.orderBy(cBuilder.asc(rootAluno.get("nome")));

		List<Aluno> resultado = getEntityManager().createQuery(cQuery).getResultList();

		return resultado;
	}
	
	public List<Aluno> filtrarAluno(Long cursoId, Integer idadeMin, Integer idadeMax, String cidade){
		
		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Aluno> cQuery = cBuilder.createQuery(Aluno.class);
		final Root<Aluno> rootAluno = cQuery.from(Aluno.class);
		
		List<Predicate> filtrosSelecionados = new ArrayList<>();
		
		// filtro de cursoId
		if(cursoId != null && cursoId > 0) {
			filtrosSelecionados.add(cBuilder.equal(rootAluno.get("curso").get("id"), cursoId));
		}
		
		// filtro de idadeMin
		if(idadeMin != null && idadeMin > 0) {
			filtrosSelecionados.add(cBuilder.ge(rootAluno.get("idade"), idadeMin));
		}
		
		//filtro de idadeMax
		if(idadeMax != null && idadeMax > 0) {
			filtrosSelecionados.add(cBuilder.le(rootAluno.get("idade"), idadeMax));
		}
		
		//filtro de cidade
		if(cidade != null && !cidade.trim().isEmpty()) {
			Predicate pLike = cBuilder.like(cBuilder.lower(rootAluno.get("endereco").get("cidade")),
					"%" + cidade.toLowerCase() + "%");
			
			filtrosSelecionados.add(pLike);
		}
		
		// juntar todos os filtros selecionados (AND)
		if(!filtrosSelecionados.isEmpty()) {
			cQuery.where(cBuilder.and(filtrosSelecionados.toArray(new Predicate[0])));
		}
		
		cQuery.orderBy(cBuilder.asc(rootAluno.get("nome")));
		
		List<Aluno> resultado = getEntityManager().createQuery(cQuery).getResultList();
		
		return resultado;
	}

}
