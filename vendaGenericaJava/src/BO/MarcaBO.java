package BO;

import java.util.List;
import DTO.MarcaDTO;

public class MarcaBO extends BO{
	public String cadastrarMarca(MarcaDTO marca) {
		return permanencia.getMarcadao().create(marca)? "Marca cadastrada com sucesso" : "Ocorreu algum problema, tente novamente";
	}
	public List<MarcaDTO> listarMarcas() {
		return permanencia.getMarcadao().get();
	}
	public List<MarcaDTO> getByNome(String nome) {
		return permanencia.getMarcadao().getByNome(nome.toLowerCase());
	}
}
