package application;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import exception.ExceptionProgram;
import inventario.dao.ProductDAO;
import inventario.model.Product;

public class Program {

	public static void main(String[] args) throws Exception {

		Locale.setDefault(Locale.US);
		char optionMenu;
		char optionExit = ' ';

		try (Scanner teclado = new Scanner(System.in)) {
			do {
				clearScreen();
				System.out.println(" --- Inventário --- ");
				System.out.println();

				System.out.println("Adicionar produto: A");
				System.out.println("Buscar Produto por nome: B");
				System.out.println("Localizar Produto por ID: L");
				System.out.println("Modificar Produto: M");
				System.out.println("Excluir Produto: E");

				System.out.print("Digite aqui: ");
				optionMenu = teclado.next().charAt(0);
				System.out.println();

				ProductDAO productDao = new ProductDAO();

				switch (optionMenu) {
				case 'a':
				case 'A':
					addProduct(teclado, productDao);
					break;
				case 'b':
				case 'B':
					searchProductByName(teclado, productDao);
					break;
				case 'l':
				case 'L':
					searchProductByID(teclado, productDao);
					break;
				case 'm':
				case 'M':
					updateProduct(teclado, productDao);
					break;
				case 'e':
				case 'E':
					deleteProduct(teclado, productDao);
					break;
				default:
					System.out.println("Opção inválida. Tente novamente.");
					break;
				}
				// visualização dos registros de todos os dados
				for (Product p : productDao.listProduct()) {
					System.out.println("Produtos no Inventário: " + p.getProduct());

				}

				System.out.println("Deseja consultar o inventário novamente? S/N");
				optionExit = teclado.next().charAt(0);

			} while (optionExit != 'n' && optionExit != 'N');

			System.out.println("Produtos no inventário");
			System.out.println();
		}

		System.out.println("Até a próxima");
	}

	public static void addProduct(Scanner teclado, ProductDAO productDao) throws Exception {
		Product product = new Product();

		System.out.println("Adicionar produto");
		System.out.println();

		System.out.print("Produto: ");
		product.setProduct(teclado.next());
		System.out.println();

		System.out.print("Descrição: ");
		product.setDescription(teclado.next());
		System.out.println();

		System.out.print("Preço: ");
		product.setPrice(teclado.nextDouble());
		System.out.println();

		product.setRegisterDate(new Date());

		productDao.create(product);
	}

	public static void searchProductByName(Scanner teclado, ProductDAO productDao) throws Exception {
		System.out.println("Digite o nome do produto: ");
		String productName = teclado.next();

		try {
			List<Product> productList = productDao.listProductName(productName);

			if (!productList.isEmpty()) {
				System.out.println("Produtos encontrados com o nome '" + productName + "':");
				for (Product product : productList) {
					System.out.println("- " + product.getProduct() + " | Descrição: " + product.getDescription()
							+ " | Preço: " + product.getPrice());
				}
			} else {
				System.out.println("Nenhum produto encontrado com o nome '" + productName + "'.");
			}
		} catch (ExceptionProgram e) {
			System.out.println("Erro ao buscar produtos por nome: " + e.getMessage());
		}
	}

	public static void searchProductByID(Scanner teclado, ProductDAO productDao) throws Exception {
		System.out.println("Digite o Id do Produto: ");
		int id = teclado.nextInt();
		Product product = productDao.findById(id);
		if (product != null) {
			System.out.println("Produto encontrado: " + product.getProduct());
		} else {
			System.out.println("Produto com ID " + id + " não encontrado.");
		}
	}

	public static void updateProduct(Scanner teclado, ProductDAO productDao) throws Exception {
		System.out.println("Atualizar produto");

		System.out.println("Id do produto para atualizar: ");
		int id = teclado.nextInt();

		Product productUpdate = productDao.findById(id);

		if (productUpdate != null) {
			System.out.print("Novo nome: ");
			productUpdate.setProduct(teclado.next());
			System.out.println();

			System.out.print("Nova Descrição: ");
			productUpdate.setDescription(teclado.next());
			System.out.println();

			System.out.print("Novo Preço: ");
			productUpdate.setPrice(teclado.nextDouble());
			System.out.println();

			productUpdate.setRegisterDate(new Date());

			productDao.update(productUpdate);

			System.out.println("Dados do produto atualizado");
		} else {
			System.out.println("Id não encontrado");
		}
	}

	public static void deleteProduct(Scanner teclado, ProductDAO productDao) throws Exception {
		System.out.println("Excluir Produto");

		System.out.print("Digite o Id do produto para excluir: ");
		int id = teclado.nextInt();

		System.out.println("Confirme o id: ");
		int verificationId = teclado.nextInt();

		if (verificationId == id) {
			productDao.deleteById(id);
		} else {
			System.out.println("Erro ao confirmar Id");
		}
	}

	// Método para limpar a tela do console
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
}
