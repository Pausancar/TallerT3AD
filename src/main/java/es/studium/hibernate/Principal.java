package es.studium.hibernate;

import java.util.List;
import es.studium.hibernate.dao.PedidoDao;

public class Principal {

	public static void main(String[] args) {
		PedidoDao pedidoDao = new PedidoDao();
		//como PedidoDao hereda de AbstractDao podemos usar el m�todo getAll
		List<Pedido> pedidos = pedidoDao.getAll();
		System.out.println("Lista de pedidos: " + pedidos);
		}
}
