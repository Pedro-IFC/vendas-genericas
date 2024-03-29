package DTO;

public class ProdutoDTO  extends DTOBase{
	private String nome;
	private Double preco;
	private int estoque;
	private int id;
	private String descricao;
	private int marca;
	private int vendedor;
	public ProdutoDTO() {
	}
	public ProdutoDTO(String nome, Double preco, int estoque, String descricao, int marca, int vendedor) {
		super();
		this.nome = nome;
		this.preco = preco;
		this.estoque = estoque;
		this.descricao = descricao;
		this.marca = marca;
		this.vendedor = vendedor;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Double getPreco() {
		return preco;
	}
	public void setPreco(Double preco) {
		this.preco = preco;
	}
	public int getEstoque() {
		return estoque;
	}
	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}
	public int getId() {
		return id;
	}
	public void setId(int idProduto) {
		this.id = idProduto;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getMarca() {
		return marca;
	}
	public void setMarca(int marca) {
		this.marca = marca;
	}
	public int getVendedor() {
		return vendedor;
	}
	public void setVendedor(int vendedor) {
		this.vendedor = vendedor;
	}
	
	
}
