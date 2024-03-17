package inventario.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import exception.ExceptionProgram;
import inventario.factory.ConnectionFactory;
import inventario.model.Product;

public class ProductDAO {

	public void create(Product product) throws Exception {

		// Inserir produto
		String sql = "INSERT INTO Produtos(product, description, price, registerDate) VALUES(?, ?, ?, ?)";

		Connection con = null;

		PreparedStatement pstm = null;

		try {
			// Criar conexão

			con = ConnectionFactory.createConnectionToMySQL();

			// Criando preparedStatement para executar query

			pstm = con.prepareStatement(sql);

			// adicionar valores esperados pela query
			pstm.setString(1, product.getProduct());
			pstm.setString(2, product.getDescription());
			pstm.setDouble(3, product.getPrice());
			pstm.setTimestamp(4, new java.sql.Timestamp(product.getRegisterDate().getTime()));

			// executar query

			pstm.execute();
			System.out.println("Novo Produto adicionado");

		} catch (ExceptionProgram e) {
			System.out.println(e.getMessage());
		} finally {
			// fechar conexões
			try {
				if (pstm != null) {
					pstm.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (ExceptionProgram e) {
				System.out.println(e.getMessage());
			}
		}

	}

	public void update(Product product) throws Exception {

		// Atualizar produto
		String sql = "UPDATE Produtos SET product = ?, description = ?, price = ?, registerDate = ? " + "WHERE id = ?";

		Connection con = null;
		PreparedStatement pstm = null;

		try {
			// criar conexão com banco
			con = ConnectionFactory.createConnectionToMySQL();

			// criar classe pra executar query
			pstm = con.prepareStatement(sql);

			// adicionar valores para atualizar
			pstm.setString(1, product.getProduct());
			pstm.setString(2, product.getDescription());
			pstm.setDouble(3, product.getPrice());
			pstm.setTimestamp(4, new java.sql.Timestamp(product.getRegisterDate().getTime()));

			// verificando o id
			pstm.setInt(5, product.getId());

			// executa a query
			pstm.execute();
			System.out.println("Produto atualizado");

		} catch (ExceptionProgram e) {
			System.out.println(e.getMessage());
			// fechar conexões
		} finally {
			try {
				if (pstm != null) {
					pstm.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (ExceptionProgram e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void deleteById(int id) throws Exception {

		// Deletar produto
		String sql = "DELETE FROM Produtos WHERE id= ?";

		Connection con = null;
		PreparedStatement pstm = null;

		try {
			// criar conexão com banco
			con = ConnectionFactory.createConnectionToMySQL();

			// criar classe pra executar query
			pstm = con.prepareStatement(sql);

			// vendo o id
			pstm.setInt(1, id);

			// executa a query
			pstm.execute();
			System.out.println("Produto deletado");

		} catch (ExceptionProgram e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (pstm != null) {
					pstm.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (ExceptionProgram e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public List<Product> listProduct() throws Exception {

		// Retornar as colunas da planilha
		String sql = "SELECT * FROM Produtos";

		List<Product> products = new ArrayList<Product>();

		Connection con = null;
		PreparedStatement pstm = null;

		// Classe que vai recuperar os dados

		ResultSet rset = null;

		try {
			con = ConnectionFactory.createConnectionToMySQL();

			pstm = con.prepareStatement(sql);

			rset = pstm.executeQuery();

			while (rset.next()) {
				Product product = new Product();

				// recuperar id
				product.setId(rset.getInt("id"));

				// recuperar nome do produto
				product.setProduct(rset.getString("product"));

				// recuperar descrição
				product.setDescription(rset.getString("description"));

				// recuperar preço
				product.setPrice(rset.getDouble("price"));

				// recuperar data de registro
				product.setRegisterDate(rset.getDate("registerDate"));

				products.add(product);

			}

		} catch (ExceptionProgram e) {
			System.out.println(e.getMessage());

		} finally {
			// fechando conexões
			try {
				if (rset != null) {
					rset.close();
				}
				if (pstm != null) {
					pstm.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (ExceptionProgram e) {
				System.out.println(e.getMessage());
			}
		}
		return products;

	}

	public Product findById(int id) throws Exception {
		// busca produto por id
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		Product product = null;

		try {
			con = ConnectionFactory.createConnectionToMySQL();

			String sql = "SELECT * FROM Produtos WHERE id = ?";
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, id);
			rset = pstm.executeQuery();

			if (rset.next()) {
				product = new Product();
				product.setId(rset.getInt("id"));
				product.setProduct(rset.getString("product"));
				product.setDescription(rset.getString("description"));
				product.setPrice(rset.getDouble("price"));
				product.setRegisterDate(rset.getDate("registerDate"));
			}
		} catch (ExceptionProgram e) {
			System.out.println(e.getMessage());
		} finally {
			// fecha conexões
			try {
				if (rset != null) {
					rset.close();
				}
				if (pstm != null) {
					pstm.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (ExceptionProgram e) {
				System.out.println(e.getMessage());
			}
		}

		return product;
	}

	public List<Product> findByProductName(String productName) throws Exception {
		// busca produto por nome
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		Product product = null;
		List<Product> products = new ArrayList<>();

		try {
			con = ConnectionFactory.createConnectionToMySQL();

			String sql = "SELECT * FROM Produtos WHERE product LIKE ?";
			pstm = con.prepareStatement(sql);
			pstm.setString(1, "%" + productName + "%");
			rset = pstm.executeQuery();

			while (rset.next()) {
				product = new Product();
				product.setId(rset.getInt("id"));
				product.setProduct(rset.getString("product"));
				product.setDescription(rset.getString("description"));
				product.setPrice(rset.getDouble("price"));
				product.setRegisterDate(rset.getDate("registerDate"));
				products.add(product);
			}
		} catch (ExceptionProgram e) {
			System.out.println(e.getMessage());
		} finally {
			// fecha conexões
			try {
				if (rset != null) {
					rset.close();
				}
				if (pstm != null) {
					pstm.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (ExceptionProgram e) {
				System.out.println(e.getMessage());
			}
		}

		return products;

	}

	// lista de produtos por nome
	public List<Product> listProductName(String productName) throws Exception {
		String sql = "SELECT * FROM Produtos WHERE product = ?";
		List<Product> products = new ArrayList<Product>();
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;

		try {
			con = ConnectionFactory.createConnectionToMySQL();
			pstm = con.prepareStatement(sql);
			pstm.setString(1, productName);
			rset = pstm.executeQuery();

			while (rset.next()) {
				Product productList = new Product();
				productList.setId(rset.getInt("id"));
				productList.setProduct(rset.getString("product"));
				productList.setDescription(rset.getString("description"));
				productList.setPrice(rset.getDouble("price"));
				productList.setRegisterDate(rset.getDate("registerDate"));
				products.add(productList);
			}
		} catch (ExceptionProgram e) {
			System.out.println(e.getMessage());
		} finally {
			// fechando conexões
			try {
				if (rset != null) {
					rset.close();
				}
				if (pstm != null) {
					pstm.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (ExceptionProgram e) {
				System.out.println(e.getMessage());
			}
		}
		return products;
	}

}