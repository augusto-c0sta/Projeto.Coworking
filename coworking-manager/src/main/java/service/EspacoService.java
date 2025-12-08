package service;

import java.util.List;

import dao.EspacoDAO;
import model.Espaco;

public class EspacoService {
	
	private EspacoDAO espacoDAO = new EspacoDAO();
	
	public void  cadastrarEspaco(Espaco espaco)throws Exception{
		
		Espaco existente= espacoDAO.buscarPorId(espaco.getId());
		if(existente !=null) {
			throw new Exception("já existe um espaço com esse ID");
		}
		
		espacoDAO.salvar(espaco);
		
	}
	
	public Espaco buscarPorId(String id){
		return  espacoDAO.buscarPorId(id);		
	}
	
	public List<Espaco> listarTodos(){
		return espacoDAO.listarTodos();
	}

}
