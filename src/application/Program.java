package application;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import inventario.dao.ProductDAO;
import inventario.model.Product;

public class Program {

	public static void main(String[] args) throws Exception {

		Locale.setDefault(Locale.US);

		Scanner teclado = new Scanner(System.in);

		// Variaveis usadas no programa
		char optionMenu;
		int id;
		String productName;
		String descriptionProduct;
		double priceProduct;
		char optionDelete = ' ';
		char optionExit;

		do {
			clearScreen();

			System.out.println(" --- Iventário --- ");
			System.out.println();

			System.out.println("Adicionar produto: A");

			System.out.println("Buscar Produto por nome: B");

			System.out.println("Localizar Produto por ID: L");

			System.out.println("Modificar Produto: M");

			System.out.println("Excluir Produto: E");

			System.out.print("Digite aqui: ");
			optionMenu = teclado.next().charAt(0);
			System.out.println();

			if (optionMenu == 'a' || optionMenu == 'A') {

				ProductDAO productDAO = new ProductDAO();
				Product product = new Product();

				// Adicionar novo produto
				System.out.println("Adicionar produto");
				System.out.println();

				System.out.print("Produto: ");
				productName = teclado.next();
				product.setProduct(productName);
				System.out.println();

				System.out.print("Descrição: ");
				descriptionProduct = teclado.next();
				product.setDescription(descriptionProduct);
				System.out.println();

				System.out.print("Preço: ");
				priceProduct = teclado.nextDouble();
				product.setPrice(priceProduct);
				System.out.println();

				product.setRegisterDate(new Date());

				productDAO.create(product);

			}
			if (optionMenu == 'm' || optionMenu == 'M') {
				// Atualizar produto
				System.out.println("Atualizar produto");

				// numero localizado no banco de dados
				System.out.println("Id do produto para atualizar: ");
				id = teclado.nextInt();

				ProductDAO productDAO = new ProductDAO();
				Product productUpdate = productDAO.findById(id);

				if (productUpdate != null) {

					System.out.print("Novo nome: ");
					productName = teclado.next();
					productUpdate.setProduct(productName);
					System.out.println();

					System.out.print("Nova Descrição: ");
					descriptionProduct = teclado.next();
					productUpdate.setDescription(descriptionProduct);
					System.out.println();

					System.out.print("Novo Preço: ");
					priceProduct = teclado.nextDouble();
					System.out.println();

					productUpdate.setRegisterDate(new Date());

					productDAO.update(productUpdate);

					System.out.println("Dados do produto atualizado");

				} else {
					System.out.println("Id não encontrado");
				}

			}

			ProductDAO productDao = new ProductDAO();

			while (optionDelete != 'n' || optionDelete != 'N') {
				if (optionMenu == 'E' || optionMenu == 'e') {

					// deletar produto
					System.out.println("Excluir Produto");

					System.out.print("Digite o Id do produto para excluir: ");
					id = teclado.nextInt();

					System.out.println("Confirme o id: ");
					int verificationId = teclado.nextInt();

					if (verificationId == id) {

						productDao.deleteById(id);
						break;

					} else {
						System.out.println("Erro ao confirmar Id");
						System.out.println("Deseja tentar excluir novamente ?S/N");
						optionDelete = teclado.next().charAt(0);

					}
				}
			}

			if (optionMenu == 'l' || optionMenu == 'L') {

				ProductDAO productDAO = new ProductDAO();

				// ID do produto que deseja buscar
				System.out.println("Digite o Id do Produto: ");
				id = teclado.nextInt();
				Product product = productDAO.findById(id);
				if (product != null) {
					System.out.println("Produto encontrado: " + product.getProduct());
				} else {
					System.out.println("Produto com ID " + id + " não encontrado.");
				}

			}
			if (optionMenu == 'b' || optionMenu == 'B') {
				// Buscar por nome
				System.out.println("Digite o nome do produto: ");
				productName = teclado.next();

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
				} catch (Exception e) {
					System.out.println("Erro ao buscar produtos por nome: " + e.getMessage());
				}
			}

			// visualização dos registros de todos os dados
			for (Product p : productDao.listProduct()) {
				System.out.println("Produtos no Inventário: " + p.getProduct());

			}
			System.out.println("Deseja consultar o inventário novamente ? S/N");
			optionExit = teclado.next().charAt(0);

		} while (optionExit != 'n' && optionExit != 'N');

		System.out.println("Até a poxima");

	}

	// Método para limpar a tela do console
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
}
