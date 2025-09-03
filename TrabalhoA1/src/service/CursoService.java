package service;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import model.Aluno;
import model.Curso;

@Stateless
public class CursoService extends GenericService<Curso>{

	public CursoService() {
		super(Curso.class);
	}
	
public List<Curso> listarCursoOrdenado() {
	final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
	final CriteriaQuery<Curso> cQuery = cBuilder.createQuery(Curso.class);
	final Root<Curso> rootCurso = cQuery.from(Curso.class);
	
	//cQuery.select(rootCurso);
	cQuery.orderBy(cBuilder.asc(rootCurso.get("nome")));
	
	List<Curso> result = getEntityManager().createQuery(cQuery).getResultList();
	return result;
}

private boolean alunoNoCurso(Long cursoId) {
	final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
	final CriteriaQuery<Long> cQuery = cBuilder.createQuery(Long.class);
	final Root<Aluno> root = cQuery.from(Aluno.class);
	
	cQuery.select(cBuilder.count(root));
	cQuery.where(cBuilder.equal(root.get("curso").get("id"), cursoId));
	
	boolean result = getEntityManager().createQuery(cQuery).getSingleResult() > 0;
	
	return result;
}

@Override
public void remove(Curso c) {
	if (alunoNoCurso(c.getId())) {
		throw new EJBException("O curso não pode ser excluído pois existem alunos matriculados nele.");
	}
	super.remove(c);
}
}
