package inventario.factory;

import java.sql.Connection;
import java.sql.DriverManager;

import exception.ExceptionProgram;

public class ConnectionFactory {
	
	// Nome do usuário
		private static final String USERNAME = "root";

		// Senha do banco
		private static final String PASSWORD = "";

		// Caminho/Porta banco de dados e nome do banco de dados
		private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/inventario";

		// Conexão
		public static Connection createConnectionToMySQL() throws Exception {

			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);

			return connection;

		}

		public static void main(String[] args) throws Exception {
			Connection con = null;
			try {
				con = createConnectionToMySQL();

				if (con != null) {
					System.out.println("Conexão obtida com sucesso");
				}
			} catch (ExceptionProgram e) {
				System.out.println(e.getMessage()); {
					
				}
			} finally {
				try {
					if (con != null) {
						con.close();
					}
				} catch (ExceptionProgram e) {
					System.out.println(e.getMessage());
				}
			}
		}


}
